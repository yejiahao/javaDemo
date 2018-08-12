package org.yejh;

public class SwapTest {
    public static void main(String[] args) {
        StringBuffer sb1 = new StringBuffer("A");
        StringBuffer sb2 = new StringBuffer("B");

        String s1 = "A";
        String s2 = "B";

        swap(sb1, sb2);
        System.out.println(sb1);
        System.out.println(sb2);

        System.out.println();

        swapbbb(s1, s2);
        System.out.println(s1);
        System.out.println(s2);
    }

    public static void swap(StringBuffer a, StringBuffer b) {
        a.append(b);
        b = a;
        System.out.println("swap: " + a);
        System.out.println("swap: " + b);
    }

    public static void swapbbb(String a, String b) {
        a = a + b;
        b = a;
        System.out.println("swapbbb: " + a);
        System.out.println("swapbbb: " + b);
    }
}
