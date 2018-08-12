package org.yejh.sort;

public class BubbleSort {
    private static int[] bubbleSort(int[] n) {
        for (int i = n.length; i > 1; i--) {
            for (int j = 0; j < i - 1; j++) {
                if (n[j] > n[j + 1]) {
                    int temp = n[j];
                    n[j] = n[j + 1];
                    n[j + 1] = temp;
                }
            }
            for (int k : n) {
                System.out.print(k + "\t");
            }
            System.out.println();
        }
        return n;
    }

    public static void main(String[] args) {
        int[] n = {4, 5, 6, 3, 2, 1, 6, 5, 8};
        n = BubbleSort.bubbleSort(n);
        System.out.print("after bubbleSort: ");
        for (int k : n) {
            System.out.print(k + "\t");
        }
    }

}
