package com.yunmu.uof.service;

import java.util.concurrent.Future;

public interface AsyncTaskService {

    void asyncTest(String ss);

    Future<String> asyncTestWithResult(int i);
}
