package org.yejh;

public class StringTest {
    public static void main(String[] args) {
        String s1 = "Programming";
        String s2 = new String("Programming");
        String s3 = "Program";
        String s4 = "ming";
        String s5 = "Program" + "ming";
        String s6 = s3 + s4;
        System.out.println(s1 == s2);
        System.out.println(s1 == s5);
        System.out.println(s1 == s6);
        System.out.println(s1 == s6.intern());
        System.out.println(s2 == s2.intern());

        /*
         * false true false true false
         */

        String str = "1bcdAasdfdfg";
        for (char c : str.toCharArray()) {
            System.out.print(c + "\t");
        }
        System.out.println();

        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);
        System.out.println("------------------------------");

        System.out.println(2);
        System.out.println(2 < 3);
        System.out.println(2 << 3);
        System.out.println("------------------------------");

        String normalStr = "hello, I am Mr.Ben.";
        System.out.println(StringTest.reverseString(normalStr));
        System.out.println(normalStr);
        System.out.println("------------------------------");

        int reverseInt1 = 456123;
        int reverseInt2 = 85258;
        int reverseInt3 = 12566521;
        System.out.println(palindrome(reverseInt1));
        System.out.println(palindrome(reverseInt2));
        System.out.println(palindrome(reverseInt3));
    }

    private static String reverseString(String normalStr) {
        if (normalStr == null || normalStr.length() <= 1) {
            return normalStr;
        }
        return reverseString(normalStr.substring(1)) + normalStr.charAt(0);
    }

    private static Boolean palindrome(int source) {
        int temp = source;
        int target = 0;
        while (temp > 0) {
            target = target * 10 + temp % 10;
            temp /= 10;
        }
        return source == target;
    }
}
