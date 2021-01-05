package com.yunmu.uof.utils.httpclient;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpclientUtil {

    public static String get(String url, String accessToken) throws IOException {
        String result;
        //发送get请求
        HttpGet requestGet = new HttpGet(url);
        //4、发送接口
        CloseableHttpClient client = HttpClients.createDefault();
        requestGet.addHeader("x-access-token", accessToken);

        //5、接收响应结果
        CloseableHttpResponse response = client.execute(requestGet);
        if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK && response.getEntity() != null) {
            result = EntityUtils.toString(response.getEntity());
            return result;
        }
        return null;
    }
}

