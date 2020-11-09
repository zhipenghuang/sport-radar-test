package com.yunmu.uof.enums;

import lombok.Getter;

/**
 * @version V1.0
 * @Title: ClassifyEnum
 * @Package com.ygty.business.data.statiscs.client.model
 * @Description: 静态数据枚举类型
 * @author: wanglan
 */
@Getter
public enum SportDataType {
    /**
     * 枚举api url 中的大写单词 为可变入参
     */
    SCHEDULE_PAGE("https://stgapi.betradar.com/v1/sports/zh/schedules/pre/schedule.xml?start=ZERO&limit=1000", "分页查询赛程"),

    SOCCER_MARKET("https://stgapi.betradar.com/v1/descriptions/LANGUAGE/markets.xml?include_mappings=true", "所有盘口"),

    ALL_SPORT("https://stgapi.betradar.com/v1/sports/zh/sports.xml", "所有运动"),

    MATCH_STATUS("https://stgapi.betradar.com/v1/descriptions/zh/match_status.xml", "比赛状态"),

    TIME_LINE("https://stgapi.betradar.com/v1/sports/zh/sport_events/MATCHID/timeline.xml", "时间线");

    SportDataType(String api, String desc) {
        this.api = api;
        this.desc = desc;
    }

    /**
     * api
     */
    private String api;
    /**
     * 描述
     */
    private String desc;
}
