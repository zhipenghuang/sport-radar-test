package com.yunmu.uof.entity.sport_xml;

import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "sports")
@XmlType(propOrder = {"sportXmls"})
@Data
@ToString
public class SportsXml implements Serializable {

    @XmlElement(name = "sport")
    private List<SportXml> sportXmls;
}
