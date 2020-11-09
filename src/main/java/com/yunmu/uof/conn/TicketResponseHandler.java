package com.yunmu.uof.conn;

import com.sportradar.mts.sdk.api.TicketCancelResponse;
import com.sportradar.mts.sdk.api.TicketResponse;

/**
 * Created by pandazhong on 17/7/25.
 */
public interface TicketResponseHandler {
    void onTicketResponseReceived(TicketResponse response);
    void onTicketCancelResponseReceived(TicketCancelResponse response);
}
