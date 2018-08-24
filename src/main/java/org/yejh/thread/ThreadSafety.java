package org.yejh.thread;

public class ThreadSafety {
    private int count = 0;

    public int add() {
        for (int i = 1; i <= 10; i++) {
            count += i;
        }
        System.out.println(Thread.currentThread().getName() + ", count: " + count);
        return count;
    }

    public static void main(String[] args) {
        // 单例前提下，成员变量变得线程不安全
        ThreadSafety ts = new ThreadSafety();
        for (int i = 1; i <= 5; i++) {
            new MyThread(ts).start();
        }
    }

    static class MyThread extends Thread {
        private ThreadSafety ts;

        public MyThread(ThreadSafety ts) {
            this.ts = ts;
        }

        @Override
        public void run() {
            ts.add();
        }
    }
}
