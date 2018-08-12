package org.yejh.trycatch;

public class ThrowableInvoke {
    public static Boolean m() {
        StackTraceElement[] steArray = new Throwable().getStackTrace();
        System.out.println("steArray length: " + steArray.length);
        for (int i = 0, length = steArray.length; i < length; i++) {
            if ("method1".equals(steArray[i].getMethodName())) {
                System.out.println("i: " + i);// 栈反序排列： m method2 method1 main
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public static Boolean method1() {
        return m();
    }

    public static Boolean method2() {
        return m();
    }

    public static void main(String[] args) {
        System.out.println("method1: " + method1());
        System.out.println("-----------------------------------------------------");
        System.out.println("method1: " + Sub.method1());
        System.out.println("-----------------------------------------------------");
        System.out.println("method2: " + method2());
    }
}

class Sub {
    public static Boolean method1() {
        return ThrowableInvoke.method2();
    }
}