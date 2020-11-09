package com.yunmu.uof.conn;

import com.yunmu.uof.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class App {

    @Autowired
    private MyMtsSdk myMtsSdk;

    /**
     * Send ticket
     * 发送订单
     * @param request
     * @return
     */
    public SendTicketResponse sendTicket(SendTicketRequest request) {
        return myMtsSdk.sendTicket(request);
    }

    /**
     * Cancel ticket
     * 取消订单
     * @param request
     * @return
     */
    public CancelTicketResponse cancelTicket(CancelTicketRequest request) {
        return myMtsSdk.cancelTicket(request);
    }
}
