package com.yunmu.uof.service.impl;

import com.yunmu.uof.dao.MatchDao;
import com.yunmu.uof.entity.SoccerMatch;
import com.yunmu.uof.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchDao matchDao;

    @Override
    public SoccerMatch findMatches(String matchId) {
        SoccerMatch match = matchDao.findMatches(matchId);
        return match;
    }
}
