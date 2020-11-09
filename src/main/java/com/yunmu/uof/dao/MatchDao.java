package com.yunmu.uof.dao;

import com.google.common.collect.Lists;
import com.yunmu.uof.entity.SoccerMatch;
import org.apache.commons.collections4.CollectionUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationExpression;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MatchDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public SoccerMatch findMatches(String matchId) {
        // 体育项目类型
        Criteria criteria = Criteria.where("_id").is(matchId);
//        // 盘口状态(活跃)/亚盘口状态(活跃)/盘口选项状态(活跃)
//        criteria.and("markets").elemMatch(
//                Criteria.where("status").is(0).and("specifiers").elemMatch(
//                        Criteria.where("isActive").is(true).and("outComes").elemMatch(
//                                Criteria.where("isActive").is(true))));
//        // 排序判断
//        Sort sort = Sort.by(Sort.Order.asc("matchScheduled"), Sort.Order.asc("_id"));
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
//                Aggregation.sort(sort),
//                Aggregation.skip(0L),
//                Aggregation.limit(30),
                Aggregation.project("markets").and(new AggregationExpression() {
                    @Override
                    public Document toDocument(AggregationOperationContext aggregationOperationContext) {
                        Document document = new Document();
                        document.append("$map",
                                new Document("input", new Document("$filter", new Document("input", "$markets")
                                        .append("as", "market")
                                        .append("cond", new Document("$eq", Lists.newArrayList("$$market.status", 0)))))
                                        .append("as", "markets")
                                        .append("in", new Document("_id", "$$markets._id")
                                                .append("status", "$$markets.status")
                                                .append("marketId", "$$markets.marketId")
                                                .append("marketName", "$$markets.marketName")
                                                .append("producer", "$$markets.producer")
                                                .append("groups", "$$markets.groups")
                                                .append("specifiers", new Document("$map",
                                                        new Document("input", new Document("$filter", new Document("input", "$$markets.specifiers")
                                                                .append("as", "specifier")
                                                                .append("cond", Lists.newArrayList("$$specifier.isActive", true))))
                                                                .append("as", "specifiers")
                                                                .append("in", new Document("specifierId", "$$specifiers.specifierId")
                                                                        .append("isActive", "$$specifiers.isActive")
                                                                        .append("specifier", "$$specifiers.specifier")
                                                                        .append("specifierMap", "$$specifiers.specifierMap")
                                                                        .append("isFavourite", "$$specifiers.isFavourite")
                                                                        .append("status", "$$specifiers.status")
                                                                        .append("outComes", new Document("$filter", new Document("input", "$$specifiers.outComes")
                                                                                .append("as", "outCome").
                                                                                        append("cond", new Document("$eq", Lists.newArrayList("$$outCome.isActive", true))))))))));
                        return document;
                    }
                }).as("markets"));
        AggregationResults<SoccerMatch> matchList = mongoTemplate.aggregate(aggregation, "soccer_match", SoccerMatch.class);
        List<SoccerMatch> matches = matchList.getMappedResults();
        return CollectionUtils.isEmpty(matches) ? null : matches.get(0);
    }
}
