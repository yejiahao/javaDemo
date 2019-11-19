package org.yejh;

public class SwapTest {
    public static void main(String[] args) {
        StringBuilder sb1 = new StringBuilder("A");
        StringBuilder sb2 = new StringBuilder("B");

        String str1 = "C";
        String str2 = "D";

        System.out.printf("[before]swapSb: %s, %s%n", sb1, sb2);
        swapSb(sb1, sb2);
        System.out.printf("[after ]swapSb: %s, %s%n", sb1, sb2);

        System.out.println();

        System.out.printf("[before]swapStr: %s, %s%n", str1, str2);
        swapStr(str1, str2);
        System.out.printf("[after ]swapStr: %s, %s%n", str1, str2);
    }

    private static void swapSb(StringBuilder sb1, StringBuilder sb2) {
        sb1.append(sb2);
        sb2 = sb1;
        System.out.printf("[ swap ]swapSb: %s, %s%n", sb1, sb2);
    }

    private static void swapStr(String str1, String str2) {
        str1 = str1 + str2;
        str2 = str1;
        System.out.printf("[ swap ]swapStr: %s, %s%n", str1, str2);
    }
}
