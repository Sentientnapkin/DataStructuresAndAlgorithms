package Algorithms.Dynamic;
import java.util.*;

public class RNA {
 // Returns true if the given characters are a matching pair
    // (i.e. one of 'A'+'U', 'U'+'A', 'C'+'G', or 'G'+'C')
    public static boolean isPair(char b1, char b2) {
        return ((b1 == 'A') && (b2 == 'U')) ||
               ((b1 == 'U') && (b2 == 'A')) ||
               ((b1 == 'C') && (b2 == 'G')) ||
               ((b1 == 'G') && (b2 == 'C'));
    }

    // Given a sequence of base pairs (e.g. "ACCGGUAGU"),
    // finds the number of base pairs that can match while maintaining
    // the following constraints:
    // * No sharp turns: each pair must be separated by at least 4 intervening bases; (i < j - 4)
    // * A pair must be either A and U or C and G
    // * A single base can only appear in at most one pair
    // * Non-crossing condition: if (i, j) and (k, l) are both pairs, we cannot have i < k < j < l
    public static int numberBasePairs(String sequence) {
		int n = sequence.length();
		int[][] table = new int[n][n];

        for (int k = 5; k < n; k++) {
            for (int i = 0; i < n - k; i++) {
                int j = i + k;

				int largestT = 0;
                for(int t = i; t < j; t++){
					if(t > i+3 && isPair(sequence.charAt(i), sequence.charAt(t))){
						int combinations = 1 + table[i+1][t-1] + table[t+1][j];
						largestT = Math.max(largestT, combinations);
					}

					if(t < j-3 && isPair(sequence.charAt(t), sequence.charAt(j))){
						int combinations = (t>0) ? 1 + table[i][t-1] + table[t+1][j-1] : 1 + table[t+1][j-1];
						largestT = Math.max(largestT, combinations);
					}

					if (!(isPair(sequence.charAt(i), sequence.charAt(t)) || isPair(sequence.charAt(t), sequence.charAt(j)))){
						int combinations = table[i+1][j-1];
						largestT = Math.max(largestT, combinations);
					}
				}

				table[i][j] = largestT;
            }
        }

        return table[0][table[0].length-1];
    }

    public static void main(String[] args) {
        // --------------------------
        // Test 1: RNA Structure
        // --------------------------
        System.out.println("-------------------");
        System.out.println("Test 1: Longest Continuous Palindrome");
        System.out.println("Expected:");
        System.out.println("2\n" +
                "5\n" +
                "15\n" +
                "14");

		System.out.println("\nGot: ");

        System.out.println(numberBasePairs("ACCGGUAGU")); // 2
        System.out.println(numberBasePairs("ACAUGAUGGCCAUGU")); // 5
        System.out.println(numberBasePairs("ACACACACACACACAAAUUUGUGUGUGUGUGUGU")); // 15
        System.out.println(numberBasePairs("CAGAUCGGCGAUACGAGCAUAGCAAUGCUAAGCGAGCUUAGCUGCA")); // 14
    }
}
