package org.yejh.thread;

public class RunnableABCPrinter implements Runnable {
    private String name;
    private Object previous;
    private Object self;

    public RunnableABCPrinter(String name, Object previous, Object self) {
        this.name = name;
        this.previous = previous;
        this.self = self;
    }

    @Override
    public void run() {
        int count = 10;
        while (count > 0) {
            synchronized (previous) {
                synchronized (self) {
                    System.out.print(name);
                    count--;
                    self.notify();
                }
                try {
                    previous.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();
        Runnable ra = new RunnableABCPrinter("A", c, a);
        Runnable rb = new RunnableABCPrinter("B", a, b);
        Runnable rc = new RunnableABCPrinter("C", b, c);
        new Thread(ra).start();
        Thread.sleep(100);
        new Thread(rb).start();
        Thread.sleep(100);
        new Thread(rc).start();
        Thread.sleep(100);
    }
}
