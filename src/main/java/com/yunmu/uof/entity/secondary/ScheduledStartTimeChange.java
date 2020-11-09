package com.yunmu.uof.entity.secondary;

import lombok.Data;

import java.util.Date;

@Data
public class ScheduledStartTimeChange {

    public Date changeAt;

    public Date newTime;

    public Date oldTime;

    public ScheduledStartTimeChange(Date changeAt, Date newTime, Date oldTime) {
        this.changeAt = changeAt;
        this.newTime = newTime;
        this.oldTime = oldTime;
    }
}
