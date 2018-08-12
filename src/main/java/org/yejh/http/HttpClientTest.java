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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpClientTest {
    public static String get(String url, Map<String, String> entityMap, String charset) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = "";
        try {
            StringBuffer sb = new StringBuffer();
            for (Map.Entry<String, String> entry : entityMap.entrySet()) {
                if (sb.length() > 0) {
                    sb.append("&");
                } else {
                    sb.append("?");
                }
                sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), charset));
            }
            url += sb.toString();

            // 创建httpGet.
            HttpGet httpget = new HttpGet(url);
            System.out.println("executing request: " + httpget.getURI());
            // 执行get请求.
            CloseableHttpResponse response = httpClient.execute(httpget);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                System.out.println("------------------------------------");
                // 打印响应状态
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    result = EntityUtils.toString(entity);
                    // 打印响应内容长度
                    System.out.println("Response content length: " + entity.getContentLength());
                    // 打印响应内容
                    System.out.println("Response content: " + result);
                }
                System.out.println("------------------------------------");
            } finally {
                response.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        return result;
    }

    public static String post(String url, Map<String, String> entityMap, String charset) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = "";
        // 创建httpPost
        HttpPost httppost = new HttpPost(url);
        System.out.println("executing request: " + httppost.getURI());
        // 创建参数队列
        List<NameValuePair> formParams = new ArrayList<>();

        for (Map.Entry<String, String> entry : entityMap.entrySet()) {
            formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        System.out.println("request body: " + formParams);

        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formParams, charset);
            httppost.setEntity(uefEntity);

            CloseableHttpResponse response = httpClient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity, charset);
                    System.out.println("--------------------------------------");
                    System.out.println(response.getStatusLine());
                    System.out.println("Response content: " + result);
                    System.out.println("--------------------------------------");
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String url = "http://v5.pc.duomi.com/search-ajaxsearch-searchall";
        Map<String, String> requestData = new HashMap<>();
        requestData.put("kw", "Java");

        HttpClientTest.get(url, requestData, "UTF-8");
        System.out.println("========================================");
        HttpClientTest.post(url, requestData, "UTF-8");
    }
}
