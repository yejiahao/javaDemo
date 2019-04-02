package org.algorithm;

/**
 * 整数 i 的初始值为 1，每次操作可以选择对 i 加 1 或者将 i 加倍。
 * 若想获得整数 2019，最少需要多少次操作？
 */
public class YearCount {
    public static void main(String[] args) {
        int number = 2019;
        System.out.printf("获得整数 %d ，最少需要 %d 次操作%n", number, calculate(number));
    }

    private static int calculate(int number) {
        int count = 0;
        while (number != 1) {
            if ((number & 1) == 1) {
                number -= 1;
            } else {
                number >>= 1;
            }
            count++;
        }
        return count;
    }
}
