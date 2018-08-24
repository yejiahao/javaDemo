package org.yejh.thread;

public class ThreadRunnableTest {
    public static void main(String[] args) {
        Thread t1 = new ThreadTest("T_A");
        Thread t2 = new ThreadTest("T_B");

        Runnable r1 = new RunnableTest("R_A");
        Runnable r2 = new RunnableTest("R_B");
        Thread t3 = new Thread(r1);
        Thread t4 = new Thread(r2);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}

class ThreadTest extends Thread {
    private String name;

    public ThreadTest(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + i + ": " + name);
        }
    }
}

class RunnableTest implements Runnable {
    private String name;

    public RunnableTest(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + i + ": " + name);
        }
    }
}

