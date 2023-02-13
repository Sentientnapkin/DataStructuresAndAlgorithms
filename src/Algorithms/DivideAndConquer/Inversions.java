package Algorithms.DivideAndConquer;
import java.util.Arrays;

public class Inversions {
    // Helper method to create subArrays from arrays
    // The indexes work just like substring: begin is included
    // but end is excluded
    public static int[] subArray(int[] arr, int begin, int end) {
        int[] output = new int[end-begin];
        for (int i = 0; i < output.length; i++) {
            output[i] = arr[begin+i];
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

    // Merges two arrays AND counts the number of inversions between them
    public static InversionResult mergeAndCount(int[] A, int[] B) {
		InversionResult output = new InversionResult(new int[A.length + B.length], 0);

		int aIndex = 0;
		int bIndex = 0;
		for (int i = 0; i < output.arr.length; i++) {

			if (aIndex == A.length) {
				for (int j = i; j < output.arr.length; j++) {
					output.arr[j] = B[bIndex++];
				}
				return output;
			}
			if (bIndex == B.length) {
				for (int j = i; j < output.arr.length; j++) {
					output.arr[j] = A[aIndex++];
				}
				return output;
			}

			if (A[aIndex] < B[bIndex]) {
				output.arr[i] = A[aIndex++];
			} else {
				output.arr[i] = B[bIndex++];
				output.count+=(A.length-aIndex);
			}
		}

		return output;
    }

    // Sorts two arrays AND sums the number of inversions between them
    public static InversionResult sortAndCount(int[] arr) {
		if (arr.length == 1) {
			return new InversionResult(arr, 0);
		}

		InversionResult leftHalf = new InversionResult(subArray(arr, 0, arr.length / 2), 0);
		InversionResult rightHalf = new InversionResult(subArray(arr, arr.length / 2, arr.length), 0);

		leftHalf = sortAndCount(leftHalf.arr);
		rightHalf = sortAndCount(rightHalf.arr);

		InversionResult result = mergeAndCount(leftHalf.arr, rightHalf.arr);
		return new InversionResult(result.arr, result.count + leftHalf.count + rightHalf.count);
    }

    public static void main(String[] args) {
        // --------------------------
        // Test 1: Merge and Count Method
        // --------------------------
        System.out.println("-------------------");
        System.out.println("Test 1: Merge and Count Method");
        System.out.println("Expected:");
        System.out.println("[1, 2, 3, 4, 5, 6, 7, 8]\n" +
                "8");

        System.out.println("\nGot:");


        int[] arr1 = {1, 3, 6, 8};
        int[] arr2 = {2, 4, 5, 7};
        InversionResult m1 = mergeAndCount(arr1, arr2);

        printArray(m1.arr);
        System.out.println(m1.count);

        // --------------------------
        // Test 2: Sort and Count
        // --------------------------
        System.out.println("-------------------");
        System.out.println("Test 2: Sort and Count");
        System.out.println("Expected:");
        System.out.println("[1, 2, 3, 4]\n" +
                "5\n" +
                "[1, 2, 3, 4, 5, 6, 7, 8]\n" +
                "7\n" +
                "[1, 2, 3, 4, 5, 6, 7, 8]\n" +
                "8");

        System.out.println("\nGot:");

        InversionResult A = sortAndCount(new int[]{4, 2, 3, 1});
        printArray(A.arr);
        // [1, 2, 3, 4]
        System.out.println(A.count);
        // 5

        InversionResult B = sortAndCount(new int[]{2, 3, 4, 5, 6, 7, 8, 1});
        printArray(B.arr);
        // [1, 2, 3, 4, 5, 6, 7, 8]
        System.out.println(B.count);
        // 7

        InversionResult C = sortAndCount(new int[]{2, 3, 7, 4, 1, 5, 8, 6});
        printArray(C.arr);
        // [1, 2, 3, 4, 5, 6, 7, 8]
        System.out.println(C.count);
        // 8
    }

    static class InversionResult {
        int[] arr;
        int count;

        public InversionResult(int[] arr, int count) {
            this.arr = arr;
            this.count = count;
        }
    }
}
