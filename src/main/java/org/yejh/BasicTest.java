package org.yejh;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class BasicTest {
    private static void operateMethod(String str) {
        new Date();
    }

    private static void method1(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            BasicTest.operateMethod(list.get(i));
        }
    }

    private static void method2(List<String> list) {
        for (int i = 0, size = list.size(); i < size; i++) {
            BasicTest.operateMethod(list.get(i));
        }
    }

    private static void method3(List<String> list) {
        for (String s : list) {
            BasicTest.operateMethod(s);
        }
    }

    public static void main(String[] args) {
        List<String> list = Collections.nCopies(300000000, "str");

        long s_time = System.currentTimeMillis();
        BasicTest.method1(list);
        long e_time = System.currentTimeMillis();
        System.out.println("for before: " + (e_time - s_time));
        // -----------------------------------------------------------------
        s_time = System.currentTimeMillis();
        BasicTest.method2(list);
        e_time = System.currentTimeMillis();
        System.out.println("for after: " + (e_time - s_time));
        // -----------------------------------------------------------------
        s_time = System.currentTimeMillis();
        BasicTest.method3(list);
        e_time = System.currentTimeMillis();
        System.out.println("for each: " + (e_time - s_time));
    }
}
