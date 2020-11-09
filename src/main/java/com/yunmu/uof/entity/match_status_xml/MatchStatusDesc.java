package com.yunmu.uof.entity.match_status_xml;

import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "match_status_descriptions")
@XmlType(propOrder = {"matchStatuses"})
@Data
@ToString
public class MatchStatusDesc implements Serializable {

    @XmlElement(name = "match_status")
    private List<MatchStatus> matchStatuses;
}
