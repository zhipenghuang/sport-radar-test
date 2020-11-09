package com.yunmu.uof.test;

import cn.hutool.json.JSONUtil;
import com.yunmu.uof.DemoApplication;
import com.yunmu.uof.entity.AA;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class Other {

    @Test
    public void  test1(){
        AA aa = JSONUtil.toBean("{\n" +
                "  \"id\": \"47\",\n" +
                "  \"specifier\": \"\",\n" +
                "  \"timeStamp\": 1599046729087\n" +
                "}", AA.class);
        System.err.println(aa);
    }

    @Test
    public void  test2(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("ss").append("_").append("");

        System.err.println(buffer.toString());
    }
}
