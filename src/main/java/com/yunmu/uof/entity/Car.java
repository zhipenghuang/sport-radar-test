package com.yunmu.uof.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class Car implements Serializable {

    private String color;
    private String car_name;
    private Integer price;

    public Car(String car_name) {
        super();
        this.car_name = car_name;
    }
}
