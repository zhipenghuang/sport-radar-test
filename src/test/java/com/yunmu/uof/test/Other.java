package com.yunmu.uof.test;

import cn.hutool.json.JSONUtil;
import com.yunmu.uof.DemoApplication;
import com.yunmu.uof.dao.MatchDao;
import com.yunmu.uof.entity.*;
import com.yunmu.uof.utils.ProtostuffUtil;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.runtime.RuntimeSchema;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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

    private static RuntimeSchema<User> schema = RuntimeSchema.createFrom(User.class);

    @Test
    public void test6() throws IOException {
        User user1 = new User();
        user1.setEmail("10000@qq.com");
        user1.setFirstName("zhang");
        user1.setLastName("sanfeng");
        List<User> users = new ArrayList<>();
        users.add(new User("20000@qq.com"));
        user1.setFriends(users);
        Car car1 = new Car("宾利");
        Car car2 = new Car("法拉利");
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        user1.setCars(cars);
        byte[] bytes = ProtostuffIOUtil.toByteArray(user1, schema,
                LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
        System.err.println("-----------proto length : " + bytes.length);
        User user2 = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(bytes, user2, schema);
        System.err.println(user2);

        String s = JSONUtil.toJsonStr(user1);
        System.err.println("-----------json length : " + s.getBytes().length);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(user1);
        os.flush();
        os.close();
        byte[] bytes1 = bos.toByteArray();
        System.err.println("-----------java ser length : " + bytes1.length);
        bos.close();

        //使用自定义的工具类
        byte[] bytes2 = ProtostuffUtil.serializer(user1);
        System.err.println("-----------自定义proto length : " + bytes2.length);
        User newUser = ProtostuffUtil.deserializer(bytes2, User.class);
        System.err.println(newUser);
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
