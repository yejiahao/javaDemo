package org.yejh.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ABCDPrinter implements Runnable {
    private char name;
    private Object self;
    private Object next;

    public ABCDPrinter(char name, Object self, Object next) {
        this.name = name;
        this.self = self;
        this.next = next;
    }

    @Override
    public void run() {
        for (int i = 10; i >= 1; i--) {
            synchronized (self) {
                synchronized (next) {
                    System.out.print(name);
                    next.notify();
                }
                if (i > 1) {
                    try {
                        self.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Object oa = new Object();
        Object ob = new Object();
        Object oc = new Object();
        Object od = new Object();

        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(new ABCDPrinter('A', oa, ob));
        Thread.sleep(100);
        es.execute(new ABCDPrinter('B', ob, oc));
        Thread.sleep(100);
        es.execute(new ABCDPrinter('C', oc, od));
        Thread.sleep(100);
        es.execute(new ABCDPrinter('D', od, oa));
        es.shutdown();
    }
}
