package com.yunmu.uof.entity.secondary;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Competitor {

    public String abbreviation;

    public String country;

    public String countryCode;

    public String gender;

    public String name;

    @Field("id")
    public String id;
}
