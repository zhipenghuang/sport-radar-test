package com.yunmu.uof.controller;

import com.yunmu.uof.entity.SoccerMatch;
import com.yunmu.uof.service.MatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("match")
@Slf4j
public class MatchController {

    @Autowired
    private MatchService matchService;

    @PostMapping(path = "/findMatches")
    public SoccerMatch findMatches(@RequestParam(name = "matchId") String matchId) {
        return matchService.findMatches(matchId);
    }

    @PostMapping(path = "/test10")
    public String test10(@RequestParam(name = "ss") String ss) {
        matchService.test(ss);
        log.info("ctr--------------");
        return "hello";
    }

    @PostMapping(path = "/getTimeLine")
    public String getTimeLine(@RequestParam(name = "matchId") String matchId) throws InterruptedException {
        matchService.fetchTimeLine(matchId);
        return "success";
    }
}
