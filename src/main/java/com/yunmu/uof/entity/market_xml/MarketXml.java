package com.yunmu.uof.entity.market_xml;

import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

// XML文件中的根标识
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "market_descriptions")
@XmlType(propOrder = {"market"})
@Data
@ToString
public class MarketXml implements Serializable {

    @XmlElement(name = "market")
    private List<MarketDesc> market;
}


