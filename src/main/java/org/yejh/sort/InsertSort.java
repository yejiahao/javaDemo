package org.yejh.sort;

public class InsertSort {
    private static int[] insertSort(int[] n) {
        for (int i = 1; i < n.length; i++) {
            for (int j = i; j > 0; j--) {
                if (n[j] >= n[j - 1]) {
                    break;
                } else {
                    int temp = n[j];
                    n[j] = n[j - 1];
                    n[j - 1] = temp;
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
        int[] n = {4, 6, 3, 2, 1, 5, 8};
        n = InsertSort.insertSort(n);
        System.out.print("after insertSort: ");
        for (int k : n) {
            System.out.print(k + "\t");
        }
    }
}
