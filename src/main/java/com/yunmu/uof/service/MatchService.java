package com.yunmu.uof.service;

import com.yunmu.uof.entity.SoccerMatch;

public interface MatchService {

    SoccerMatch findMatches(String matchId);

    String test(String ss);

    String fetchTimeLine(String matchId) throws InterruptedException;
}
