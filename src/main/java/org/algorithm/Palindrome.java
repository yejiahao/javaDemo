package org.algorithm;

/**
 * 判断一个数字是否为回文数：2145412是回文数，33765不是回文数
 */
public class Palindrome {
    public static void testSymmetry(int source) {
        int temp = source;
        int target = 0;

        while (temp > 0) {
            target = target * 10 + temp % 10;
            temp /= 10;
        }

        System.out.printf("source: %d%ntarget: %d%n回文数？%6s%n", source, target, source == target);
    }

    public static void main(String[] args) {
        int number = 2145412;
        Palindrome.testSymmetry(number);
    }
}
