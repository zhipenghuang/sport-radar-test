package com.yunmu.uof.dto;

import lombok.Data;

import java.util.List;

@Data
public class Bet {

    //@ApiModelProperty(required = false, value = "必填，Single: 1, Multiple: selection的数量")
    private int selectedSystem;

    //@ApiModelProperty(required = false, value = "必填，Bet ID, 格式：\"B-\" + uid + nowMs + \"-bet-\" + i")
    private String id;

    //@ApiModelProperty(required = false, value = "必填，投注金额，实际金额*10000取整")
    private long stake;

    //@ApiModelProperty(required = false, value = "必填，投注金额类型，\"unit\" 或者 \"total\", 前者表示这个金额用于单注，后者表示这个金额用于整个ticket")
    private String stakeType;

    //@ApiModelProperty(required = false, value = "必填，一组投注选项")
    private List<Selection> selections;

    public static final int SELECTED_SYSTEM_SINGLE = 1;
    public static final String STAKE_TYPE_UNIT = "unit";
    public static final String STAKE_TYPE_TOTAL = "total";
}