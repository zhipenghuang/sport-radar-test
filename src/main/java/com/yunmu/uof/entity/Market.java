package com.yunmu.uof.entity;

import com.sportradar.unifiedodds.sdk.oddsentities.OutcomeOdds;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

/**
 * 盘口
 *
 * @author xxxx
 * @version 1.0
 * @since 2020/7/21 21:16
 */
@Data
@Document()
public class Market {

    /**
     * 盘口id
     */
    private String id;

    /**
     * 修饰符
     */
    private Map<String, String> specifier;

    /**
     * 盘口名称
     */
    private String marketName;

    /**
     * 盘口的状态  1 (未开始)  -1 (上半场加时)  0 (下半场)   ？ (handed_over)   -4 (关闭)   -3 (结束)
     */
    private Integer status;

    /**
     * 是否推荐盘口 0 否   1 是
     */
    private Integer isFavourite;

    private List<OutcomeOdds> outcomeOdds;
}
