package com.yunmu.uof.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "soccer_match")
public class SoccerMatch implements Serializable {
    /**
     * 比赛id
     */
    @Id
    private String id;
    /**
     * 盘口列表
     */
    private List<MarketDTO> markets = new ArrayList<>();
}
