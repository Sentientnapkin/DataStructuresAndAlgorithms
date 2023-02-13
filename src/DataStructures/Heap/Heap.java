package DataStructures.Heap;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * Name:
 * List Anyone You Collaborated With (if any):
 */

public class Heap {
    int[] arr;
    int maxSize;
    int currentSize;

    public Heap() {
        maxSize = 100;
        currentSize = 0;
        arr = new int[maxSize];
    }

    // Calculate the array index of the parent
    private int parent(int index) {
        return (index-1) / 2;
    }

    // Calculate the array index of the first child
    private int child1(int index) {
        return (2*index)+1;
    }

    // Calculate the array index of the second child
    private int child2(int index) {
        return (2*index)+2;
    }

    // Returns the smallest value in the heap; returns -1
    // if the heap is empty
    public int peek() {
        if (currentSize == 0) {
            return -1;
        }
        else {
            return arr[0];
        }
    }

    // Move the element with the given index up to its correct location
    public void trickleUp(int index) {
        int trickledIndex = index;

        while(trickledIndex!= 0 && arr[trickledIndex] < arr[parent(trickledIndex)]){
            int temp = arr[trickledIndex];
            arr[trickledIndex] = arr[parent(trickledIndex)];
            arr[parent(trickledIndex)] = temp;
            trickledIndex = parent(trickledIndex);
        }
    }

    // Insert a new element
    // Most of the work will be done by the trickleUp() method
    public void insert(int key) {
        if(currentSize==maxSize-1){
            maxSize*=2;
            int[] newArr = new int[maxSize];
            System.arraycopy(arr, 0, newArr, 0, maxSize/2);
            arr = newArr;
        }

        arr[currentSize] = key;
        trickleUp(currentSize);
        currentSize++;
    }

    public int getSmallestChildIndex (int index){
        //leaf
        if(child1(index) >= currentSize && child2(index) >= currentSize){
            return -1;
        }
        //only right side exists
        if(child1(index) >= currentSize){
            return child2(index);
        }
        //only left side exists
        if(child2(index) >= currentSize){
            return child1(index);
        }

        //find smallest child if both exist
        return arr[child1(index)] < arr[child2(index)] ? child1(index) : child2(index);
    }

    public boolean shouldTrickle(int index){
        return ((child1(index)<currentSize && arr[index] > arr[child1(index)])
                || (child2(index)<currentSize) && arr[index] > arr[child2(index)]);
    }

    // Move the element with the given index down to it's correct location
    public void trickleDown(int index) {
        int trickledIndex = index;

        while(shouldTrickle(trickledIndex)){
            int smallestIndex = getSmallestChildIndex(trickledIndex);
            int temp = arr[trickledIndex];
            arr[trickledIndex] = arr[smallestIndex];
            arr[smallestIndex] = temp;
            trickledIndex = smallestIndex;
        }
    }

    // Delete the minimum element; returns -1 if the heap is empty
    public int deleteMin() {
        if (currentSize==0){
            return -1;
        }

        int returnValue = arr[0];
        arr[0] = arr[currentSize-1];
        arr[currentSize-1] = 0;
        currentSize--;
        trickleDown(0);

        return returnValue;
    }

    // Searches for some element and changes it value to a new value
    // The element will then (likely) need to change location
    public void change(int oldValue, int newValue) {
        int oldIndex = -1;
        for(int i = 0;i<currentSize;i++){
            if(arr[i]==oldValue){
                oldIndex = i;
            }
        }

        if(oldIndex < 0){
            return;
        }

        arr[oldIndex] = newValue;
        trickleDown(oldIndex);
        trickleUp(oldIndex);
    }

    // A simple heapsort using the default heapsort algorithm
    public static void heapSort(int[] unsortedArr) {
        Heap sorted = new Heap();
        for (int value : unsortedArr) {
            sorted.insert(value);
        }

        for(int i = 0;i<unsortedArr.length;i++){
            unsortedArr[i] = sorted.deleteMin();
        }
    }

    // A faster implementation of heapsort that "heapifies"
    // the array rather than inserting the values individually
    public static void fasterHeapSort(int[] unsortedArr) {
        Heap sorted = new Heap();
        System.arraycopy(unsortedArr, 0, sorted.arr, 0, unsortedArr.length);
        sorted.currentSize = unsortedArr.length;

        for(int i = unsortedArr.length-1; i>=0;i--){
            sorted.trickleDown(i);
        }

        for(int i = 0;i<unsortedArr.length;i++){
            unsortedArr[i] = sorted.deleteMin();
        }
    }

    // Creates an array of 100 random numbers between 0 and 10000
    public static int[] generator100() {
        int[] x = new int[100];
        for (int i = 0; i < 100; i++) {
            x[i] = ((int) (Math.random() * 10000));
        }

        return x;
    }

    // Duplicates a given array of size 100
    public static int[] duplicate100(int[] arr) {
        int[] x = new int[100];
        for (int i = 0; i < 100; i++) {
            x[i] = arr[i];
        }

        return x;
    }

    // Print function: we'll just make it into a tree to make it easier for us
    // to visualize what is going on
    public void print() {
        HeapNode[] nodes = new HeapNode[currentSize];
        for (int i = 0; i < currentSize; i++) {
            nodes[i] = new HeapNode(arr[i]);
        }

        for (int i = 0; i < currentSize; i++) {
            int child1Index = child1(i);
            int child2Index = child2(i);

            if (child1Index < currentSize) {
                nodes[i].left = nodes[child1Index];
            }
            if (child2Index < currentSize) {
                nodes[i].right = nodes[child2Index];
            }
        }

        if (currentSize > 0) {
            BTreePrinter.printNode(nodes[0]);
        }
    }

    public static void main(String[] args) {
        // --------------------------
        // Test 1: Insertion and Trickle Up
        // --------------------------
        System.out.println("-------------------");
        System.out.println("Test 1: Insertion and Trickle Up");
        System.out.println("Expected:");
        System.out.println(" 7   \n" +
                "/ \\ \n" +
                "8 9 \n");
        System.out.println("   1       \n" +
                "  / \\   \n" +
                " /   \\  \n" +
                " 2   3   \n" +
                "/ \\ /   \n" +
                "8 7 9  ");

        System.out.println("\nGot:");
        Heap test = new Heap();

        // These nodes are actually added in a way that doesn't
        // mess up the binary heap property
        test.insert(7);
        test.insert(8);
        test.insert(9);
        test.print();
        //  7
        // / \
        // 8 9

        // Adding these next few nodes requires some trickle-ups!
        test.insert(1);
        test.insert(2);
        test.insert(3);

        test.print();
        //    1
        //   / \
        //  /   \
        //  2   3
        // / \ /
        // 8 7 9

        // --------------------------
        // Test 2: Delete Min and Trickle Down
        // --------------------------
        System.out.println("-------------------");
        System.out.println("Test 2: Delete Min and Trickle Down");
        System.out.println("Expected:");
        System.out.println("   2       \n" +
                "  / \\   \n" +
                " /   \\  \n" +
                " 7   3   \n" +
                "/ \\     \n" +
                "8 9 \n");
        System.out.println(" 7   \n" +
                "/ \\ \n" +
                "8 9 ");

        System.out.println("\nGot:");

        // Delete a minimum value
        test.deleteMin();
        test.print();
        //    2
        //   / \
        //  /   \
        //  7   3
        // / \
        // 8 9

        // Delete some more minimum values
        test.deleteMin();
        test.deleteMin();
        test.print();
        //  7
        // / \
        // 8 9

        // --------------------------
        // Test 3: Change (trickle up)
        // --------------------------
        System.out.println("-------------------");
        System.out.println("Test 3: Change (trickle up)");
        System.out.println("   2       \n" +
                "  / \\   \n" +
                " /   \\  \n" +
                " 4   3   \n" +
                "/ \\ / \\ \n" +
                "8 7 9 6 \n");
        System.out.println("   1       \n" +
                "  / \\   \n" +
                " /   \\  \n" +
                " 2   3   \n" +
                "/ \\ / \\ \n" +
                "8 4 9 6 ");

        System.out.println("\nGot:");
        // First, insert some nodes
        test.insert(3);
        test.insert(4);
        test.insert(5);
        test.insert(6);
        // test.print();
        //    3
        //   / \
        //  /   \
        //  4   5
        // / \ / \
        // 8 7 9 6

        // Then change values of other nodes that trickle up
        test.change(5, 2);
        test.print();
        //    2
        //   / \
        //  /   \
        //  4   3
        // / \ / \
        // 8 7 9 6

        test.change(7, 1);
        test.print();
        //    1
        //   / \
        //  /   \
        //  2   3
        // / \ / \
        // 8 4 9 6

        // --------------------------
        // Test 4: Change (trickle down)
        // --------------------------
        System.out.println("-------------------");
        System.out.println("Test 4: Change (trickle down)");
        System.out.println("   1       \n" +
                "  / \\   \n" +
                " /   \\  \n" +
                " 2   6   \n" +
                "/ \\ / \\ \n" +
                "8 4 9 10\n");
        System.out.println("   2       \n" +
                "  / \\   \n" +
                " /   \\  \n" +
                " 4   6   \n" +
                "/ \\ / \\ \n" +
                "8 20 9 10 ");

        System.out.println("\nGot:");
        // test.print();
        //    1
        //   / \
        //  /   \
        //  2   3
        // / \ / \
        // 8 4 9 6

        // Change values of nodes that trickle down
        test.change(3, 10);
        test.print();
        //    1
        //   / \
        //  /   \
        //  2   6
        // / \ / \
        // 8 4 9 10

        test.change(1, 20);
        test.print();
        //    2
        //   / \
        //  /   \
        //  4   6
        // / \ / \
        // 8 20 9 10

        // --------------------------
        // Test 6: Normal Heapsort
        // --------------------------
        System.out.println("-------------------");
        System.out.println("Test 5: Normal Heapsort");
        System.out.println("1 2 3 5 7 8 23 32 43 44 53 67 78 90");

        System.out.println("\nGot:");
        int[] testArr = {3, 1, 5, 2, 7, 8, 23, 53, 43, 44, 78, 32, 67, 90};
        heapSort(testArr);
        for (int x : testArr) {
            System.out.print(x + " ");
        }
        System.out.println();
        // 1 2 3 5 7 8 23 32 43 44 53 67 78 90

        // --------------------------
        // Test 6: Faster Heapsort
        // --------------------------
        System.out.println("-------------------");
        System.out.println("Test 6: Faster Heapsort");
        System.out.println("1 2 3 5 7 8 23 32 43 44 53 67 78 90");

        System.out.println("\nGot:");
        int[] testArr2 = {3, 1, 5, 2, 7, 8, 23, 53, 43, 44, 78, 32, 67, 90};
        fasterHeapSort(testArr2);
        for (int x : testArr2) {
            System.out.print(x + " ");
        }
        System.out.println();
        // 1 2 3 5 7 8 23 32 43 44 53 67 78 90

        // --------------------------
        // Test 7: Heapsort time comparisons!
        // --------------------------
        System.out.println("-------------------");
        System.out.println("Test 7: Faster Heapsort");
        // Write your own code using System.nanoTime() to determine
        // the relative speed of your two heapsort implementations
        // Make sure to only use System.nanoTime() to record the time
        // spent sorting, and to not record the time making the arrays
        // of random numbers.
        // You should use generate100() to make random arrays and
        // duplicate100() to duplicate the random array

        long NormalTotalTime = 0;
        long FastTotalTime = 0;
        for(int trial = 0;trial<10000;trial++){
            int[] randomsNormal = generator100();
            int[] randomsFast = duplicate100(randomsNormal);

            long startTime = System.nanoTime();
            heapSort(randomsNormal);
            long stopTime = System.nanoTime();
            NormalTotalTime += stopTime - startTime;

            startTime = System.nanoTime();
            fasterHeapSort(randomsFast);
            stopTime = System.nanoTime();
            FastTotalTime += stopTime - startTime;
        }

        System.out.println("Normal heapsort takes " + NormalTotalTime/10000 + " ns on average (10000 trials)");
        System.out.println("Faster heapsort takes " + FastTotalTime/10000 + " ns on average (10000 trials)");
    }
}

// This class is ****just for the print function****
// Our heap does not actually use any nodes!
class HeapNode {
    int data;
    HeapNode left;
    HeapNode right;

    public HeapNode(int data) {
        this.data = data;
    }
}

// Print binary tree in a helpful way
// from: https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram
class BTreePrinter {

    public static <T extends Comparable<?>> void printNode(HeapNode root) {
        int maxLevel = BTreePrinter.maxLevel(root);

        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private static <T extends Comparable<?>> void printNodeInternal(List<HeapNode> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || BTreePrinter.isAllElementsNull(nodes))
            return;

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        BTreePrinter.printWhitespaces(firstSpaces);

        List<HeapNode> newNodes = new ArrayList<HeapNode>();
        for (HeapNode node : nodes) {
            if (node != null) {
                System.out.print(node.data);
                newNodes.add(node.left);
                newNodes.add(node.right);
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            BTreePrinter.printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                BTreePrinter.printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    BTreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (nodes.get(j).left != null)
                    System.out.print("/");
                else
                    BTreePrinter.printWhitespaces(1);

                BTreePrinter.printWhitespaces(i + i - 1);

                if (nodes.get(j).right != null)
                    System.out.print("\\");
                else
                    BTreePrinter.printWhitespaces(1);

                BTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
            }

            System.out.println("");
        }

        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private static <T extends Comparable<?>> int maxLevel(HeapNode node) {
        if (node == null)
            return 0;

        return Math.max(BTreePrinter.maxLevel(node.left), BTreePrinter.maxLevel(node.right)) + 1;
    }

    private static <T> boolean isAllElementsNull(List<T> list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }

}
