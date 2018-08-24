package org.yejh.sort;

import java.util.Arrays;

public class MySort {

    /**
     * 插入排序
     */
    private static void insertSort(int[] n) {
        for (int i = 1, len = n.length; i < len; i++) {
            for (int j = i; j > 0; j--) {
                if (n[j] >= n[j - 1]) {
                    break;
                } else {
                    int temp = n[j];
                    n[j] = n[j - 1];
                    n[j - 1] = temp;
                }
            }
            System.out.printf("insertSort - %s: %s%n", i, Arrays.toString(n));
        }
        System.out.println("----------------------------------------------");
    }

    /**
     * 选择排序
     */
    private static void selectSort(int[] n) {
        for (int i = 1, len = n.length; i < len; i++) {
            int index = i - 1;
            for (int j = i; j < len; j++) {
                if (n[j] < n[index]) {
                    index = j;
                }
            }
            if (index != i - 1) {
                int temp = n[index];
                n[index] = n[i - 1];
                n[i - 1] = temp;
            }
            System.out.printf("selectSort - %s: %s%n", i, Arrays.toString(n));
        }
        System.out.println("----------------------------------------------");
    }

    /**
     * 冒泡排序
     */
    private static void bubbleSort(int[] n) {
        for (int i = n.length; i > 1; i--) {
            boolean isSorted = true;
            for (int j = 1; j < i; j++) {
                if (n[j - 1] > n[j]) {
                    int temp = n[j - 1];
                    n[j - 1] = n[j];
                    n[j] = temp;
                    isSorted = false;
                }
            }
            if (isSorted) {
                break;
            }
            System.out.printf("bubbleSort - %s: %s%n", i, Arrays.toString(n));
        }
        System.out.println("----------------------------------------------");
    }

    /**
     * 快速排序
     */
    private static void quickSort(int[] n) {
        quickSort(n, 0, n.length - 1);
        System.out.println("----------------------------------------------");
    }

    private static void quickSort(int[] n, int low, int high) {
        if (low < high) {
            int middle = getMiddle(n, low, high);
            System.out.printf("quickSort - %s: %s%n", middle, Arrays.toString(n));
            quickSort(n, low, middle - 1);
            quickSort(n, middle + 1, high);
        }
    }

    private static int getMiddle(int[] n, int low, int high) {
        int temp = n[low];
        while (low < high) {
            while (low < high && n[high] >= temp) high--;
            n[low] = n[high];
            while (low < high && n[low] <= temp) low++;
            n[high] = n[low];
        }
        n[low] = temp;
        return low;
    }

    public static void main(String[] args) {
        int[] n1 = {4, 6, 7, 3, 2, 1, 3, 5, 8, 7};
        int[] n2 = Arrays.copyOf(n1, n1.length);
        int[] n3 = Arrays.copyOf(n1, n1.length);
        int[] n4 = Arrays.copyOf(n1, n1.length);
        insertSort(n1);
        selectSort(n2);
        bubbleSort(n3);
        quickSort(n4);
    }
}
