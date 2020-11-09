package com.yunmu.uof.dto;

import lombok.Data;

/**
 * Created by pandazhong on 17/10/10.
 */
@Data
public class CancelTicketRequest extends BaseRequest {
    //@ApiModelProperty(required = true, value = "客户端必填，类型号码")
    private int code;

    //@ApiModelProperty(required = true, value = "客户端必填，Ticket ID")
    private String ticketId;

    //@ApiModelProperty(required = true, value = "必填，Limit ID")
    private int limitId;
}
