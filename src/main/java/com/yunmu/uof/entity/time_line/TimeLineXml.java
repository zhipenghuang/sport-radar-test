package com.yunmu.uof.entity.time_line;

import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "match_timeline")
@XmlType(propOrder = {"matchId", "events", "sportEventStatus"})
@Data
@ToString
public class TimeLineXml implements Serializable {

    @XmlElement(name = "matchId")
    private String matchId;

    @XmlElementWrapper(name = "timeline")
    @XmlElement(name = "event")
    private List<TimeEventXml> events;

    @XmlElement(name = "sport_event_status")
    private SportEventStatus sportEventStatus;
}
