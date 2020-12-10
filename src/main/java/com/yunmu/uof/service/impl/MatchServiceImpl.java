package com.yunmu.uof.service.impl;

import com.yunmu.uof.dao.MatchDao;
import com.yunmu.uof.entity.SoccerMatch;
import com.yunmu.uof.service.AsyncTaskService;
import com.yunmu.uof.service.MatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchDao matchDao;
    @Autowired
    private AsyncTaskService asyncTaskService;

    @Override
    public SoccerMatch findMatches(String matchId) {
        SoccerMatch match = matchDao.findMatches(matchId);
        return match;
    }

    @Override
    public String test(String ss) {
        for (int i = 0; i < 11; i++) {
            try {
                asyncTaskService.asyncTest(ss);
            } catch (TaskRejectedException e) {
                log.error("rejected task");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return "hello";
    }

}
