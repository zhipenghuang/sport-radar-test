package com.yunmu.uof.entity.sport_xml;

import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "sport")
@XmlType(propOrder = {"id","name"})
@Data
@ToString
public class SportXml implements Serializable {

    @XmlAttribute(name = "id")
    private String id;

    @XmlAttribute(name = "name")
    private String name;
}
