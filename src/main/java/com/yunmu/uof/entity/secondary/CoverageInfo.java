package com.yunmu.uof.entity.secondary;

import lombok.Data;

import java.util.List;

@Data
public class CoverageInfo {

    public String coveredFrom;

    public List<String> includes;

    public String level;

    public boolean isLive;
}
