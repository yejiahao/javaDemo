package org.yejh;

class A {
    static {
        System.out.println("static A");
    }

    public A() {
        System.out.println("Constructor A");
    }
}

class B extends A {
    static {
        System.out.println("static B");
    }

    public B() {
        System.out.println("Constructor B");
    }
}

public class ExtendsExample {
    public static void main(String[] args) {
        A a = new B();
        a = new B();
    }
}
