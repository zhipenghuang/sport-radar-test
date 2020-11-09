package com.yunmu.uof.entity.secondary;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Season {
    @Field("id")
    public String id;

    public String name;
}
