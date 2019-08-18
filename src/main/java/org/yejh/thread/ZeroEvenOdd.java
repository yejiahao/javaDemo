package org.yejh.thread;

/**
 * https://leetcode-cn.com/problems/print-zero-even-odd/
 * <p>
 * 输出整数序列 010203040506... ，其中序列的长度必须为 2n
 * <p>
 * e.g.
 * input: n = 2
 * output: "0102"
 * description: 三条线程异步执行，其中一个调用 {@link #zero}，另一个线程调用 {@link #even}，最后一个线程调用 {@link #odd}。
 *
 * @author <a href="mailto:yejh.1248@qq.com">Ye Jiahao</a>
 * @create 2019-08-18
 * @since x.y.z
 */
public class ZeroEvenOdd {

    private Object oa = new Object();
    private Object ob = new Object();
    private Object oc = new Object();

    private int n;

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            synchronized (oa) {
                synchronized (ob) {
                    synchronized (oc) {
                        printNumber.accept(0);
                        if (i % 2 == 0) {
                            ob.notifyAll();
                        } else {
                            oc.notifyAll();
                        }
                    }
                }
                oa.wait();
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        Thread.sleep(200L);
        for (int i = 2; i <= n; i += 2) {
            synchronized (ob) {
                synchronized (oa) {
                    printNumber.accept(i);
                    oa.notifyAll();
                }
                // 最后一次打印之后，zero 线程无法通知，需要自身超时退出
                ob.wait(300L);
            }
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        Thread.sleep(100L);
        for (int i = 1; i <= n; i += 2) {
            synchronized (oc) {
                synchronized (oa) {
                    printNumber.accept(i);
                    oa.notifyAll();
                }
                // 最后一次打印之后，zero 线程无法通知，需要自身超时退出
                oc.wait(300L);
            }
        }
    }

    public static void main(String[] args) {
        IntConsumer ic = new IntConsumer();
        ZeroEvenOdd zeo = new ZeroEvenOdd(9);

        new Thread(() -> {
            try {
                zeo.zero(ic);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "zero").start();
        new Thread(() -> {
            try {
                zeo.even(ic);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "even").start();
        new Thread(() -> {
            try {
                zeo.odd(ic);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "odd").start();
    }

    static class IntConsumer {
        void accept(int n) {
            System.out.print(n);
        }
    }
}
