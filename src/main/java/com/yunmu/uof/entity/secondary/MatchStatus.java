package com.yunmu.uof.entity.secondary;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Data
public class MatchStatus {
    @Field("id")
    public Integer id;

    public String description;

    public BigDecimal awayScore;

    public BigDecimal homeScore;

    public String reportingStatus;

    public String WinnerId;
}
