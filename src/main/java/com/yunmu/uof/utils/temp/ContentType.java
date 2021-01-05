package com.yunmu.uof.utils.temp;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2020-09-27 22:16
 */
public enum ContentType {

    FORM("application/x-www-from-urlencoded"),
    MULTIPART("multipart/form-data"),
    JSON("application/json");

    private final String value;
    private final static String ENCODING = "utf-8";
    ContentType(String value){
        this.value = value + ";charset=" +ENCODING;
    }
    @Override
    public String toString(){
        return value;
    }
}
