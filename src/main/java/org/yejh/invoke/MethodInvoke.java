package org.yejh.invoke;

import java.lang.reflect.Method;

public class MethodInvoke {
    public static void main(String[] args) {
        String str = "hello world.";
        try {
            Method method1 = str.getClass().getMethod("length");
            Method method2 = str.getClass().getMethod("isEmpty");
            Method method3 = str.getClass().getMethod("toUpperCase");
            System.out.println(method1.invoke(str));
            System.out.println(method2.invoke(str));
            System.out.println(method3.invoke(str));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
