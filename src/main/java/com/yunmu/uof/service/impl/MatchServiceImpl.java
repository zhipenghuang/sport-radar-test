package com.yunmu.uof.service.impl;

import com.google.common.base.Stopwatch;
import com.yunmu.uof.dao.MatchDao;
import com.yunmu.uof.entity.SoccerMatch;
import com.yunmu.uof.entity.TimeEvent;
import com.yunmu.uof.service.AsyncTaskService;
import com.yunmu.uof.service.MatchService;
import com.yunmu.uof.utils.CustomThreadFactory;
import com.yunmu.uof.utils.FetchStaticDataManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
@Slf4j
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchDao matchDao;
    @Autowired
    private AsyncTaskService asyncTaskService;
    @Autowired
    private FetchStaticDataManager fetchStaticDataManager;

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
                String string = (String) future.get(500L, TimeUnit.SECONDS);
                log.info(string);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        stopwatch.stop();
        log.info("------------" + stopwatch.elapsed(TimeUnit.SECONDS) + "s");
        return "hello";
    }

    private static final int threads = 50;

    @Override
    public String fetchTimeLine(String matchId) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(threads);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(threads, threads, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(threads),
                new CustomThreadFactory("云木pool"), new ThreadPoolExecutor.AbortPolicy());
        executor.prestartAllCoreThreads();
        for (int i = 0; i < threads; i++) {
            executor.submit(() -> {
                List<TimeEvent> timeEventsXml = fetchStaticDataManager.getTimeLine("sr:match:25112718");
                log.info(timeEventsXml == null ? null : timeEventsXml.toString());
                countDownLatch.countDown();
            });
        }
        //等待计算线程执行完
        countDownLatch.await();
        executor.shutdown();
        return null;
    }

}
