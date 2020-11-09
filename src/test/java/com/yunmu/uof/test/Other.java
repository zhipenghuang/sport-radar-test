package com.yunmu.uof.test;

import com.yunmu.uof.DemoApplication;
import com.yunmu.uof.dao.MatchDao;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class Other {

    @Autowired
    private MatchDao matchDao;

    @Test
    public void test1() {
        matchDao.findMatches("sr:match:22434613");
    }
}
