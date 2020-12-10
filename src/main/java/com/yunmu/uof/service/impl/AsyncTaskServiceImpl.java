package com.yunmu.uof.service.impl;

import com.yunmu.uof.service.AsyncTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AsyncTaskServiceImpl implements AsyncTaskService {

    @Async("executor1")
    public void asyncTest(String ss) {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("service---------------------------");
    }
}
