package org.yejh.sort;

public class ChooseSort {
    private static int[] chooseSort(int[] n) {
        int min = 0;
        for (int i = 0; i < n.length - 1; i++) {
            min = i;
            for (int j = i + 1; j < n.length; j++) {
                if (n[j] < n[min]) {
                    min = j;
                }
            }
            if (min != i) {
                int temp = n[min];
                n[min] = n[i];
                n[i] = temp;
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
        n = ChooseSort.chooseSort(n);
        System.out.print("after chooseSort: ");
        for (int k : n) {
            System.out.print(k + "\t");
        }
    }
}
