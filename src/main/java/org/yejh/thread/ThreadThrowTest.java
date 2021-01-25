package org.yejh.thread;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 线程出现异常后的捕获场景测试
 *
 * @author <a href="mailto:yejh.1248@qq.com">Ye Jiahao</a>
 * @create 2021-01-25
 * @since 1.1.0
 */
public class ThreadThrowTest {

    public static void main(String[] args) {
        try {
            Thread t1 = new Thread(/*Runnable*/() -> {
                System.out.println("thread runnable start");
                int i = 1 / 0;
                System.out.println("thread runnable end");
            }, "t1");
            t1.setUncaughtExceptionHandler((t, e) -> System.out.printf("thread: %s, exception: %s%n", t.getName(), Arrays.toString(e.getStackTrace())));
            t1.start();

            ExecutorService es = Executors.newSingleThreadExecutor(r -> new Thread(r, "single-thread"));

            es.execute(/*Runnable*/() -> {
                System.out.println("execute runnable start");
                int i = 1 / 0;
                System.out.println("execute runnable end");
            });

            Future<?> submit2 = es.submit(/*Runnable*/() -> {
                System.out.println("submit runnable start");
                int i = 1 / 0;
                System.out.println("submit runnable end");
            });
            System.out.println("submit2.get() = " + submit2.get());

            Future<Integer> submit3 = es.submit(/*Callable*/() -> {
                System.out.println("submit callable start");
                int i = 1 / 0;
                System.out.println("submit callable end");
                return 1;
            });
            System.out.println("submit3.get() = " + submit3.get());

            es.shutdown();
        } catch (Exception e) {
            System.out.println("exception: " + Arrays.toString(e.getStackTrace()));
        }
    }
}
