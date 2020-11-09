package com.yunmu.uof.entity;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@FieldNameConstants
public class PeriodScoreDTO implements Serializable {

    /**
     * 主队在此阶段的得分/进球得分/比赛得分数
     */
    private BigDecimal homeScore;

    /**
     * 客队在此阶段的得分/进球得分/比赛得分数
     */
    private BigDecimal awayScore;

    /**
     * 阶段的类型说明（常规时间RegularPeriod，加时Overtime，点球对决Penalties，其他Other等等）
     */
    private String type;

    /**
     * 当前阶段类型是否是常规阶段，并且指示是哪个常规阶段
     */
    private Integer number;

    /**
     * 描述
     */
    private String periodDescription;

    /**
     * 比赛状态代码
     */
    private Integer matchStatusCode;
}
