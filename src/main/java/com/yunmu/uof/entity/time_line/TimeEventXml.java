package com.yunmu.uof.entity.time_line;

import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "event")
@XmlType(
        propOrder = {
                "type",
                "matchTime",
                "matchClock",
                "team",
                "x",
                "y",
                "period",
                "homeScore",
                "awayScore",
                "matchStatusCode"
        })
@Data
@ToString
public class TimeEventXml implements Serializable {

    @XmlAttribute(name = "type")
    private String type;

    @XmlAttribute(name = "match_time")
    private String matchTime;

    @XmlAttribute(name = "match_clock")
    private String matchClock;

    @XmlAttribute(name = "team")
    private String team;

    @XmlAttribute(name = "x")
    private String x;

    @XmlAttribute(name = "y")
    private String y;

    @XmlAttribute(name = "period")
    private String period;

    @XmlAttribute(name = "home_score")
    private String homeScore;

    @XmlAttribute(name = "away_score")
    private String awayScore;

    @XmlAttribute(name = "match_status_code")
    private String matchStatusCode;
}
