package com.yunmu.uof.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

/**
 * 盘口选项
 *
 * @author xxxx
 * @version 1.0
 * @since 2020/7/21 21:16
 */
@Data
@Document(collection = "OutcomeOdds")
public class OutcomeOdds {

    /** id */
    private String id;

    /** 赔率值 */
    private BigDecimal odds;

    /** 赢的概率 */
    private BigDecimal probabilities;

    /** 是否活跃 1 (活跃)  0 (不活跃) */
    private Integer isctive;

}