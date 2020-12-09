package com.yunmu.uof.entity;

import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "match_timeline")
@XmlType(propOrder = {"events"})
@Data
@ToString
public class TimeLine implements Serializable {

    @XmlElementWrapper(name = "timeline")
    @XmlElement(name = "event")
    private List<TimeEvent> events;
}
