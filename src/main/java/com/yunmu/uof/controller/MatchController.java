package com.yunmu.uof.controller;

import com.yunmu.uof.entity.SoccerMatch;
import com.yunmu.uof.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("match")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @PostMapping(path = "/findMatches")
    public SoccerMatch findMatches(@RequestParam(name = "matchId") String matchId) {
        return matchService.findMatches(matchId);
    }
}
