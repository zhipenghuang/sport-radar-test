package com.yunmu.uof.dto;

import lombok.Data;

@Data
public class TicketCashoutRequest extends BaseRequest {

    //@ApiModelProperty(required = true, value = "必填，Ticket ID")
    private String ticketId;

    //@ApiModelProperty(required = true, value = "必填，cashout stake")
    private long cashoutStake;

    //@ApiModelProperty(required = true, value = "必填，limit id, gamemania: 827, guanhao: 1209")
    private int limitId;
}
