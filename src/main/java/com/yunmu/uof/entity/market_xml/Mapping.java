package com.yunmu.uof.entity.market_xml;

import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "mapping")
@XmlType(propOrder = {"sportId", "sportName"})
@Data
@ToString
public class Mapping implements Serializable {

    @XmlAttribute(name = "sport_id")
    private String sportId;

    @XmlAttribute(name = "name")
    private String sportName;
}