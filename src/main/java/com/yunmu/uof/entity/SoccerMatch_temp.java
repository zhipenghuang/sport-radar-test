package com.yunmu.uof.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * 足球比赛实例
 *
 * @author aleng
 * @version 1.0
 * @since 2020/7/21 12:58
 */
@Data
@Document(collection = "soccer_match")
public class SoccerMatch_temp {
    /**
     * id
     */
    @Id
    private String id;
    /**
     * 赛程id
     */
    private String matchId;
    /**
     * 赛程状态
     */
    private String matchStatus;
    /**
     * 比赛时间
     */
    private String matchScheduled;
    /**
     * 日期确定时间不确定的情况
     */
    private Boolean matchStartTimeTbd;
    /**
     * 主队id
     */
    private String homeTeamId;
    /**
     * 主队名称
     */
    private String homeTeamName;
    /**
     * 客队id
     */
    private String awayTeamId;
    /**
     * 客队全称
     */
    private String awayTeamName;
    /**
     * 赛事id
     */
    private String tournamentId;
    /**
     * 赛事名称
     */
    private String tournamentName;
    /**
     * 赛事所属地区名称
     */
    private String tournamentCategoryName;
    /**
     * 赛事所属地区id
     */
    private String tournamentCategoryId;
    /**
     * 赛事运动id
     */
    private String tournamentSportId;
    /**
     * 赛事运动名称
     */
    private String tournamentSportName;
    /**
     * 生产者ID
     */
    private String producer;

    private List<Market> markets;

}
