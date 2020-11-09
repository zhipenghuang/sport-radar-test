package com.yunmu.uof.entity.markets;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Data
@ToString
public class Outcome {

    @Field("id")
    private String id;

    private String name;

    private Boolean isActive;

    private BigDecimal odds;

    private BigDecimal probability;

    private Boolean isPlayerOutcome;
}
