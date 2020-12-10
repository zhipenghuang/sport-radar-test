package com.yunmu.uof.service.impl;

import com.yunmu.uof.service.AsyncTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
@Slf4j
public class AsyncTaskServiceImpl implements AsyncTaskService {

    @Override
    @Async("executor1")
    public void asyncTest(String ss) {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("service---------------------------");
    }

    @Override
    @Async("executor1")
    public Future<String> asyncTestWithResult(int i) {
        try {
            // 这个方法需要调用500毫秒
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 消息汇总
        return new AsyncResult<>(String.format("这个是第{%s}个异步调用的证书", i));
    }
}
