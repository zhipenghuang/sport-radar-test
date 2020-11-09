package com.yunmu.uof.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by pandazhong on 18/2/26.
 */
@Data
public class SendTicketRequest extends BaseRequest {

    //@ApiModelProperty(required = false, value = "必填，全局唯一的Ticket ID")
    private String ticketId;

    //@ApiModelProperty(required = false, value = "必填，货币缩写，肯先令：KES")
    private String currency;

    //@ApiModelProperty(required = false, value = "选填，客户端IP")
    private String clientIp;

    //@ApiModelProperty(required = false, value = "选填，用户ID")
    private String uid;

    //@ApiModelProperty(required = false, value = "选填，语言ID，英语：EN")
    private String languageId;

    //@ApiModelProperty(required = false, value = "选填，客户端设备")
    private String clientDevice;

    //@ApiModelProperty(required = false, value = "选填，客户端信用值")
    private int confidence;

    //@ApiModelProperty(required = false, value = "必填，一组投注信息")
    private List<Bet> bets;

    //@ApiModelProperty(required = false, value = "必填，APP ID")
    private int appId;

    //@ApiModelProperty(required = false, value = "必填，哪个端，1:mobile, 2:pc, 3:sms")
    private int channel;

    //@ApiModelProperty(required = false, value = "必填, Limit ID")
    private int limitId;

    //@ApiModelProperty(required = false, value = "可选, 回调地址: http://domainname/v1/x, 如果为空，则通过kafka通知结果")
    private String callback;

    public final static int MOBILE = 1;
    public final static int PC = 2;
    public final static int SMS = 3;
}
