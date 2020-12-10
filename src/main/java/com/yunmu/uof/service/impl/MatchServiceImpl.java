package com.yunmu.uof.service.impl;

import com.google.common.base.Stopwatch;
import com.yunmu.uof.dao.MatchDao;
import com.yunmu.uof.entity.SoccerMatch;
import com.yunmu.uof.service.AsyncTaskService;
import com.yunmu.uof.service.MatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

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
        Stopwatch stopwatch = Stopwatch.createStarted();
        List<Future<String>> futures = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            try {
                Future<String> future = asyncTaskService.asyncTestWithResult(i);
                futures.add(future);
            } catch (TaskRejectedException e) {
                log.error("rejected task");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.info("------------" + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms");
        for (Future future : futures) {
            try {
                String string = (String) future.get();
                log.info(string);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        stopwatch.stop();
        log.info("------------" + stopwatch.elapsed(TimeUnit.SECONDS) + "s");
        return "hello";
    }

}
