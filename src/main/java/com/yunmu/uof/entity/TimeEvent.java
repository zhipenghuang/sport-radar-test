package com.yunmu.uof.entity;

import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "event")
@XmlType(
    propOrder = {
      "id",
      "type",
      "time",
      "match_time",
      "match_clock",
      "team",
      "x",
      "y",
      "period",
      "home_score",
      "away_score",
      "match_status_code"
    })
@Data
@ToString
public class TimeEvent implements Serializable {

  @XmlAttribute(name = "id")
  private String id;

  @XmlAttribute(name = "type")
  private String type;

  @XmlAttribute(name = "time")
  private String time;

  @XmlAttribute(name = "match_time")
  private String match_time;

  @XmlAttribute(name = "match_clock")
  private String match_clock;

  @XmlAttribute(name = "team")
  private String team;

  @XmlAttribute(name = "x")
  private String x;

  @XmlAttribute(name = "y")
  private String y;

  @XmlAttribute(name = "period")
  private String period;

  @XmlAttribute(name = "home_score")
  private String home_score;

  @XmlAttribute(name = "away_score")
  private String away_score;

  @XmlAttribute(name = "match_status_code")
  private String match_status_code;
}
