package com.yunmu.uof.service;

import com.yunmu.uof.entity.markets.SoccerMatchDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service("marketService")
public class MarketService {

    @Autowired
    private MongoTemplate mongoTemplate;


    public void handleMarkets(SoccerMatchDb soccerMatch) {
//        Query query = new Query();
//        query.addCriteria(Criteria.where("_id").is(soccerMatch.getMatchId()));
//
//        Document document = new Document();
//        mongoTemplate.getConverter().write(soccerMatch, document);
//        UpdateResult result = mongoTemplate.upsert(query, Update.fromDocument(new Document().append("$set", document)), "soccer_match_ym");
//        System.out.println(result.getUpsertedId());
        mongoTemplate.insert(soccerMatch,"soccer_match");
    }
}
