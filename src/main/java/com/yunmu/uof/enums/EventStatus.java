package com.yunmu.uof.enums;

public enum EventStatus {

    NOT_STARTED("not_started"),//未开始
    LIVE("live"),//进行中
    ENDED("ended"),//比赛已结束，结果还未确认
    CLOSED("closed"),//结果已确认，比赛关闭
    CANCELLED("cancelled"),//取消
    POSTPONED("postponed"),//延时
    SUSPENDED("suspended"),//暂停
    INTERRUPTED("interrupted"),//中断
    DELAYED("delayed");//延期

    private String status;

    EventStatus(String status) {
        this.status = status;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
