package com.yunmu.uof.entity;


import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 盘口
 *
 * @author xxxx
 * @version 1.0
 * @since 2020/7/21 21:16
 */
@Data
@ToString
public class AA {

    private String id;

    private String specifier;

    private Long timeStamp;

    private Integer matchResultType;

    private List<BB> outcomes;
}