package com.yunmu.uof.entity.time_line;

import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "period_score")
@XmlType(propOrder = {"number", "homeScore", "awayScore", "type"})
@Data
@ToString
public class PeriodScore implements Serializable {

    @XmlAttribute(name = "number")
    private String number;

    @XmlAttribute(name = "home_score")
    private String homeScore;

    @XmlAttribute(name = "away_score")
    private String awayScore;

    @XmlAttribute(name = "type")
    private String type;

}
