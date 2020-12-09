package com.yunmu.uof.service.impl;

import com.yunmu.uof.dao.MatchDao;
import com.yunmu.uof.entity.SoccerMatch;
import com.yunmu.uof.service.MatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchDao matchDao;

    @Override
    public SoccerMatch findMatches(String matchId) {
        SoccerMatch match = matchDao.findMatches(matchId);
        return match;
    }

    @Async("executor1")
    @Override
    public String test(String ss) {

        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("service---------------------------");
        return "ss";
    }
}
