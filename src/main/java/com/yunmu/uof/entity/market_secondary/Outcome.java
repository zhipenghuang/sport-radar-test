package com.yunmu.uof.entity.market_secondary;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Outcome {

    @Field("id")
    public String id;

    public String name;
}
