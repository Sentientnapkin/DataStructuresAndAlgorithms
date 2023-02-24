package Algorithms.Dynamic;

public class SubsetSum {
    // Given an array of integers and an int sum, determine if some subset
    // of those integers adds up to exactly sum

    // For example, if our input array is [1, 1, 4], the possible sums which would
    // return true are 0, 1, 2, 4, 5, 6. Any other sum value would return false.
    public static boolean subsetSum(int[] arr, int sum) {
		boolean[][] solutions = new boolean[arr.length+1][sum+1];
		for(int i = 0; i<solutions.length; i++){
			solutions[i][0] = true;
		}

		for(int row = 1; row < solutions.length; row++){
			for(int col = 1; col < solutions[0].length; col++){
				if(solutions[row-1][col]
				   || (col-arr[row-1] >= 0 && solutions[row-1][col-arr[row-1]])){
					solutions[row][col] = true;
				}
			}
		}

        return solutions[solutions.length-1][solutions[0].length-1];
    }

    // Given some items with both weight and value, as well as a max capacity
    // determine the most value we can fit in the knapsack

    // Note that the weight and value of each item is stored in same index of the relevant array
    // (e.g. weights[2] is the weight of item 2 and values[2] is that same item's value)
    public static int knapsack(int[] weights, int[] values, int capacity) {
		int[][] solutions = new int[weights.length+1][capacity+1];

		for(int items = 1; items < solutions.length; items++){
			for(int capacities = 1; capacities < solutions[0].length; capacities++){
				if(capacities-weights[items-1] < 0){
					solutions[items][capacities] = solutions[items-1][capacities];
				} else {
					solutions[items][capacities] = Math.max(solutions[items-1][capacities], solutions[items-1][capacities-weights[items-1]] + values[items-1]);
				}
			}
		}

        return solutions[solutions.length-1][solutions[0].length-1];
    }

    public static void main(String[] args) {
        // --------------------------
        // Test 1: Basic Subset Sum
        // --------------------------
        System.out.println("-------------------");
        System.out.println("Test 1: Basic Subset Sum");
        System.out.println("Expected:");
        System.out.println("true\n" +
                "true\n" +
                "true\n" +
                "false");

        System.out.println("\nGot:");


        // Does {1, 1, 3, 7} have a subset sum of 5?
        int[] arr1 = {1, 1, 3, 7};
        System.out.println(subsetSum(arr1, 5));
        // true

        // Does {1, 1, 3, 7} have a subset sum of 11?
        System.out.println(subsetSum(arr1, 11));
        // true

        // Does {1, 1, 3, 7} have a subset sum of 12?
        System.out.println(subsetSum(arr1, 12));
        // true

        // Does {1, 1, 3, 7} have a subset sum of 6?
        System.out.println(subsetSum(arr1, 6));
        // false

        // --------------------------
        // Test 2: More Subset Sum
        // --------------------------
        System.out.println("-------------------");
        System.out.println("Test 2: More Subset Sum");
        System.out.println("Expected:");
        System.out.println("true\n" +
                "true\n" +
                "true\n" +
                "false");

        System.out.println("\nGot:");


        // Does {1, 1, 5, 6, 8, 10, 12, 14, 16} have a subset sum of 11?
        int[] arr2 = {1, 1, 5, 6, 8, 10, 12, 14, 16};
        System.out.println(subsetSum(arr2, 11));
        // true

        // Does {1, 1, 5, 6, 8, 10, 12, 14, 16} have a subset sum of 28?
        System.out.println(subsetSum(arr2, 28));
        // true

        // Does {1, 1, 5, 6, 8, 10, 12, 14, 16} have a subset sum of 32?
        System.out.println(subsetSum(arr2, 32));
        // true

        // Does {1, 1, 5, 6, 8, 10, 12, 14, 16} have a subset sum of 68?
        System.out.println(subsetSum(arr1, 68));
        // true

        // --------------------------
        // Test 3: Basic Knapsack
        // --------------------------
        System.out.println("-------------------");
        System.out.println("Test 3: Basic Knapsack");
        System.out.println("Expected:");
        System.out.println("160\n" +
                "290\n" +
                "200");

        System.out.println("\nGot:");

        // Basic Tests
        int[] weights1 = {5, 10, 25};
        int[] values1 = {70, 90, 140};
        System.out.println(knapsack(weights1, values1, 25));
        // 160

        int[] weights2 = {5, 10, 20};
        int[] values2 = {150, 60, 140};
        System.out.println(knapsack(weights2, values2, 30));
        // 290

        int[] weights3 = {5, 20, 10};
        int[] values3 = {50, 140, 60};
        System.out.println(knapsack(weights3, values3, 30));
        // 200

        // --------------------------
        // Test 4: Advanced Knapsack
        // --------------------------
        System.out.println("-------------------");
        System.out.println("Test 4: Advanced Knapsack");
        System.out.println("Expected:");
        System.out.println("117");

        System.out.println("\nGot:");

        // More advanced test
        int[] weights4 = {85, 26, 48, 21, 22, 95, 43, 45, 55, 52};
        int[] values4 = {79, 32, 47, 18, 26, 85, 33, 40, 45, 59};
        System.out.println(knapsack(weights4, values4, 101));
        // 117
    }
}
