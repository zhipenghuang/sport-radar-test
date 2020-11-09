package com.yunmu.uof.dto;

import lombok.Data;

@Data
public class Selection {

    //@ApiModelProperty(required = false, value = "必填，如果specifiers不为空，则id = \"uof:\" + betSelection.getProductId() + \"/\" + betSelection.getSportId() + \"/\" + betSelection.getMarketId() + \"/\" + betSelection.getOutcomeId() + \"?\" + betSelection.getSpecifiers(), 如果specifiers为空，则id=\"uof:\" + betSelection.getProductId() + \"/\" + betSelection.getSportId() + \"/\" + betSelection.getMarketId() + \"/\" + betSelection.getOutcomeId()")
    private String id;

    //@ApiModelProperty(required = false, value = "必填，如果比赛ID为sr:match:12345，那么eventId=12345")
    private String eventId;

    //@ApiModelProperty(required = false, value = "必填，默认为false，表示投注者是庄家投注")
    private boolean banker;

    //@ApiModelProperty(required = false, value = "必填，赔率*10000")
    private int odds;
}