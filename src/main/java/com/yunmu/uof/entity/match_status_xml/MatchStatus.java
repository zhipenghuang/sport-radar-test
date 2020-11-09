package com.yunmu.uof.entity.match_status_xml;

import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "match_status")
@XmlType(propOrder = {"id", "description", "period_number", "sports"})
@Data
@ToString
public class MatchStatus implements Serializable {

    @XmlAttribute(name = "id")
    private String id;

    @XmlAttribute(name = "description")
    private String description;

    @XmlAttribute(name = "period_number")
    private String period_number;

    @XmlElement(name = "sports")
    private List<Sports> sports;
}
