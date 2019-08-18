package org.yejh.http;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 基于 JDK 自带的 {@link URLConnection} 实现的 http(s) 请求器，不依赖任何第三方框架
 *
 * @author <a href="mailto:yejh.1248@qq.com">Ye Jiahao</a>
 * @create 2019-08-18
 * @since x.y.z
 */
public class MyHttpRequester {

    private String url;
    private Map<String, String> headers = new LinkedHashMap<>();
    private Map<String, Object> formFields = new LinkedHashMap<>();
    private String content;
    private String method = "GET";
    private String charset = "UTF-8";
    private int connectTimeout = 5000;
    private int readTimeout = 10000;

    public String sendRequest() {
        HttpURLConnection conn = null;
        try {
            String buildUrl = buildUrl(url);
            URL requestUrl = new URL(buildUrl);

            System.out.println("request url: " + buildUrl);
            System.out.println("request method: " + method);
            System.out.println("request headers: " + headers);
            System.out.println("request parameters: " + formFields);
            System.out.println("request content: " + content);

            conn = (HttpURLConnection) requestUrl.openConnection();

            acceptAllHttps(conn);       // 信任所有 https 证书
            setRequestProperty(conn);   // 设置 RequestProperty
            doSettingAndConnect(conn);  // 设置属性并且尝试链接
            writeRequestBody(conn);     // 设置表单参数或请求内容
            finish(conn);               // 完成请求

            String response = getResponseResult(conn);

            System.out.println("response: " + response);

            return response;
        } catch (IOException e) {
            System.err.println("request error: " + e);
            return null;
        } finally {
            if (Objects.nonNull(conn)) {
                conn.disconnect();
            }
        }
    }

    private String buildUrl(String url) throws UnsupportedEncodingException {
        if ("GET".equalsIgnoreCase(method)) {
            if (url.contains("?")) {
                return url + "&" + formFieldsToUrl();
            }
            return url + "?" + formFieldsToUrl();
        }
        return url;
    }

    private void acceptAllHttps(HttpURLConnection conn) {
        if (conn instanceof HttpsURLConnection) {
            ((HttpsURLConnection) conn).setSSLSocketFactory(DummySSLSocketFactory.getDefault());
            ((HttpsURLConnection) conn).setHostnameVerifier((hostName, sslSession) -> true);
        }
    }

    private void doSettingAndConnect(HttpURLConnection conn) throws IOException {
        boolean isNotMethodGet = !"GET".equalsIgnoreCase(method);

        conn.setRequestMethod(method);
        conn.setConnectTimeout(connectTimeout);
        conn.setReadTimeout(readTimeout);
        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(isNotMethodGet);
        conn.connect();
    }

    private void writeRequestBody(HttpURLConnection conn) throws IOException {
        if ("GET".equalsIgnoreCase(method)) { // get 请求没有 body
            return;
        }
        String requestBody = null;
        if (!formFields.isEmpty()) {
            requestBody = formFieldsToUrl();
        } else if (Objects.nonNull(content) && content.length() > 0) {
            requestBody = content;
        }
        if (Objects.nonNull(requestBody) && requestBody.length() > 0) {
            OutputStream requestStream = conn.getOutputStream();
            requestStream.write(requestBody.getBytes(charset));
        }
    }

    private void finish(HttpURLConnection conn) throws IOException {
        if (conn.getDoOutput()) {
            conn.getOutputStream().flush();
        }
    }

    private String getResponseResult(HttpURLConnection conn) throws IOException {
        InputStream in;
        // Http OK
        if (conn.getResponseCode() / 100 == 2) {
            in = conn.getInputStream();
        } else {
            in = conn.getErrorStream();
        }
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024 * 4];
            int length;
            while ((length = in.read(buffer)) != -1) {
                bos.write(buffer, 0, length);
            }
            return new String(bos.toByteArray(), charset);
        }
    }

    private String formFieldsToUrl() throws UnsupportedEncodingException {
        StringBuilder urlParams = new StringBuilder();
        for (Map.Entry<String, Object> entry : formFields.entrySet()) {
            String encodedValue = "";
            if (Objects.nonNull(entry.getValue())) {
                encodedValue = URLEncoder.encode(Objects.toString(entry.getValue()), charset);
            }
            urlParams.append(entry.getKey()).append("=").append(encodedValue).append("&");
        }
        return urlParams.toString().length() > 0 ? urlParams.toString().replaceFirst("&$", "") : urlParams.toString();
    }

    public void addFormField(String key, Object value) {
        formFields.put(key, value);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    private void setRequestProperty(HttpURLConnection conn) {
        headers.forEach(conn::setRequestProperty);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setFormFields(Map<String, Object> formFields) {
        this.formFields = formFields;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public static class DummySSLSocketFactory extends CustomSSLSocketFactory {

        @Override
        TrustManager[] initTrustManager() {
            // 构造虚构的 TrustManager
            return new TrustManager[]{new X509TrustManager() {

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) {
                }
            }};
        }

        @Override
        @SuppressWarnings("StatementWithEmptyBody")
        protected Socket configSocket(Socket socket) {
            if (socket instanceof SSLSocket) {
                // workaround is from http://bugs.java.com/view_bug.do?bug_id=4639763
                // ((SSLSocket) socket).setEnabledProtocols(new String[]{"SSLv2Hello", "SSLv3"});

                /* == StackTraces ==
                    javax.net.ssl.SSLHandshakeException: No appropriate protocol (protocol is disabled or cipher suites are inappropriate)
                        at sun.security.ssl.Handshaker.activate(Handshaker.java:503)
                        at sun.security.ssl.SSLSocketImpl.kickstartHandshake(SSLSocketImpl.java:1470)
                        at sun.security.ssl.SSLSocketImpl.performInitialHandshake(SSLSocketImpl.java:1339)
                        at sun.security.ssl.SSLSocketImpl.startHandshake(SSLSocketImpl.java:1391)
                        at sun.security.ssl.SSLSocketImpl.startHandshake(SSLSocketImpl.java:1375)
                        at sun.net.www.protocol.https.HttpsClient.afterConnect(HttpsClient.java:563)
                        at sun.net.www.protocol.https.AbstractDelegateHttpsURLConnection.connect(AbstractDelegateHttpsURLConnection.java:185)
                        at sun.net.www.protocol.https.HttpsURLConnectionImpl.connect(HttpsURLConnectionImpl.java:153)
                 */
            }
            return socket;
        }

        public static SSLSocketFactory getDefault() {
            return new DummySSLSocketFactory();
        }
    }

    abstract static class CustomSSLSocketFactory extends SSLSocketFactory {

        private SSLSocketFactory impl;

        CustomSSLSocketFactory() {
            try {
                SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, initTrustManager(), null);
                this.impl = sslContext.getSocketFactory();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        abstract TrustManager[] initTrustManager() throws Exception;

        @Override
        public String[] getDefaultCipherSuites() {
            return impl.getDefaultCipherSuites();
        }

        @Override
        public String[] getSupportedCipherSuites() {
            return impl.getSupportedCipherSuites();
        }

        @Override
        public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException {
            return configSocket(impl.createSocket(socket, host, port, autoClose));
        }

        @Override
        public Socket createSocket() throws IOException {
            return configSocket(impl.createSocket());
        }

        @Override
        public Socket createSocket(String host, int port) throws IOException {
            return configSocket(impl.createSocket(host, port));
        }

        @Override
        public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException {
            return configSocket(impl.createSocket(host, port, localHost, localPort));
        }

        @Override
        public Socket createSocket(InetAddress host, int port) throws IOException {
            return configSocket(impl.createSocket(host, port));
        }

        @Override
        public Socket createSocket(InetAddress host, int port, InetAddress localHost, int localPort) throws IOException {
            return configSocket(impl.createSocket(host, port, localHost, localPort));
        }

        protected Socket configSocket(Socket socket) {
            return socket;
        }
    }
}
