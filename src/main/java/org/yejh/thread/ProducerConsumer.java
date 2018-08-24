package org.yejh.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProducerConsumer {
    public static Buffer buffer = new Buffer();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(new ProducerRunnable());
        executorService.execute(new ConsumerRunnable());
        executorService.shutdown();
    }

    static class ProducerRunnable implements Runnable {
        int i = 0;

        @Override
        public void run() {
            while (true) {
                buffer.addElement(++i);
                try {
                    Thread.sleep((int) Math.random() * 10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }

    static class ConsumerRunnable implements Runnable {
        @Override
        public void run() {
            while (true) {
                buffer.removeElement();
                try {
                    Thread.sleep((int) Math.random() * 10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }

    static class Buffer {
        private List<Integer> list = new ArrayList<>();
        private static final int CAPACITY = 2;

        public void addElement(int i) {
            synchronized (this) {
                if (list.size() == CAPACITY) {
                    System.out.println("线性表满了，不能生产！");
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                list.add(i);
                System.out.println("produce element: " + i);
                this.notifyAll();
            }
        }

        public void removeElement() {
            synchronized (this) {
                if (list.isEmpty()) {
                    System.out.println("线性表空了，不能消费！");
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int i = list.remove(0);
                System.out.println("consume element: " + i);
                this.notifyAll();
            }
        }
    }
}
