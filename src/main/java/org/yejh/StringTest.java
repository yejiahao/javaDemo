package org.yejh;

public class StringTest {
    public static void main(String[] args) {
        String s1 = "Programming";
        String s2 = new String("Programming");
        String s3 = "Program";
        String s4 = "ming";
        String s5 = "Program" + "ming";
        String s6 = s3 + s4;
        System.out.println("s1 == s2: " + (s1 == s2));// false
        System.out.println("s1 == s5: " + (s1 == s5));// true
        System.out.println("s1 == s6: " + (s1 == s6));// false
        System.out.println("s1 == s6.intern(): " + (s1 == s6.intern()));// true
        System.out.println("s2 == s2.intern(): " + (s2 == s2.intern()));// false

        System.out.print("toCharArray: ");
        String str = "1bcdAasdfdfg";
        for (char c : str.toCharArray()) {
            System.out.print("\t" + c);
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
    }

    private static String reverseString(String normalStr) {
        if (normalStr == null || normalStr.length() <= 1) {
            return normalStr;
        }
        return reverseString(normalStr.substring(1)) + normalStr.charAt(0);
    }
}
