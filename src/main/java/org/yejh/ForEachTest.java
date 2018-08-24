package org.yejh;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ForEachTest {
    private static void operateMethod() {
        new Date();
    }

    private static void testFor(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            ForEachTest.operateMethod();
        }
    }

    private static void testFor2(List<String> list) {
        for (int i = 0, size = list.size(); i < size; i++) {
            ForEachTest.operateMethod();
        }
    }

    private static void testForEach(List<String> list) {
        for (String s : list) {
            ForEachTest.operateMethod();
        }
    }

    public static void main(String[] args) {
        List<String> list = Collections.nCopies(300_000_000, "str");

        long sTime = System.currentTimeMillis();
        ForEachTest.testFor(list);
        long eTime = System.currentTimeMillis();
        System.out.printf("[testFor] time consumer: %d ms%n", eTime - sTime);
        System.out.println("-----------------------------------------------------------");

        sTime = System.currentTimeMillis();
        ForEachTest.testFor2(list);
        eTime = System.currentTimeMillis();
        System.out.printf("[testFor2] time consumer: %d ms%n", eTime - sTime);
        System.out.println("-----------------------------------------------------------");

        sTime = System.currentTimeMillis();
        ForEachTest.testForEach(list);
        eTime = System.currentTimeMillis();
        System.out.printf("[testForEach] time consumer: %d ms%n", eTime - sTime);
    }
}
