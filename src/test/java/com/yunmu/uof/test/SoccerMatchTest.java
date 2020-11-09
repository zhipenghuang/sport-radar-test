package com.yunmu.uof.test;

import com.google.common.collect.Lists;
import com.sportradar.unifiedodds.sdk.OddsFeed;
import com.sportradar.unifiedodds.sdk.SportsInfoManager;
import com.sportradar.unifiedodds.sdk.cfg.OddsFeedConfiguration;
import com.yunmu.uof.DemoApplication;
import com.yunmu.uof.constant.SdkConstants;
import com.yunmu.uof.entity.MatchResultConfig;
import com.yunmu.uof.entity.market_xml.MarketDesc;
import com.yunmu.uof.entity.match_status_xml.MatchStatus;
import com.yunmu.uof.entity.match_status_xml.Sports;
import com.yunmu.uof.entity.time_line.TimeEventXml;
import com.yunmu.uof.entity.time_line.TimeLineXml;
import com.yunmu.uof.enums.MatchResultType;
import com.yunmu.uof.listener.GlobalEventsListener;
import com.yunmu.uof.utils.FetchStaticDataManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class SoccerMatchTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void test() {
        final OddsFeed oddsFeed;
        OddsFeedConfiguration configuration = OddsFeed.getOddsFeedConfigurationBuilder()
                .setAccessToken("sARnisHjRXjNb48FKE")
                .selectIntegration()
                .setSdkNodeId(SdkConstants.NODE_ID)
                .setDefaultLocale(Locale.CHINESE)
                .build();

        log.info("Creating a new OddsFeed instance");
        oddsFeed = new OddsFeed(new GlobalEventsListener(), configuration);
        SportsInfoManager sportsInfoManager = oddsFeed.getSportsInfoManager();

    }

    @Test
    public void test3() {
        FetchStaticDataManager client = new FetchStaticDataManager();
        List<MarketDesc> markets = client.getAllMarket();
        if (markets != null) {
            mongoTemplate.insert(markets, "market_all_new");
        }
    }

    @Test
    public void test4() {
        String startDate = "2020-07-24T12:25:00.000Z";
        Date date = utcToLocal(startDate);
        Date now = new Date();

        System.err.println(now.after(date));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String format = sdf.format(now);
        System.err.println(format);
    }

    public Date utcToLocal(String utcTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date utcDate = null;
        try {
            utcDate = sdf.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf.setTimeZone(TimeZone.getDefault());
        Date locatlDate = null;
        String localTime = sdf.format(utcDate.getTime());
        try {
            locatlDate = sdf.parse(localTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return locatlDate;
    }

    @Test
    public void test5() {
        String s = "<bet_settlement certainty=\\\"2\\\" product=\\\"3\\\" event_id=\\\"sr:match:22972609\\\" timestamp=\\\"1596832974369\\\"><outcomes><market id=\\\"11\\\"><outcome id=\\\"4\\\" result=\\\"1\\\"/><outcome id=\\\"5\\\" result=\\\"0\\\"/></market><market id=\\\"1\\\"><outcome id=\\\"1\\\" result=\\\"0\\\"/><outcome id=\\\"3\\\" result=\\\"1\\\"/><outcome id=\\\"2\\\" result=\\\"0\\\"/></market><market id=\\\"10\\\"><outcome id=\\\"10\\\" result=\\\"1\\\"/><outcome id=\\\"9\\\" result=\\\"0\\\"/><outcome id=\\\"11\\\" result=\\\"0\\\"/></market></outcomes></bet_settlement>";
        String s1 = s.replaceAll("\\\\", "");
        System.err.println(s1);
    }

    @Test
    public void test6() {
        FileInputStream fileInputStream = null;
        Workbook workbook = null;
        File file = new File("C:\\Users\\Administrator\\Downloads\\Telegram Desktop\\market_all_new.xls");
        //获取workbook对象
        try {
            fileInputStream = new FileInputStream(file);
            workbook = new HSSFWorkbook(fileInputStream);
        } catch (IOException e) {
            e.getStackTrace();
        }
        //得到一个工作表
        Sheet sheet = workbook.getSheetAt(0);
        Map<String, String> markets = new HashMap<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            //获得每一行数据
            Row row = sheet.getRow(i);
            Cell idCell = row.getCell(0);
            Cell groupsCell = row.getCell(4);
            markets.put(idCell.getStringCellValue(), groupsCell.getStringCellValue());
        }
        //关闭文件输入流
        try {
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> spec = Lists.newArrayList("49", "50", "51", "52", "53", "54", "56", "57", "58", "59", "818", "819");
        MatchResultConfig cfg1 = new MatchResultConfig();
        cfg1.setSportId("sr:sport:1");
        cfg1.setType(MatchResultType.FULL_TIME.getType());
        cfg1.setMarketIds(new ArrayList<>());
        MatchResultConfig cfg2 = new MatchResultConfig();
        cfg2.setSportId("sr:sport:1");
        cfg2.setType(MatchResultType.FIRST_HALF.getType());
        cfg2.setMarketIds(new ArrayList<>());
        MatchResultConfig cfg3 = new MatchResultConfig();
        cfg3.setSportId("sr:sport:1");
        cfg3.setType(MatchResultType.SECOND_HALF.getType());
        cfg3.setMarketIds(new ArrayList<>());
        MatchResultConfig cfg4 = new MatchResultConfig();
        cfg4.setSportId("sr:sport:1");
        cfg4.setType(MatchResultType.TWO_HALF.getType());
        cfg4.setMarketIds(new ArrayList<>());
        for (Map.Entry<String, String> entry : markets.entrySet()) {
            if (entry.getValue().contains("1st_half")) {
                cfg2.getMarketIds().add(entry.getKey());
            } else if (entry.getValue().contains("2nd_half")) {
                cfg3.getMarketIds().add(entry.getKey());
            } else if (spec.stream().filter(m -> m.equals(entry.getKey())).findFirst().isPresent()) {
                cfg4.getMarketIds().add(entry.getKey());
            } else {
                cfg1.getMarketIds().add(entry.getKey());
            }

        }
        mongoTemplate.insert(cfg1);
        mongoTemplate.insert(cfg2);
        mongoTemplate.insert(cfg3);
        mongoTemplate.insert(cfg4);
        System.err.println(markets);
    }

    @Test
    public void test7() {
        FetchStaticDataManager client = new FetchStaticDataManager();
        List<MatchStatus> matchStatus = client.getMatchStatus();
        mongoTemplate.insert(matchStatus, "match_status");
    }

    @Test
    public void test8() {
        List<MatchStatus> match_status = mongoTemplate.findAll(MatchStatus.class, "match_status");
        BulkOperations bulkOps = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, "match_status");
        for (MatchStatus matchStatus : match_status) {
            Sports sports = matchStatus.getSports().get(0);
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(matchStatus.getId()));

            if (sports.getAll().equals("true")) {
                Document document = new Document();
                document.append("sports.all", true);
                document.append("sports.sports", null);
                bulkOps.upsert(query, Update.fromDocument(new Document().append("$set", document)));
            } else {
                Document document = new Document();
                document.append("sports.all", null);
                document.append("sports.sports", sports.getSports());
                bulkOps.upsert(query, Update.fromDocument(new Document().append("$set", document)));
            }
        }
        bulkOps.execute();
    }

    @Test
    public void test9() {
        FetchStaticDataManager client = new FetchStaticDataManager();
        TimeLineXml timeLine = client.getTimeLine("sr:match:23893925");
        for (TimeEventXml eventXml : timeLine.getEvents()) {
            System.err.println(eventXml);
        }
        timeLine.setMatchId("sr:match:23893925");
        System.err.println(timeLine.getSportEventStatus());
        mongoTemplate.insert(timeLine, "match_time_line");
    }
}

