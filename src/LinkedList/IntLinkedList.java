package LinkedList;

import StacksAndQueues.Queue;

public class IntLinkedList implements Queue {
    // Each IntLinkedList keeps track of its own head node
    private IntNode head;

    // Constructor: The IntLinkedList starts empty, as the head is null
    public IntLinkedList() {
        head = null;
    }

    // toString function that is called when LinkedList is printed
    // Allows us to print out the LinkedList in a nice format: 1 -> 2 -> 3 -> null
    public String toString() {
        String str = "";
        IntNode current = head;
        while (current != null)
        {
            str = str + current.data + " -> ";
            current = current.next;
        }
        str +=  "null";
        return str;
    }

    public void push(int data) {
        insertFirst(data);
    }

    public int pop() {
        return removeFirst();
    }

    public int peek() {
        if (head == null){
            return -1;
        } else {
            return head.data;
        }
    }

    public boolean isEmpty(){
        return head == null;
    }

    public void enqueue(int data) {
        insertFirst(data);
    }

    public int dequeue() {
        return removeLast();
    }



    // Inserts a new node at the front of the LinkedList (prepend)
    public void insertFirst(int data) {
        IntNode newHead = new IntNode(data);
        newHead.next = head;
        this.head = newHead;
    }

    // Inserts a new node at the back of the LinkedList (append)
    public void insertLast(int data) {
        IntNode lastNode = new IntNode(data);
        IntNode current = head;
        if (current == null){
            head = lastNode;
            return;
        }
        while (current.next != null) {
            current = current.next;
        }
        current.next = lastNode;
    }

    // Returns true if val is present in the list, and false otherwise
    public boolean search(int val) {
        IntNode current = head;
        while (current != null) {
            if (current.data == val) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // Removes the first node from the LinkedList, and returns its data value
    // Returns -1 if the list is empty
    public int removeFirst() {
        if (head == null) {
            return -1;
        } else {
            int data = head.data;
            head = head.next;
            return data;
        }
    }

    // Removes the last node from the LinkedList, and returns its data value
    // Returns -1 if the list is empty
    public int removeLast() {
        if (head == null){
            return -1;
        } else if (head.next == null){
            int val = head.data;
            head = null;
            return val;
        } else {
            IntNode current = head;
            while(current.next.next != null){
                current = current.next;
            }
            int val = current.next.data;
            current.next = null;
            return val;
        }
    }

    // Removes all instances of val from the IntLinkedList
    public void removeAll(int val) {
        if (head != null){
            while (head.data == val){
                if (head.next != null) {
                    head = head.next;
                } else {
                    head = null;
                    return;
                }
            }
            IntNode current = head.next;
            IntNode prev = head;
            while (current != null){
                if(current.data == val){
                    prev.next = current.next;
                }
                prev = prev.next;
                if(prev == null){
                    return;
                }
                current = prev.next;
            }
        }
    }

    // Reverses the IntLinkedList
    public void reverse() {
        if(head!= null && head.next!=null){
            IntNode prev = null;
            IntNode current = head;
            IntNode next = head.next;

            while(current != null){
                current.next = prev;

                prev = current;
                current = next;
                if(next != null) {
                    next = current.next;
                }
            }
            head = prev;
        }
    }

    // Checks if the IntLinkedList contains a cycle
    public boolean hasCycle() {
        IntNode turtle = head;
        IntNode hare = head;

        while(hare != null && hare.next != null){
            turtle = turtle.next;
            hare = hare.next.next;
            if(turtle == hare){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        //queue test code;

        Queue queue = new IntLinkedList();
        queue.enqueue(10);
        queue.enqueue(5);
        queue.enqueue(2);
        queue.enqueue(7);

        System.out.println(queue.peek()); // 10

        while (!queue.isEmpty()) {
            System.out.println(queue.dequeue());
        }
// 10
// 5
// 2
// 7

     //Stack test code
//        Stack stack = new IntLinkedList();
//        stack.push(10);
//        stack.push(5);
//        stack.push(2);
//        stack.push(7);
//
//        System.out.println(stack.peek()); // 7
//
//        while (!stack.isEmpty()) {
//            System.out.println(stack.pop());
//        }
// 7
// 2
// 5
// 10




    // IntLinkedList testcode
//        // --------------------------
//        // Test 1: insertFirst method
//        // --------------------------
//        System.out.println("-------------------");
//        System.out.println("Test 1: insertFirst");
//        System.out.println("Expected:");
//        System.out.println("4 -> 2 -> 3 -> null");
//
//        // Make a list containing 4 -> 2 -> 3 -> null
//        IntLinkedList list1 = new IntLinkedList();
//        list1.insertFirst(3);
//        list1.insertFirst(2);
//        list1.insertFirst(4);
//        System.out.println("\nGot:");
//        System.out.println(list1); // 4 -> 2 -> 3 -> null
//
//        // --------------------------
//        // Test 2: insertLast method
//        // --------------------------
//        System.out.println("-------------------");
//        System.out.println("Test 2: insertLast");
//        System.out.println("Expected:");
//        System.out.println("7 -> 2 -> 5 -> 10 -> null");
//
//        // Make a list containing 7 -> 2 -> 5 -> 10 -> null
//        IntLinkedList list2 = new IntLinkedList();
//        list2.insertLast(7);
//        list2.insertLast(2);
//        list2.insertLast(5);
//        list2.insertLast(10);
//        System.out.println("\nGot:");
//        System.out.println(list2); // 7 -> 2 -> 5 -> 10 -> null
//
//        // --------------------------
//        // Test 3: search method
//        // --------------------------
//        System.out.println("-------------------");
//        System.out.println("Test 3: search");
//        System.out.println("Expected:");
//        System.out.println("true");
//        System.out.println("true");
//        System.out.println("true");
//        System.out.println("true");
//        System.out.println("false");
//
//        // Search the list for some different values
//        System.out.println("\nGot:");
//        System.out.println(list2.search(7)); // true
//        System.out.println(list2.search(2)); // true
//        System.out.println(list2.search(5)); // true
//        System.out.println(list2.search(10)); // true
//        System.out.println(list2.search(13)); // false
//
//        // --------------------------
//        // Test 4: removeFirst method
//        // --------------------------
//        System.out.println("-------------------");
//        System.out.println("Test 4: removeFirst");
//        System.out.println("Expected:");
//        System.out.println("2 -> 3 -> null");
//        System.out.println("2 -> 5 -> 10 -> null");
//
//        // Remove some values from the lists
//        System.out.println("\nGot:");
//        // Remove the first value from the list: 4 -> 2 -> 3 -> null
//        list1.removeFirst();
//        System.out.println(list1); // 2 -> 3 -> null
//
//        // Remove the first value from the list: 7 -> 2 -> 5 -> 10 -> null
//        list2.removeFirst();
//        System.out.println(list2); // 2 -> 5 -> 10 -> null
//
//        // --------------------------
//        // Test 5: removeLast method
//        // --------------------------
//        System.out.println("-------------------");
//        System.out.println("Test 5: removeLast");
//        System.out.println("Expected:");
//        System.out.println("2 -> null");
//        System.out.println("null");
//
//        // Remove some values from the lists
//        System.out.println("\nGot:");
//        // Remove the last value from the list: 2 -> 3 -> null
//        list1.removeLast();
//        System.out.println(list1); // 2 -> null
//        // Remove the last value from the list: 2 -> 3 -> null
//        list1.removeLast();
//        System.out.println(list1); // null
//
//        // --------------------------
//        // Test 6: removeAll method
//        // --------------------------
//        System.out.println("-------------------");
//        System.out.println("Test 6: removeAll");
//        System.out.println("Expected:");
//        System.out.println("1 -> 2 -> 3 -> 4 -> 5 -> null");
//        System.out.println("5 -> 3 -> 1 -> 2 -> 0 -> null");
//        System.out.println("null");
//
//        // Remove all 6's from the list: 1 -> 2 -> 6 -> 3 -> 4 -> 6 -> 5 -> 6 -> 6 -> null
//        System.out.println("\nGot:");
//        IntLinkedList list3 = new IntLinkedList();
//        list3.insertLast(1);
//        list3.insertLast(2);
//        list3.insertLast(6);
//        list3.insertLast(3);
//        list3.insertLast(4);
//        list3.insertLast(6);
//        list3.insertLast(5);
//        list3.insertLast(6);
//        list3.removeAll(6);
//        System.out.println(list3); // 1 -> 2 -> 3 -> 4 -> 5 -> null
//
//        // Remove all 6's from the list: 6 -> 5 -> 3 -> 6 -> 1 -> 2 -> 6 -> 0 -> null
//        list3 = new IntLinkedList();
//        list3.insertLast(6);
//        list3.insertLast(5);
//        list3.insertLast(3);
//        list3.insertLast(6);
//        list3.insertLast(1);
//        list3.insertLast(2);
//        list3.insertLast(6);
//        list3.insertLast(0);
//        list3.removeAll(6);
//        System.out.println(list3); // 5 -> 3 -> 1 -> 2 -> 0 -> null
//
//        // Remove all 6's from the list: 6 -> 6 -> 6 -> 6 -> 6 -> null
//        list3 = new IntLinkedList();
//        list3.insertLast(6);
//        list3.insertLast(6);
//        list3.insertLast(6);
//        list3.insertLast(6);
//        list3.insertLast(6);
//        list3.removeAll(6);
//        System.out.println(list3); // null
//
//        // --------------------------
//        // Test 7: reverse
//        // --------------------------
//        System.out.println("-------------------");
//        System.out.println("Test 7: reverse");
//        System.out.println("Expected:");
//        System.out.println("4 -> 2 -> 3 -> null");
//        System.out.println("7 -> 3 -> 2 -> 4 -> null");
//
//        // Reverse the list: 3 -> 2 -> 4 -> null
//        System.out.println("\nGot:");
//        IntLinkedList list4 = new IntLinkedList();
//        list4.insertLast(3);
//        list4.insertLast(2);
//        list4.insertLast(4);
//        list4.reverse();
//        System.out.println(list4); // 4 -> 2 -> 3 -> null
//
//        // Reverse the list: 4 -> 2 -> 3 -> 7 -> null
//        list4.insertLast(7);
//        list4.reverse();
//        System.out.println(list4); // 7 -> 3 -> 2 -> 4 -> null
//
//        // --------------------------
//        // Test8: hasCycle
//        // --------------------------
//        System.out.println("-------------------");
//        System.out.println("Test 8: hasCycle");
//        System.out.println("Expected:");
//        System.out.println("true");
//        System.out.println("true");
//        System.out.println("false");
//
//        // Check a list with a cycle: 3 -> 2 -> 4 --|
//        //                            ^-------------|
//        System.out.println("\nGot:");
//        IntLinkedList list5 = new IntLinkedList();
//        list5.insertLast(3);
//        list5.insertLast(2);
//        list5.insertLast(4);
//        list5.head.next.next.next = list5.head;
//        System.out.println(list5.hasCycle()); // true
//
//        // Check a list with a cycle: 3 -> 2 -> 4 -> 5 --|
//        //                                 ^-------------|
//        list5 = new IntLinkedList();
//        list5.insertLast(3);
//        list5.insertLast(2);
//        list5.insertLast(4);
//        list5.insertLast(5);
//        list5.head.next.next.next.next = list5.head.next;
//        System.out.println(list5.hasCycle()); // true
//
//        // Check a list without a cycle: 3 -> 2 -> 4 -> 5 -> null
//        list5 = new IntLinkedList();
//        list5.insertLast(3);
//        list5.insertLast(2);
//        list5.insertLast(4);
//        list5.insertLast(5);
//        System.out.println(list5.hasCycle()); // false
    }
}


