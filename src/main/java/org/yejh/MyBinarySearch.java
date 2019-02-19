package org.yejh;

public class MyBinarySearch {
    public static void main(String[] args) {
        int[] a = {2, 5, 12, 16, 18, 45, 67, 78, 79, 83, 89, 93, 97, 99};
        int target = 78;
        System.out.println("target index: " + searchIndex(a, target));
    }

    private static int searchIndex(int[] array, int target) {
        int min = 0;
        int max = array.length - 1;

        while (min <= max) {
            int middle = (max - min) / 2 + min;
            if (target < array[middle]) {
                max = middle - 1;
            }
            if (target > array[middle]) {
                min = middle + 1;
            }
            if (target == array[middle]) {
                return middle;
            }
        }
        return -1;
    }
}
