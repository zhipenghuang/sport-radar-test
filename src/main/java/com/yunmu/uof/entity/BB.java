package com.yunmu.uof.entity;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 盘口选项
 *
 * @author xxxx
 * @version 1.0
 * @since 2020/7/21 21:16
 */
@Data
@ToString
public class BB {

    /** id */
    private String id;

    /** 输赢结果 0,LOST 1,WON -1,UNDECIDED_YET */
    private Integer result;

    /** 取消因子 */
    private BigDecimal voidFactor;

    /** 平局因子 */
    private BigDecimal deadHeatFactor;

    /** 1:全场、2:上半场、3:下半场、4:两个半场 */
    private Integer matchResultType;
}

