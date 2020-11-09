package com.yunmu.uof.controller;

import com.yunmu.uof.conn.App;
import com.yunmu.uof.dto.CancelTicketRequest;
import com.yunmu.uof.dto.CancelTicketResponse;
import com.yunmu.uof.dto.SendTicketRequest;
import com.yunmu.uof.dto.SendTicketResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TicketController {

    @Autowired
    private App app;

    @RequestMapping(path = "/mts-gateway/v1/send/ticket", method = RequestMethod.POST)
    public SendTicketResponse sendTicket(@RequestBody SendTicketRequest request) {
        return app.sendTicket(request);
    }

    @RequestMapping(path = "/mts-gateway/v1/cancel/ticket", method = RequestMethod.POST)
    public CancelTicketResponse cancelTicket(@RequestBody CancelTicketRequest request) {
        return app.cancelTicket(request);
    }

    @RequestMapping(path = "/info", method = RequestMethod.POST)
    public String info(String ss) {
        log.info("info-----------" + System.currentTimeMillis());
        return "success";
    }

    @RequestMapping(path = "/warn", method = RequestMethod.POST)
    public String warn() {
        log.warn("warn-----------" + System.currentTimeMillis());
        return "success";
    }

    @RequestMapping(path = "/error1", method = RequestMethod.POST)
    public String ffgsssss() {
        log.error("error-----------" + System.currentTimeMillis());
        return "success";
    }

    @RequestMapping(path = "/aa", method = RequestMethod.POST)
    public int aa() {
        int i = 1 / 0;
        return i;
    }
}
