package com.yunmu.uof.entity.match_status_xml;

import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "sports")
@XmlType(propOrder = {"all", "sports"})
@Data
@ToString
public class Sports implements Serializable {

    @XmlAttribute(name = "all")
    private String all;

    @XmlElement(name = "sport")
    private List<Sport> sports;
}
