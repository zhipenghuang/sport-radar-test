package com.yunmu.uof.enums;

import lombok.Getter;

@Getter
public enum MatchResultType {
    FULL_TIME(1, "全场"),
    FIRST_HALF(2, "上半场"),
    SECOND_HALF(3, "下半场"),
    TWO_HALF(4, "上下半场");

    MatchResultType(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    private Integer type;

    private String desc;
}
