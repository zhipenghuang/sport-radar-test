package com.yunmu.uof.entity.secondary;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class Fixture {
    public ProducerInfo producerInfo;

    public CoverageInfo coverageInfo;

    public Map<String, String> ExtraInfo;

    public Map<String, String> references;

    public Date nextLiveTime;

    public boolean startTimeConfirmed;

    public Date startTime;

    public boolean startTimeTbd;

    public ReplacedBy replacedBy;

    public List<ScheduledStartTimeChange> scheduledStartTimeChanges;

    public List<TvChannel> tvChannels;
}
