package org.yejh.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class SDFThreadTest {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new MyThread().start();
        }
    }

    /**
     * SimpleDateFormat线程不安全解决方法：
     * 1. SimpleDateFormat设置为局部变量
     * 2. synchronized块
     * 3. 第三方时间类库（joda-time）
     * 4. JDK8时间类库
     * 5. ThreadLocal<T>线程局部变量
     */
    static class MyThread extends Thread {
        @Override
        public void run() {
            try {
                DateFormat df = OldDate.DF;
                DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                DateTimeFormatter df4 = OldDate.JDK8_FORMATTER;
                DateFormat df5 = OldDate.LOCAL.get();

                System.out.println(Thread.currentThread().getName() + "\t" + df.parse("2018-08-12 19:00:05"));
//                System.out.println(Thread.currentThread().getName() + "\t" + df1.parse("2018-08-12 19:00:05"));
//                System.out.println(Thread.currentThread().getName() + "\t" + df5.parse("2018-08-12 19:00:05"));
//                System.out.println(Thread.currentThread().getName() + "\t" + LocalDateTime.parse("2018-08-12 19:00:05", df4));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
