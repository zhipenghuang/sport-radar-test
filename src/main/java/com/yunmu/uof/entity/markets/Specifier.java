package com.yunmu.uof.entity.markets;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class Specifier {

    private String name;

    private List<Outcome> outcomes;
}
