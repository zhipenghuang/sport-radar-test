package com.yunmu.uof.conn;

import com.alibaba.fastjson.JSON;
import com.sportradar.mts.sdk.api.*;
import com.sportradar.mts.sdk.api.builders.BetBuilder;
import com.sportradar.mts.sdk.api.builders.TicketBuilder;
import com.sportradar.mts.sdk.api.enums.*;
import com.sportradar.mts.sdk.api.impl.TicketCancelAckImpl;
import com.sportradar.mts.sdk.api.impl.TicketCancelImpl;
import com.sportradar.mts.sdk.api.interfaces.*;
import com.sportradar.mts.sdk.app.MtsSdk;
import com.yunmu.uof.dto.Bet;
import com.yunmu.uof.dto.Selection;
import com.yunmu.uof.dto.TicketCashoutResponse;
import com.yunmu.uof.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class MyMtsSdk implements TicketResponseHandler, ApplicationListener<ContextRefreshedEvent> {

    private MtsSdkApi mtsSdk;
    private TicketAckSender ticketAckSender;
    private TicketCancelAckSender ticketCancelAckSender;
    private TicketCancelSender ticketCancelSender;
    private TicketSender ticketSender;
    private TicketCashoutSender ticketCashoutSender;

    private List<TicketData> pendingTickets = Collections.synchronizedList(new ArrayList<>());

    private Map<String, Integer> cancelCodeMap = Collections.synchronizedMap(new HashMap<>());

    private Object mutex = new Object();
    private boolean available = false;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("${time-zone:}")
    private String timeZone;

    @Value("${sdk-ticket-version:2.3}")
    private String sdkTicketVersion;

    @Value("${sdk-bookmaker-id:}")
    private int sdkBookmakerId;

    @Value("${sdk-limit-id:}")
    private int sdkLimitId;

    @Value("${sdk-hostname:}")
    private String sdkHostname;

    @Value("${sdk-vhost:}")
    private String sdkVHost;

    @Value("${sdk-username:}")
    private String sdkUsername;

    @Value("${sdk-password:}")
    private String sdkPassword;

    private static final int PHASE_MTS_SENT_RESPONSE_ACCEPTED = 3;
    private static final int PHASE_MTS_SENT_RESPONSE_REJECT = 4;
    private static final int PHASE_MTS_SENT_CANCEL_RESPONSE_CANCELLED = 7;
    private static final int PHASE_MTS_SENT_CANCEL_RESPONSE_NOT_CANCELLED = 8;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
//        TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
//
//        Properties properties = new Properties();
//        properties.setProperty("mts.sdk.hostname", sdkHostname);
//        properties.setProperty("mts.sdk.vhost", sdkVHost);
//        properties.setProperty("mts.sdk.username", sdkUsername);
//        properties.setProperty("mts.sdk.password", sdkPassword);
//
//        mtsSdk = new MtsSdk(MtsSdk.getConfiguration(properties));
//        mtsSdk.open();
//
//        ticketAckSender = mtsSdk.getTicketAckSender(new TicketAckHandler(this));
//        ticketCancelAckSender = mtsSdk.getTicketCancelAckSender(new TicketCancelAckHandler(this));
//        ticketCancelSender = mtsSdk.getTicketCancelSender(new TicketCancelResponseHandler(this));
//        ticketSender = mtsSdk.getTicketSender(new TicketResponseHandlerImpl(this));
//        ticketCashoutSender = mtsSdk.getTicketCashoutSender(new TicketCashoutResponseHandlerImpl());
//
//        becomeAvailable();
//
//        Runnable runnable = () -> {
//            long now = System.currentTimeMillis();
//            for (TicketData td : pendingTickets) {
//                String ticketId = td.getData().getTicketId();
//                if (now - td.getTs() > 10000) {
//                    becomeUnavailable();
//                    log.error("No response from mts for the ticket {}", td.getType(), ticketId);
//                    switch (td.getType()) {
//                        case TicketData.TYPE_SEND:
//                            pendingTickets.remove(td);
//                            pendingTickets.add(new TicketData(TicketData.TYPE_CANCELLATION, System.currentTimeMillis(), td.getAppId(), td.getData(), td.getCallback()));
//                            cancelTicket(ticketId, TicketCancellationReason.TimeoutTriggered);
//                            break;
//                        case TicketData.TYPE_CANCELLATION:
//                            pendingTickets.remove(td);
//                            break;
//                        default:
//                            break;
//                    }
//                }
//            }
//        };
//
//        ScheduledExecutorService service = Executors
//                .newSingleThreadScheduledExecutor();
//        service.scheduleAtFixedRate(runnable, 1, 1, TimeUnit.SECONDS);
    }

    public boolean isAvailable() {
        synchronized (mutex) {
            return available;
        }
    }

    private void becomeAvailable() {
        synchronized (mutex) {
            available = true;
        }
    }

    private void becomeUnavailable() {
        synchronized (mutex) {
            available = false;
        }
    }

    public void onTicketResponseReceived(TicketResponse response) {
        int phase;
        if (response.getStatus() == TicketAcceptance.ACCEPTED) {
            phase = PHASE_MTS_SENT_RESPONSE_ACCEPTED;
        } else {
            phase = PHASE_MTS_SENT_RESPONSE_REJECT;
        }

        String errorMessage = "";
        if (response.getReason() != null) {
            errorMessage = response.getReason().getMessage();
        }

        updateTicket(response.getTicketId(), phase, errorMessage);

        if (!isAvailable()) {
            becomeAvailable();
            log.info("The server become available because receive send response for the ticket {}", response.getTicketId());
        }

        removeTicketDataById(response.getTicketId());
    }

    public void onTicketCancelResponseReceived(TicketCancelResponse response) {
        int phase;
        boolean sendAck = true;
        if (cancelCodeMap.get(response.getTicketId()) == 102) {
            sendAck = false;
        }

        cancelCodeMap.remove(response.getTicketId());

        if (response.getStatus() == TicketCancelAcceptance.Cancelled) {
            phase = PHASE_MTS_SENT_CANCEL_RESPONSE_CANCELLED;
            if (sendAck) {
                try {
                    TicketCancelAckImpl impl = new TicketCancelAckImpl(response.getTicketId(), sdkBookmakerId, response.getReason().getCode(), response.getTicketId(), TicketCancelAckStatus.CANCELLED, new Date(), sdkTicketVersion);
                    ticketCancelAckSender.send(impl);
                    log.info("Send cancel ack [Cancelled] for the ticket {}", response.getTicketId());
                } catch (Exception e) {
                    log.error("Send cancel ack [Cancelled] failed for the ticket {}", response.getTicketId());
                } finally {

                }
            }
        } else {
            phase = PHASE_MTS_SENT_CANCEL_RESPONSE_NOT_CANCELLED;
            if (sendAck) {
                try {
                    TicketCancelAckImpl impl = new TicketCancelAckImpl(response.getTicketId(), sdkBookmakerId, response.getReason().getCode(), response.getTicketId(), TicketCancelAckStatus.NOT_CANCELLED, new Date(), sdkTicketVersion);
                    ticketCancelAckSender.send(impl);
                    log.info("Send cancel ack [NotCancelled] for the ticket {}", response.getTicketId());
                } catch (Exception e) {
                    log.error("Send cancel ack [NotCancelled] failed for the ticket {}", response.getTicketId());
                } finally {

                }
            }
        }

        String errorMessage = "";
        if (response.getReason() != null) {
            errorMessage = response.getReason().getMessage();
        }

        updateTicket(response.getTicketId(), phase, errorMessage);

        if (!isAvailable()) {
            becomeAvailable();
            log.info("The server become available because receive send response for the ticket {}", response.getTicketId());
        }

        removeTicketDataById(response.getTicketId());
    }

    private void updateTicket(String ticketId, int phase, String errorMessage) {
        String sql = "update tbl_tickets set phase=?, error_message=? where id=?";

        try {
//            mongoTemplate.update(sql);
        } catch (DataAccessException e) {
            log.error("Update phase {} of ticket {} failed: {}", phase, ticketId, e.getMessage());
        } finally {

        }
    }

    private void removeTicketDataById(String ticketId) {
        for (TicketData td : pendingTickets) {
            if (td.getData().getTicketId().equals(ticketId)) {
                pendingTickets.remove(td);
                break;
            }
        }
    }

    private class TicketData {
        public long getTs() {
            return ts;
        }

        public Ticket getData() {
            return data;
        }

        private long ts;

        public int getType() {
            return type;
        }

        public static final int TYPE_SEND = 1;
        public static final int TYPE_CANCELLATION = 2;
        private int type;

        public int getAppId() {
            return appId;
        }

        private int appId;

        public String getCallback() {
            return callback;
        }

        private String callback;

        private Ticket data = null;

        public TicketData(int type, long ts, int appId, Ticket data, String callback) {
            this.type = type;
            this.ts = ts;
            this.appId = appId;
            this.data = data;
            this.callback = callback;
        }
    }

    /**
     * Send ticket
     *
     * @param message
     * @return
     */
    public SendTicketResponse sendTicket(SendTicketRequest message) {
        SendTicketResponse response = new SendTicketResponse();

        if (!isAvailable()) {
            response.setCode(BaseResponse.ERROR_CODE_SERVER_IS_NOT_AVAILABLE);
            return response;
        }

        SenderChannel senderChannel;

        if (message.getChannel() == SendTicketRequest.MOBILE) {
            senderChannel = SenderChannel.MOBILE;
        } else if (message.getChannel() == SendTicketRequest.PC) {
            senderChannel = SenderChannel.INTERNET;
        } else if (message.getChannel() == SendTicketRequest.SMS) {
            senderChannel = SenderChannel.SMS;
        } else {
            senderChannel = SenderChannel.INTERNET;
        }

        try {
            TicketBuilder ticketBuilder = mtsSdk.getBuilderFactory().createTicketBuilder()
                    .setTicketId(message.getTicketId())
                    .setOddsChange(OddsChangeType.NONE)
                    .setSender(mtsSdk.getBuilderFactory().createSenderBuilder()
                            .setBookmakerId(sdkBookmakerId)
                            .setLimitId(sdkLimitId)
                            .setSenderChannel(senderChannel)
                            .setCurrency(message.getCurrency())
                            .setEndCustomer(
                                    message.getClientIp(),
                                    message.getUid(),
                                    message.getLanguageId(),
                                    message.getClientDevice(),
                                    (long) message.getConfidence())
                            .build());

            for (Bet bet : message.getBets()) {
                BetBuilder betBuilder = mtsSdk.getBuilderFactory().createBetBuilder()
                        .addSelectedSystem(bet.getSelectedSystem())
                        .setBetId(bet.getId())
                        .setStake(bet.getStake(), bet.getStakeType() == Bet.STAKE_TYPE_UNIT ? StakeType.UNIT : StakeType.TOTAL);

                for (Selection selection : bet.getSelections()) {
                    betBuilder.addSelection(
                            mtsSdk.getBuilderFactory().createSelectionBuilder()
                                    .setEventId(selection.getEventId())
                                    .setId(selection.getId())
                                    .setOdds(selection.getOdds())
                                    .setBanker(selection.isBanker())
                                    .build());
                }

                ticketBuilder.addBet(betBuilder.build());
            }

            Ticket ticket = ticketBuilder.build();

            pendingTickets.add(new TicketData(TicketData.TYPE_SEND, System.currentTimeMillis(), message.getAppId(), ticket, message.getCallback()));

            ticketSender.send(ticket);
        } catch (Exception e) {
            log.error("Send ticket {} failed: {}", JSON.toJSONString(message, true), e.getMessage());
            response.setCode(BaseResponse.ERROR_CODE_FORMAT_ERROR);
        } finally {

        }

        return response;
    }

    public abstract class PublishResultHandler<T extends SdkTicket> implements PublishResultListener<T> {

        public void publishFailure(T t) {
            log.error("published failed! we should check if we want to republish msg. message : {}", t);
        }

        public void publishSuccess(T t) {
            log.info("published succeed {}", t);
        }
    }

    public class TicketAckHandler extends PublishResultHandler<TicketAck> implements TicketAckResponseListener {
        public TicketAckHandler(TicketResponseHandler ticketResponseHandler) {

        }
    }

    public class TicketCancelAckHandler extends PublishResultHandler<TicketCancelAck> implements TicketCancelAckResponseListener {
        public TicketCancelAckHandler(TicketResponseHandler ticketResponseHandler) {

        }
    }

    public class TicketCancelResponseHandler extends PublishResultHandler<TicketCancel> implements TicketCancelResponseListener {
        private TicketResponseHandler ticketResponseHandler;

        public TicketCancelResponseHandler(TicketResponseHandler ticketResponseHandler) {
            this.ticketResponseHandler = ticketResponseHandler;
        }

        public void responseReceived(TicketCancelResponse ticketCancelResponse) {
            ticketResponseHandler.onTicketCancelResponseReceived(ticketCancelResponse);
        }
    }

    public class TicketResponseHandlerImpl extends PublishResultHandler<Ticket> implements TicketResponseListener {
        private final TicketResponseHandler ticketResponseHandler;

        public TicketResponseHandlerImpl(TicketResponseHandler ticketResponseHandler) {
            this.ticketResponseHandler = ticketResponseHandler;
        }

        public void responseReceived(TicketResponse ticketResponse) {
            this.ticketResponseHandler.onTicketResponseReceived(ticketResponse);
        }
    }

    public class TicketCashoutResponseHandlerImpl extends PublishResultHandler<TicketCashout> implements TicketCashoutResponseListener {
        @Override
        public void responseReceived(com.sportradar.mts.sdk.api.TicketCashoutResponse ticketTicketCashoutResponse) {
            String msg = JSON.toJSONString(ticketTicketCashoutResponse);
            log.info("ticket cashout response {}", msg);
            log.info("reason {}", ticketTicketCashoutResponse.getReason());
            log.info("signature {}", ticketTicketCashoutResponse.getSignature());
            log.info("status {}", ticketTicketCashoutResponse.getStatus());
            log.info("ticket id {}", ticketTicketCashoutResponse.getTicketId());
        }
    }

    public TicketCashoutResponse cashout(TicketCashoutRequest request) {
        TicketCashoutResponse response = new TicketCashoutResponse();

        //TicketCashoutImpl ticketCashout = new TicketCashoutImpl(request.getTicketId(), mtsConfig.getBookmakerId(), new Date(), request.getCashoutStake(), sdkTicketVersion);
        //ticketCashoutSender.send(ticketCashout);

        return response;
    }

    public CancelTicketResponse cancelTicket(CancelTicketRequest request) {
        CancelTicketResponse response = new CancelTicketResponse();
        TicketCancellationReason reason = null;

        switch (request.getCode()) {
            case 101:
                reason = TicketCancellationReason.CustomerTriggeredPrematch;
                break;
            case 102:
                reason = TicketCancellationReason.TimeoutTriggered;
                break;
            case 103:
                reason = TicketCancellationReason.BookmakerBackofficeTriggered;
                break;
            case 104:
                reason = TicketCancellationReason.BookmakerTechnicalIssue;
                break;
            case 105:
                reason = TicketCancellationReason.ExceptionalBookmakerTriggered;
                break;
            case 106:
                reason = TicketCancellationReason.BookmakerCashbackPromotionCancellation;
                break;
            case 301:
                reason = TicketCancellationReason.SogeiTriggered;
                break;
            case 302:
                reason = TicketCancellationReason.SccsTriggered;
                break;
            default:
                log.error("Unsupported cancellation code {}", request.getCode());
                break;
        }

        cancelCodeMap.put(request.getTicketId(), request.getCode());

        if (reason != null && request.getTicketId() != null && !request.getTicketId().isEmpty()) {
            cancelTicket(request.getTicketId(), reason);
            response.setCode(0);
        } else {
            response.setCode(-1);
        }

        return response;
    }

    private void cancelTicket(String ticketId, TicketCancellationReason reason) {
        try {
            TicketCancelImpl impl = new TicketCancelImpl(ticketId, sdkBookmakerId, reason, new Date(), 1000000, null, sdkTicketVersion);
            ticketCancelSender.send(impl);
        } catch (Exception e) {
            log.error("Cancel ticket {} failed: {}", ticketId, e.getMessage());
        }
    }
}
