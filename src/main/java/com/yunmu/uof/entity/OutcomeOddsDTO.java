package com.yunmu.uof.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * OutcomeOddsDto
 *
 * @author aleng
 * @version 1.0.0
 * @since 2020年7月29日 下午8:50:52
 */
@Data
@ToString(callSuper = true)
public class OutcomeOddsDTO implements Serializable {

    /**
     * id
     */
    private String id;

    /**
     * 盘口Id + _ + specifierId + _ + id
     */
    private String outcomeId;

    /**
     * 盘口名称
     */
    private String name;

    /**
     * 赔率值
     */
    private BigDecimal odds;

    /**
     * 是否活跃
     */
    private Boolean isActive;
}
