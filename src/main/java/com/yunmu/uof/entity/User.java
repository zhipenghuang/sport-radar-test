package com.yunmu.uof.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class User implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private List<User> friends;
    private List<Car> cars;

    public User() {
    }

    public User(String email) {
        this.email = email;
    }
}
