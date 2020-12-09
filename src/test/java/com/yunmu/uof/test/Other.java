package com.yunmu.uof.test;

import com.yunmu.uof.DemoApplication;
import com.yunmu.uof.dao.MatchDao;
import com.yunmu.uof.entity.MarketDTO;
import com.yunmu.uof.entity.MatchResultConfig;
import com.yunmu.uof.entity.SoccerMatch;
import com.yunmu.uof.entity.SpecifierDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class Other {

    @Autowired
    private MatchDao matchDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void test1() {
        matchDao.findMatches("sr:match:22434613");
    }

    @Test
    public void test2() {
        List<MatchResultConfig> all = mongoTemplate.findAll(MatchResultConfig.class);
        List<MatchResultConfig> collect = all.stream().sorted(
                Comparator.comparing(MatchResultConfig::getSportId, Comparator.comparing(x -> Integer.valueOf(x.split(":")[2])))
                        .thenComparing(MatchResultConfig::getId, Comparator.comparing(x -> Integer.valueOf(x.split("_")[1]))))
                .collect(Collectors.toList());
        collect.forEach(config -> {
            List<String> markets = config.getMarketIds().stream().sorted(Comparator.comparing(Integer::valueOf)).collect(Collectors.toList());
            config.setMarketIds(markets);
        });
        mongoTemplate.remove(new Query(), MatchResultConfig.class);
        mongoTemplate.insertAll(collect);
    }

    @Test
    public void test3() {
        String specifier = "from=1|to=15|total=0.5";
        Map<String, Integer> map = parseQuarterViaSpecifier(specifier);
        if (CollectionUtils.isEmpty(map) || map.get("from") == null || map.get("to") == null) {
            System.err.println("err");
        }
        System.err.println(map.get("from"));
        System.err.println(map.get("to"));
    }

    @Test
    public void test4() {
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("sss");
        String join = StringUtils.join(list, ",");
        String[] split = join.split(",");
        System.err.println(join);
        System.out.println(split.length);
        Optional<String> first = Arrays.stream(split).filter(StringUtils::isBlank).findFirst();
        System.err.println(first.isPresent());
    }

    @Test
    public void test5() {
        final SoccerMatch soccerMatch = new SoccerMatch();
        final SpecifierDTO specifierDTO = new SpecifierDTO();
        specifierDTO.setSpecifier("sssssss");
        final MarketDTO marketDTO = new MarketDTO();
        marketDTO.getSpecifiers().add(specifierDTO);
        soccerMatch.getMarkets().add(marketDTO);
        for (MarketDTO marketDTO1 : soccerMatch.getMarkets()) {
            for (SpecifierDTO specifierDTO1 : marketDTO1.getSpecifiers()) {
                specifierDTO1.setSpecifier("111111111111");
            }
        }
        System.err.println(soccerMatch);
    }

    private Map<String, Integer> parseQuarterViaSpecifier(String specifier) {
        String[] split = specifier.split("\\|");
        Map<String, Integer> map = new HashMap<>();
        for (String s : split) {
            if (s.contains("from=")) {
                String quarterStr = s.split("=")[1];
                if (StringUtils.isBlank(quarterStr)) {
                    return null;
                }
                map.put("from", Integer.parseInt(quarterStr));
            }
            if (s.contains("to=")) {
                String quarterStr = s.split("=")[1];
                if (StringUtils.isBlank(quarterStr)) {
                    return null;
                }
                map.put("to", Integer.parseInt(quarterStr));
            }
        }
        return map;
    }
}
