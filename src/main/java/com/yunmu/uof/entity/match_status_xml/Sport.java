package com.yunmu.uof.entity.match_status_xml;

import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "id")
@XmlType(propOrder = {"id"})
@Data
@ToString
public class Sport implements Serializable {

    @XmlAttribute(name = "id")
    private String id;
}
