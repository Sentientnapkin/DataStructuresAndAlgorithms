package DivideAndConquer;

import java.util.Arrays;

public class MergeAndMergeSort {
    // Helper method to create subArrays from arrays
    // The indexes work just like substring: begin is included
    // but end is excluded
    public static int[] subArray(int[] arr, int begin, int end) {
        int[] output = new int[end - begin];
        for (int i = 0; i < output.length; i++) {
            output[i] = arr[begin + i];
        }
        return output;
    }

    // Helper method to print array to aid debugging
    public static void printArray(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }

    // Give two sorted arrays A and B return one
    // large combined sorted array
    public static int[] merge(int[] A, int[] B) {
        int[] output = new int[A.length + B.length];

        int aIndex = 0;
        int bIndex = 0;
        for (int i = 0; i < output.length; i++) {

            if (aIndex == A.length) {
                for (int j = i; j < output.length; j++) {
                    output[j] = B[bIndex++];
                }
				return output;
            }
            if (bIndex == B.length) {
                for (int j = i; j < output.length; j++) {
                    output[j] = A[aIndex++];
                }
				return output;
            }

            if (A[aIndex] < B[bIndex]) {
                output[i] = A[aIndex++];
            } else {
                output[i] = B[bIndex++];
            }
        }

        return output;
    }

    // Recursively sorts the array using Merge Sort
    public static int[] mergeSort(int[] arr) {
        if (arr.length == 1) {
            return arr;
        }

        int[] leftHalf = subArray(arr, 0, arr.length / 2);
        int[] rightHalf = subArray(arr, arr.length / 2, arr.length);

		leftHalf = mergeSort(leftHalf);
		rightHalf = mergeSort(rightHalf);

		return merge(leftHalf, rightHalf);
    }

    public static void main(String[] args) {
        // --------------------------
        // Test 1: Merge Method
        // --------------------------
        System.out.println("-------------------");
        System.out.println("Test 1: Merge Method");
        System.out.println("Expected:");
        System.out.println("[1, 2, 3, 4, 5, 7, 8, 10]\n" +
            "[1, 2, 3, 4, 5, 7, 8, 10]");

        System.out.println("\nGot:");

        int[] l = {1, 3, 4, 7};
        int[] r = {2, 5, 8, 10};
        printArray(merge(l, r));
        printArray(merge(r, l));

        // --------------------------
        // Test 2: Merge Sort
        // --------------------------
        System.out.println("-------------------");
        System.out.println("Test 2: Merge Sort");
        System.out.println("Expected:");
        System.out.println("[1, 2, 4, 12, 15, 23, 24, 28, 32, 43, 51, 56, 63, 67, 87, 94]");

        System.out.println("\nGot:");

        int[] arr = {4, 1, 23, 51, 2, 67, 12, 32, 87, 43, 56, 63, 28, 94, 15, 24};
        printArray(mergeSort(arr));
        // [1, 2, 4, 12, 15, 23, 24, 28, 32, 43, 51, 56, 63, 67, 87, 94]
    }
}
