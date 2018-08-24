package org.yejh.trycatch;

public class ThrowableInvoke {
    public static void getStackTrace() {
        StackTraceElement[] steArray = new Throwable().getStackTrace();

        for (StackTraceElement element : steArray) {
            // 栈反序排列： getStackTrace method1 main
            System.out.printf("declaringClass: %s, methodName: %s, fileName: %s, lineNumber: %d%n",
                    element.getClassName(), element.getMethodName(), element.getFileName(), element.getLineNumber());
        }
        System.out.println();
    }

    public static void method1() {
        getStackTrace();
    }

    public static void method2(int count) {
        if (count-- == 1) {
            getStackTrace();
        } else {
            method2(count);
        }
    }

    public static void main(String[] args) {
        method1();
        Sub.subMethod1();
        method2(5);
    }
}

class Sub {
    public static void subMethod1() {
        ThrowableInvoke.method1();
    }
}