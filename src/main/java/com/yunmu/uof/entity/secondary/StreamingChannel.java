package com.yunmu.uof.entity.secondary;

import lombok.Data;

@Data
public class StreamingChannel {

    public int id;

    public String name;

    public StreamingChannel(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
