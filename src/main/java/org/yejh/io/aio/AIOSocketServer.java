package org.yejh.io.aio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;

/**
 * @author <a href="mailto:yejh.1248@qq.com">Ye Jiahao</a>
 * @create 2020-01-24
 * @since x.y.z
 */
public class AIOSocketServer {

    public static void main(String[] args) {
        int port = 9003;

        // AIO 线程复用版
        ExecutorService es = Executors.newSingleThreadExecutor(r -> new Thread(r, "AIOSocketServer"));
        es.execute(() -> {
            try {
                AsynchronousChannelGroup group = AsynchronousChannelGroup.withThreadPool(Executors.newFixedThreadPool(4));
                AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel
                        .open(group)
                        .bind(new InetSocketAddress(InetAddress.getLocalHost(), port));
                server.accept(null, new CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel>() {
                    @Override
                    public void completed(AsynchronousSocketChannel result, AsynchronousServerSocketChannel attachment) {
                        server.accept(null, this); // 接收下一个请求
                        try {
                            Future<Integer> f = result.write(Charset.defaultCharset().encode("你好，世界"));
                            f.get();
                            System.out.println("服务器发送时间: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                            result.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failed(Throwable exc, AsynchronousServerSocketChannel attachment) {
                    }
                });
                group.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Socket 客户端
        try (AsynchronousSocketChannel client = AsynchronousSocketChannel.open()) {
            Future<Void> future = client.connect(new InetSocketAddress(InetAddress.getLocalHost(), port));
            future.get();
            ByteBuffer buffer = ByteBuffer.allocate(100);
            client.read(buffer, null, new CompletionHandler<Integer, Void>() {
                @Override
                public void completed(Integer result, Void attachment) {
                    System.out.println("AIO 客户端：" + new String(buffer.array()));
                }

                @Override
                public void failed(Throwable exc, Void attachment) {
                    exc.printStackTrace();
                    try {
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            TimeUnit.SECONDS.sleep(10L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
