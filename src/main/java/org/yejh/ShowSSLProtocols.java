package org.yejh;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.util.Arrays;

/**
 * SSL 协议列表
 *
 * @author <a href="mailto:yejh.1248@qq.com">Ye Jiahao</a>
 * @create 2020-04-14
 * @since x.y.z
 */
public class ShowSSLProtocols {

    public static void main(String[] args) throws Exception {
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, null, null);

        SSLSocketFactory factory = context.getSocketFactory();
        SSLSocket socket = (SSLSocket) factory.createSocket();

        String[] protocols = socket.getSupportedProtocols();
        System.out.println("Supported Protocols: " + Arrays.toString(protocols));

        protocols = socket.getEnabledProtocols();
        System.out.println("Enabled Protocols: " + Arrays.toString(protocols));
    }
}
