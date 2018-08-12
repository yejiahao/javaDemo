package org.yejh.trycatch;

import java.util.zip.DataFormatException;

public class FinallyReturn {
    private static int doStuff(int p) throws Exception {
        try {
            if (p < 0) {
                throw new DataFormatException("数字不能小于0");
            } else {
                return p;
            }
        } catch (DataFormatException e) {
            throw e;
        } finally {
            return 123;
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println("11111111111111111");
            int a = FinallyReturn.doStuff(-1);
            System.out.println("a: " + a);
            int b = FinallyReturn.doStuff(100);
            System.out.println("b: " + b);
        } catch (Exception e) {
            System.out.println("44444444444444444");
        }
    }
}
