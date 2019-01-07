package org.yejh.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

public class MyHttpClient {
    private static final Logger LOG = LoggerFactory.getLogger(MyHttpClient.class);

    private static final String CHARSET = "UTF-8";

    public static String doHttpPost(String reqStr, String url, String contentType) {
        InputStream is = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);

            conn.setConnectTimeout(5000);// 连接主机的超时时间（单位：毫秒）
            conn.setReadTimeout(10000);// 从主机读取数据的超时时间（单位：毫秒）
            conn.setRequestProperty("Content-length", String.valueOf(reqStr.getBytes().length));

            if (contentType != null) {
                conn.setRequestProperty("Content-Type", contentType);
            }

            // 信任所有https证书
            if (conn instanceof HttpsURLConnection) {
                SSLContext ctx = SSLContext.getInstance("SSL");
                ctx.init(null, new TrustManager[]{
                        new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
                            }

                            @Override
                            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
                            }

                            @Override
                            public X509Certificate[] getAcceptedIssuers() {
                                return new X509Certificate[0];
                            }
                        }
                }, null);
                ((HttpsURLConnection) conn).setSSLSocketFactory(ctx.getSocketFactory());
                ((HttpsURLConnection) conn).setHostnameVerifier((s, sslSession) -> true);
            }

            LOG.info("请求接口地址: {}, 请求数据: {}", url, reqStr);

            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), CHARSET);
            osw.write(reqStr);
            osw.flush();
            osw.close();

            // Http OK
            if (conn.getResponseCode() / 100 == 2) {
                is = conn.getInputStream();
            } else {
                is = conn.getErrorStream();
            }

            byte[] bis = toByteArray(is);
            String respStr = new String(bis, CHARSET);
            LOG.info("请求接口地址: {}, 返回数据: {}", url, respStr);
            return respStr;
        } catch (Exception e) {
            LOG.error("请求接口地址: {}, 异常: {}", url, e.getMessage(), e);
            return null;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                LOG.error("关闭URL链接: {}, 异常: {}", url, e.getMessage(), e);
                return null;
            }
        }
    }

    public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            // 打开和URL之间的连接
            URLConnection urlConnection = new URL(url).openConnection();
            // 设置请求属性
            urlConnection.setRequestProperty("accept", "*/*");
            urlConnection.setRequestProperty("connection", "Keep-Alive");
            urlConnection.setRequestProperty("Content-Type", "UTF-8");
            urlConnection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            urlConnection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = urlConnection.getHeaderFields();
            // 遍历所有的响应头字段
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                LOG.info("HttpClientHelper-sendGet： {}--->{}", entry.getKey(), entry.getValue());
            }
            // BufferedReader输入流读取URL的响应
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), CHARSET));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            LOG.error("发送GET请求出现异常! {}", e.getMessage(), e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                LOG.error("关闭URL链接: {}, 异常: {}", url, e.getMessage(), e);
            }
        }
        return result;
    }

    private static byte[] toByteArray(InputStream is) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024 * 4];
            int length;
            while ((length = is.read(buffer)) != -1) {
                bos.write(buffer, 0, length);
            }
            return bos.toByteArray();
        }
    }
}
