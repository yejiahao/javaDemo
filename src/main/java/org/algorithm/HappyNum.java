package org.algorithm;

import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * 判断一个数字是否为快乐数字：19是快乐数字，11不是快乐数字
 * <p>
 * 19
 * 1 * 1 + 9 * 9 = 82
 * 8 * 8 + 2 * 2 = 68
 * 6 * 6 + 8 * 8 = 100
 * 1 * 1 + 0 * 0 + 0 * 0 = 1
 * <p>
 * 11
 * 1 * 1 + 1 * 1 = 2
 * 2 * 2 = 4
 * 4 * 4 = 16
 * 1 * 1 + 6 * 6 = 37
 * 3 * 3 + 7 * 7 = 58
 * 5 * 5 + 8 * 8 = 89
 * 8 * 8 + 9 * 9 = 145
 * 1 * 1 + 4 * 4 + 5 * 5= 42
 * 4 * 4 + 2 * 2 = 20
 * 2 * 2 + 0 * 0 = 2
 * 这里 1 * 1 + 1 * 1 = 2 和 2 * 2 + 0 * 0 = 2 重复，所以不是快乐数字
 */
public class HappyNum {
    private static boolean isHappy(int num) {
        Set<Integer> set = new LinkedHashSet<>();
        try {
            while (num != 1) {
                int sum = 0;
                while (num != 0) {
                    sum += (num % 10) * (num % 10);
                    num /= 10;
                }

                if (set.contains(sum)) {
                    return false;
                } else {
                    set.add(sum);
                    num = sum;
                }
            }
            return true;
        } finally {
            System.out.println("set: " + set);
        }
    }

    public static void main(String[] args) {
        System.out.print("请输入一个数字： ");
        int num = new Scanner(System.in).nextInt();
        System.out.printf("%-4d是否快乐数？%6s%n", num, isHappy(num));
    }
}
