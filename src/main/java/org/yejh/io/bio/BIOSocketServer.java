package org.yejh.io.bio;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author <a href="mailto:yejh.1248@qq.com">Ye Jiahao</a>
 * @create 2020-01-24
 * @since x.y.z
 */
public class BIOSocketServer {

    public static void main(String[] args) {
        int port = 9001;

        // Socket 服务器端（简单的发送信息）
        ExecutorService es = Executors.newSingleThreadExecutor(r -> new Thread(r, "BIOSocketServer"));
        es.execute(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                while (true) {
                    // 等待连接
                    Socket socket = serverSocket.accept();
                    new Thread(() -> {
                        try (PrintWriter pw = new PrintWriter(socket.getOutputStream())) {
                            pw.println("你好，世界");
                            pw.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Socket 客户端（接收信息并打印）
        try (Socket cSocket = new Socket(InetAddress.getLocalHost(), port)) {
            BufferedReader br = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
            br.lines().forEach(s -> System.out.println("BIO 客户端：" + s));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
