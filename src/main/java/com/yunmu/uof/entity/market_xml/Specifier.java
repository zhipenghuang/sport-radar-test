package com.yunmu.uof.entity.market_xml;


import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "specifier")
@XmlType(propOrder = {"name", "type"})
@Data
@ToString
public class Specifier implements Serializable {

    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "type")
    private String type;

}
