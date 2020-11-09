package com.yunmu.uof.entity.markets;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@ToString
public class MarketDb {

    @Field("id")
    private int id;

    private String name;

    private String status;

    private Boolean isFavourite;

    private List<Specifier> specifiers;
}
