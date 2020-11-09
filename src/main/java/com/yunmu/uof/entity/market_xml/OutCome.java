package com.yunmu.uof.entity.market_xml;

import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "outcome")
@XmlType(propOrder = {"id", "name", "nameZh"})
@Data
@ToString
public class OutCome implements Serializable {

    @XmlAttribute(name = "id")
    private String id;

    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "nameZh")
    private String nameZh;
}