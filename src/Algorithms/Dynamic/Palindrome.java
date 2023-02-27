package Algorithms.Dynamic;

public class Palindrome {

    // Part 1: Longest Continuous Palindrome
    // Some of the code outline has been provided
    public static int palindrome(String x) {
        // You'll likely want to keep track of the longest substring you see
        int max = 1;

        // Make the table
        int n = x.length();
        boolean[][] table = new boolean[n][n];

        // Base cases, fill in base case diagonals
        /* Here is some example code of how to go through 1 diagonal
        for (int i = 0; i < n; i++) {
            System.out.println(table[i][i]);
        }
        */

        for(int i = 0; i < table.length-1; i++){
			table[i][i] = true;
			table[i][i+1] = x.charAt(i) == x.charAt(i+1);
		}
		table[table.length-1][table[0].length-1] = true;

        // Looping through the rest of the diagonals
        // Note that k is the length of substring along the current diagonal
        // We loop through i, and then calculate j based on k and i
        for (int k=3; k <= n; k++) {
            for (int i=0; i<= n-k; i++) {
                // We calculate j based on i and k
                int j = i+k-1;

                table[i][j] = table[i+1][j-1] && x.charAt(i) == x.charAt(j);
				if(j-i > max && table[i][j]){
					max = j - i + 1;
				}
            }
        }

        return max;
    }

    // Part 2: Palindrome Completion
    public static int complete(String str) {
		int n = str.length();
		int[][] table = new int[n][n];

		for (int k=2; k <= n; k++) {
			for (int i=0; i<= n-k; i++) {
				// We calculate j based on i and k
				int j = i+k-1;

				if(str.charAt(i)==str.charAt(j)){
					table[i][j] = table[i+1][j-1];
				} else {
					table[i][j] = Math.min(table[i+1][j], table[i][j-1]) + 1;
				}
			}
		}

		return table[0][n-1];
    }



    public static void main(String[] args) {
        // --------------------------
        // Test 1: Longest Continuous Palindrome
        // --------------------------
        System.out.println("-------------------");
        System.out.println("Test 1: Longest Continuous Palindrome");
        System.out.println("Expected:");
        System.out.println("5\n" +
                "7");

        System.out.println("\nGot:");
        System.out.println(palindrome("BABBCCCB")); // 5
        System.out.println(palindrome("helloasdfasdfracecarjkljkl")); // 7

        // --------------------------
        // Test 2: Palindrome Completion
        // --------------------------
        System.out.println("-------------------");
        System.out.println("Test 2: Palindrome Completion");
        System.out.println("Expected:");
        System.out.println("3\n" +
                "4\n" +
                "2\n" +
                "2\n" +
                "5\n");

        System.out.println("\nGot:");
        System.out.println(complete("race")); // 3 to get to racecar
        System.out.println(complete("abcde")); // 4 to get to abcdedcba
        System.out.println(complete("mda")); // 2 to get to madam
        System.out.println(complete("tcoat")); // 2 to get to tacocat
        System.out.println(complete("amsdfudksv")); // 5 to get to vamskdfufdksmav
    }
}
