package com.yunmu.uof.entity.time_line;

import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "sport_event_status")
@XmlType(propOrder = {"homeScore", "awayScore", "periodScores"})
@Data
@ToString
public class SportEventStatus implements Serializable {

    @XmlAttribute(name = "status")
    private String status;

    @XmlAttribute(name = "home_score")
    private String homeScore;

    @XmlAttribute(name = "away_score")
    private String awayScore;

    @XmlElementWrapper(name = "period_scores")
    @XmlElement(name = "period_score")
    private List<PeriodScore> periodScores;
}
