package Trie;

public class Trie {
    TrieNode root;

    // Each Trie starts with a root node
    // All children are currently null
    public Trie() {
        root = new TrieNode();
    }

    public int charToIndex(char c){
        return c - 'a';
    }

    public char indexToChar(int index){
        return (char) (index + 'a');
    }

    // Inserts a String into the Trie
    public void add(String str) {
        TrieNode currNode = root;
        for(int i = 0;i<str.length();i++){
            int index = charToIndex(str.charAt(i));
            if(currNode.children[index]==null){
                currNode.children[index] = new TrieNode();
            }
            currNode = currNode.children[index];
        }
        currNode.isWord=true;
    }

    // Searches the Trie for a String
    public boolean contains(String str) {
        TrieNode currNode = root;
        for(int i = 0;i<str.length();i++){
            int index = charToIndex(str.charAt(i));
            if(currNode.children[index]==null){
                return false;
            }
            currNode = currNode.children[index];
        }
        return currNode.isWord;
    }

    // "Deletes" a String from the Trie
    // In actuality, we will just unmark the String as a word
    public void remove(String str) {
        if(!this.contains(str)){
            return;
        }
        TrieNode currNode = root;
        for(int i = 0;i<str.length();i++){
            int index = charToIndex(str.charAt(i));
            currNode = currNode.children[index];
        }
        currNode.isWord = false;
    }

    // Calls the printRec method
    public void print() {
        printRec(this.root, "");
    }

    // Recursively prints out the Trie
    // String str will keep track of the "word so far"
    // Should print out any words
    // Should call the printRec method on all 26 children
    public void printRec(TrieNode current, String str) {
        if(current==null){
            return;
        }
        if(current.isWord){
            System.out.println(str);
        }
        for(int i = 0;i<26;i++){
            printRec(current.children[i], str + indexToChar(i));
        }
    }

    public static void main(String[] args) {
        // --------------------------
        // Test 1: Insertion (add)
        // --------------------------
        System.out.println("-------------------");
        System.out.println("Test 1: Insertion (add)");
        System.out.println("Expected:");
        System.out.println("hi\n" +
                "his\n" +
                "hiss\n" +
                "history\n" +
                "world\n");

        System.out.println("\nGot:");

        Trie test = new Trie();

        test.add("hi");
        test.add("world");
        test.add("his");
        test.add("history");
        test.add("hiss");

        test.print();
        System.out.println();
        // hi
        // his
        // hiss
        // history
        // world

        // --------------------------
        // Test 2: Deletion (remove)
        // --------------------------
        System.out.println("-------------------");
        System.out.println("Test 2: Deletion (remove)");
        System.out.println("Expected:");
        System.out.println("hi\n" +
                "hiss\n" +
                "history\n" +
                "world\n");

        System.out.println("\nGot:");
        test.remove("his");
        test.print();
        System.out.println();
        // hi
        // hiss
        // history
        // world

        // --------------------------
        // Test 3: Search (contains) and other tests
        // --------------------------
        System.out.println("-------------------");
        System.out.println("Test 3: Search (contains) and other tests");
        System.out.println("Expected:");
        System.out.println("false\n" +
                "true\n" +
                "false\n" +
                "false");

        System.out.println("\nGot:");
        Trie trie1 = new Trie();
        System.out.println(trie1.contains("card"));
        // false

        trie1.add("card");
        System.out.println(trie1.contains("card"));
        // true
        System.out.println(trie1.contains("car"));
        // false

        trie1.remove("card");
        System.out.println(trie1.contains("card"));
        // false
    }
}

// Each TrieNode keeps track of if it is the end
// of a word, as well as an array of 26 possible children
class TrieNode {
    TrieNode[] children;
    boolean isWord;

    public TrieNode() {
        children = new TrieNode[26];
        isWord = false;
    }
}