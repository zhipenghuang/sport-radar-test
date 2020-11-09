package com.yunmu.uof.entity.secondary;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Tournament {

    @Field("id")
    public String id;

    public String name;

    public Category category;

    public Tournament() {
        this.category = new Category();
    }
}
