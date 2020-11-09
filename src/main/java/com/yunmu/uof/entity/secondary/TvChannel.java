package com.yunmu.uof.entity.secondary;

import lombok.Data;

import java.util.Date;

@Data
public class TvChannel {

    public String name;

    public Date time;

    public String streamUrl;

    public TvChannel(String name, Date time, String streamUrl) {
        this.name = name;
        this.time = time;
        this.streamUrl = streamUrl;
    }
}
