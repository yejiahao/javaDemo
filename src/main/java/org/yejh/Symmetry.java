package org.yejh;

public class Symmetry {
    public static Boolean testSymmetry(int source) {
        int temp = source;
        int target = 0;

        while (temp > 0) {
            target = target * 10 + temp % 10;
            temp = temp / 10;
        }

        System.out.println("source: " + source);
        System.out.println("target: " + target);
        return source == target;
    }

    public static void main(String[] args) {
        int number = 2145412;
        System.out.println(Symmetry.testSymmetry(number));
    }
}
