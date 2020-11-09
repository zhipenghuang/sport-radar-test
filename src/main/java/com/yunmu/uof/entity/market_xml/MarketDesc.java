package com.yunmu.uof.entity.market_xml;

import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "market")
@XmlType(propOrder = {"id", "name", "groups", "outcomes", "specifiers", "sports","nameZh"})
@Data
@ToString
public class MarketDesc implements Serializable {

    @XmlAttribute(name = "id")
    private String id;

    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "nameZh")
    private String nameZh;

    @XmlAttribute(name = "groups")
    private String groups;

    @XmlElementWrapper(name = "outcomes")
    @XmlElement(name = "outcome")
    private List<OutCome> outcomes;

    @XmlElementWrapper(name = "specifiers")
    @XmlElement(name = "specifier")
    private List<Specifier> specifiers;

    @XmlElementWrapper(name = "mappings")
    @XmlElement(name = "mapping")
    private List<Mapping> sports;
}