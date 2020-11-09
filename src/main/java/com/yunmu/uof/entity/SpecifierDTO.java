package com.yunmu.uof.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * SpecifierDto
 *
 * @author aleng
 * @version 1.0.0
 * @since 2020年7月29日 下午8:49:59
 */
@Data
@ToString(callSuper = true)
public class SpecifierDTO implements Serializable {
    /**
     * 盘口选项数组
     */
    public List<OutcomeOddsDTO> outComes = new ArrayList<>();
    /**
     * 亚盘口修饰符
     */
    public String specifier;
    /**
     * 亚盘口是否活跃
     */
    private Boolean isActive;
    /**
     * 是否推荐盘口 0 否 1 是
     */
    private Boolean isFavourite;
    /**
     * 亚盘口id
     */
    private String specifierId;
    /**
     * 亚盘口修饰符Map
     */
    private Map<String, String> specifierMap;
}
