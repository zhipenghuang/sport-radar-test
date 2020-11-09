package com.yunmu.uof.entity.secondary;

import lombok.Data;

@Data
public class ProducerInfoLink {
    public String name;

    public String reference;

    public ProducerInfoLink(String name, String reference) {
        this.name = name;
        this.reference = reference;
    }
}
