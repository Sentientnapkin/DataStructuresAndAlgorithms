package Algorithms.RandomizedAlgorithms;

import java.util.LinkedList;
import java.util.Arrays;

public class Median {
    // Wrapper method to call the recursive select method
    public static int median(LinkedList<Integer> S) {
        return select(S, (S.size() + 1)/2);
    }

    // Returns the k-th largest value in S, recursively
    
    /*
    Select(S, k): 
    Choose S[0] to be the random splitter x
    For each other number b in S: 
        Put b in S- if b < x 
        Put b in S+ if b > x 

    If size(S-)== k - 1 
        Return x 
    Else if size(S-) >= k 
        Return Select(S-, k) 
    Else 
        Return Select(S+, k - 1 - size(S-))
     */
    public static int select(LinkedList<Integer> S, int k) {
        int splitter = S.pollFirst();
        
        LinkedList<Integer> sMinus = new LinkedList<>();
        LinkedList<Integer> sPlus = new LinkedList<>();
        for(int e: S){
            if (e < splitter){
                sMinus.add(e);
            } else {
                sPlus.add(e);
            }
        }

        if(sMinus.size() == k-1){
            return splitter;
        } else if (sMinus.size() >= k){
            return select(sMinus, k);
        } else {
            return select(sPlus, k - 1 - sMinus.size());
        }
    }

    public static void main(String[] args) {
        // --------------------------
        // Test 1: Median
        // --------------------------
        System.out.println("-------------------");
        System.out.println("Test 1: Median");
        System.out.println("Expected:");
        System.out.println("5\n" +
                "5\n" +
                "34");

        System.out.println("\nGot:");

        LinkedList<Integer> list1 = new LinkedList<>(Arrays.asList(9, 5, 1, 8, 2, 3, 4, 6, 7));
        System.out.println(median(list1));
        // 5

        LinkedList<Integer> list2 = new LinkedList<>(Arrays.asList(9, 5, 1, 8, 2, 3, 4, 6, 7, 10));
        System.out.println(median(list2));
        // 5

        LinkedList<Integer> list3 = new LinkedList<>(Arrays.asList(13, 24, 51, 67, 32, 78, 12, 66, 34, 56, 21, 53, 87, 93, -11, 23, 0));
        System.out.println(median(list3));
        // 34
    }
}
