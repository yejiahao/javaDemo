package org.yejh.sort;

public class QuickSort {
    private static int[] quickSort(int[] n, int low, int high) {
        if (low < high) {
            int middle = getMiddle(n, low, high);
            quickSort(n, low, middle - 1);
            quickSort(n, middle + 1, high);
        }
        return n;
    }

    private static int getMiddle(int[] n, int low, int high) {
        int temp = n[low];

        while (low < high) {
            while (low < high && n[high] >= temp) {
                high--;
            }
            n[low] = n[high];
            while (low < high && n[low] <= temp) {
                low++;
            }
            n[high] = n[low];
        }

        n[low] = temp;

        for (int k : n) {
            System.out.print(k + "\t");
        }
        System.out.println();

        return low;
    }

    public static void main(String[] args) {
        int[] n = {4, 5, 6, 3, 2, 1, 6, 5, 8};
        n = QuickSort.quickSort(n, 0, n.length - 1);
        System.out.print("after quickSort: ");
        for (int k : n) {
            System.out.print(k + "\t");
        }
    }

}