package com.yunmu.uof.utils;

import com.yunmu.uof.entity.TimeEvent;
import com.yunmu.uof.entity.TimeLine;
import com.yunmu.uof.entity.market_xml.MarketDesc;
import com.yunmu.uof.entity.market_xml.MarketXml;
import com.yunmu.uof.entity.match_status_xml.MatchStatus;
import com.yunmu.uof.entity.match_status_xml.MatchStatusDesc;
import com.yunmu.uof.entity.sport_xml.SportXml;
import com.yunmu.uof.entity.sport_xml.SportsXml;
import com.yunmu.uof.enums.SportDataType;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName: SportApiStaticDataClient
 * @Description:TODO(sport 静态数据 api客户端)
 * @author: wanglan
 */
@Configuration
public class FetchStaticDataManager {

    /**
     * 访问token
     */
    @Value("${sportradar.accessToken:sARnisHjRXjNb48FKE}")
    private String accessToken = "sARnisHjRXjNb48FKE";


    /**
     * @throws
     * @Title: getSchedulePage
     * @Description: TODO(分页获取所有赛程)
     * @param: @param language
     * @param: @return
     * @return: JSONObject
     * @Author: wanglan
     */
    public List<MatchStatus> getMatchStatus() {
        String url = SportDataType.MATCH_STATUS.getApi();
        try {
            //获取结果集
            String result = this.dataFetch(url);
            //如果结果集不为空则封装
            if (result != null) {
                MatchStatusDesc matchStatus = (MatchStatusDesc) XMLUtil.convertXmlStrToObject(MatchStatusDesc.class, result);
                return matchStatus.getMatchStatuses();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @throws
     * @Title: getAllMarket
     * @Description: TODO(获取所有盘口)
     * @param: @param language
     * @param: @return
     * @return: JSONObject
     * @Author: wanglan
     */
    public List<MarketDesc> getAllMarket() {
        try {
            //获取结果集
            String markets_result = this.dataFetch(SportDataType.SOCCER_MARKET.getApi().replace("LANGUAGE", "en"));
            String markets_result_zh = this.dataFetch(SportDataType.SOCCER_MARKET.getApi().replace("LANGUAGE", "zh"));

            MarketXml marketXml_zh = (MarketXml) XMLUtil.convertXmlStrToObject(MarketXml.class, markets_result_zh);
            Map<String, MarketDesc> marketDescMap = marketXml_zh.getMarket().stream().collect(Collectors.toMap(MarketDesc::getId, MarketDesc -> MarketDesc));

            String sports_result = this.dataFetch(SportDataType.ALL_SPORT.getApi());

            SportsXml sportsXml = (SportsXml) XMLUtil.convertXmlStrToObject(SportsXml.class, sports_result);
            Map<String, String> sportsMap = sportsXml.getSportXmls().stream().collect(Collectors.toMap(SportXml::getId, SportXml::getName));

            //如果结果集不为空则封装
            if (markets_result != null) {
                MarketXml marketXml = (MarketXml) XMLUtil.convertXmlStrToObject(MarketXml.class, markets_result);
                List<MarketDesc> list = new ArrayList<>();
                marketXml.getMarket().forEach(marketDesc -> {
                    if (marketDesc.getSports() != null) {
//                        marketDesc.getMappings().parallelStream().filter(m -> m.getSport_id().equals("sr:sport:1")).findFirst()
//                                .ifPresent(a -> list.add(marketDesc));
                        marketDesc.getSports().forEach(mapping -> {
                            mapping.setSportName(sportsMap.get(mapping.getSportId()));
                        });
                    }
                    marketDesc.setNameZh(marketDescMap.get(marketDesc.getId()).getName());
                    if (marketDesc.getOutcomes() != null) {
                        marketDesc.getOutcomes().forEach(outCome -> {
                            String name = marketDescMap.get(marketDesc.getId()).getOutcomes().stream().filter(s -> s.getId().equals(outCome.getId())).findFirst().get().getName();
                            outCome.setNameZh(name);
                        });
                    }
                    list.add(marketDesc);
                });
                return list;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<TimeEvent> getTimeLine(String matchId) {
        if (StringUtils.isBlank(matchId)) {
            return null;
        }
        String url = SportDataType.TIME_LINE.getApi().replace("MATCHID", matchId);
        try {
            // 获取结果集
            String result = this.dataFetch(url);
            // 如果结果集不为空则封装
            if (result != null) {
                TimeLine timeLine = (TimeLine) XMLUtil.convertXmlStrToObject(TimeLine.class, result);
                return timeLine.getEvents();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @throws IOException
     * @throws
     * @Title: commonFetch
     * @Description: TODO(通用获取)
     * @param: @param type
     * @param: @return
     * @return: Response
     * @Author: wanglan
     * @date: 2020年7月17日 上午10:46:25
     */
    private String dataFetch(String url) throws IOException {

        OkHttpUtils instance = OkHttpUtils.getInstance();

        Map<String, String> headMap = new HashMap<>();
        headMap.put("x-access-token", accessToken);

        Response resp = instance.getData(url, headMap);

        if (resp.code() == OkHttpUtils.CODE_SUCCESS) {
            //1.响应结果集
            String str = resp.body().string();
            return str;
        }
        return null;
    }
}
