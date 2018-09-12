package org.yejh.http;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

public class HttpClientTest {
    public static String get(String url, Map<String, String> entityMap, String charset) throws IOException {
        String result = "";

        StringBuilder sb = new StringBuilder();

        entityMap.forEach((k, v) -> {
            if (sb.length() == 0) {
                sb.append(url).append("?");
            } else {
                sb.append("&");
            }
            try {
                sb.append(k).append("=").append(URLEncoder.encode(v, charset));
            } catch (UnsupportedEncodingException e) {
                System.err.println(e);
            }
        });
        // 创建httpGet.
        HttpGet httpget = new HttpGet(sb.toString());
        System.out.println("request url: " + httpget.getURI());

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // 执行get请求
            try (CloseableHttpResponse response = httpClient.execute(httpget)) {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                System.out.println("------------------------------------");
                // 打印响应状态
                System.out.println(response.getStatusLine());
                if (Objects.nonNull(entity)) {
                    result = EntityUtils.toString(entity, charset);
                    // 打印响应内容长度
                    System.out.println("response content length: " + entity.getContentLength());
                    // 打印响应内容
                    System.out.println("response content: " + result);
                }
                System.out.println("------------------------------------");
            }
        }
        return result;
    }

    public static String post(String url, Map<String, String> entityMap, String charset) throws IOException {
        String result = "";
        // 创建httpPost
        HttpPost httppost = new HttpPost(url);
        System.out.println("request url: " + httppost.getURI());
        // 创建参数队列
        List<NameValuePair> formParams = new ArrayList<>();

        entityMap.forEach((k, v) -> formParams.add(new BasicNameValuePair(k, v)));
        System.out.println("request body: " + formParams);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            httppost.setEntity(new UrlEncodedFormEntity(formParams, charset));
            // 执行post请求
            try (CloseableHttpResponse response = httpClient.execute(httppost)) {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                System.out.println("--------------------------------------");
                // 打印响应状态
                System.out.println(response.getStatusLine());
                if (Objects.nonNull(entity)) {
                    result = EntityUtils.toString(entity);
                    // 打印响应内容长度
                    System.out.println("response content length: " + entity.getContentLength());
                    // 打印响应内容
                    System.out.println("response content: " + result);
                }
                System.out.println("--------------------------------------");
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        String url = "http://apis.juhe.cn/mobile/get";
        Map<String, String> requestData = new HashMap<>();
        requestData.put("phone", "13888888888");
        requestData.put("key", "abcdefgh");
        requestData.put("foo", "中文");

        HttpClientTest.get(url, requestData, "UTF-8");
        System.out.println("========================================");
        HttpClientTest.post(url, requestData, "UTF-8");
    }
}
