package com.yunmu.uof.entity.secondary;

import lombok.Data;

import java.util.List;

@Data
public class ProducerInfo {

    public List<ProducerInfoLink> producerInfoLinks;

    public List<StreamingChannel> streamingChannels;

    public boolean isAutoTraded;

    public boolean isInHostedStatistics;

    public boolean isInLiveCenterSoccer;

    public boolean isInLiveScore;
}
