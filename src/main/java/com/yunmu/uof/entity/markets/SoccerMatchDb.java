package com.yunmu.uof.entity.markets;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@ToString
@Document(collection = "soccer_match_ym")
public class SoccerMatchDb {

    private String matchId;

    private List<MarketDb> markets;
}
