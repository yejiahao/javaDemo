package org.yejh.thread;

import java.util.concurrent.*;

public class CallableTest implements Callable<Integer> {
    private int count;

    public CallableTest(int count) {
        this.count = count;
    }

    @Override
    public Integer call() throws Exception {
        Thread.sleep(10 * 1000);
        return count / 3;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<Integer> future = es.submit(new CallableTest(100));
        while (!future.isDone()) {
            Thread.sleep(1000);
            System.out.print("*");
        }
        System.out.println("\nresult: " + future.get());
        es.shutdown();
    }
}
