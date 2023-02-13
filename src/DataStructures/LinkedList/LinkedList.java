package DataStructures.LinkedList;

public class LinkedList<T> {
    // Each IntLinkedList keeps track of its own head node
    private Node<T> head;

    // Constructor: The IntLinkedList starts empty, as the head is null
    public LinkedList() {
        head = null;
    }

    // toString function that is called when LinkedList is printed
    // Allows us to print out the LinkedList in a nice format: 1 -> 2 -> 3 -> null
    public String toString() {
        String str = "";
        Node<T> current = head;
        while (current != null)
        {
            str = str + current.data + " -> ";
            current = current.next;
        }
        str +=  "null";
        return str;
    }

    // Inserts a new node at the front of the LinkedList (prepend)
    public void insertFirst(T data) {
        Node<T> newHead = new Node<T>(data);
        newHead.next = head;
        this.head = newHead;
    }

    // Inserts a new node at the back of the LinkedList (append)
    public void insertLast(T data) {
        Node<T> lastNode = new Node<T>(data);
        Node<T> current = head;
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
    public boolean search(T val) {
        Node<T> current = head;
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
    public T removeFirst() {
        if (head == null) {
            return null;
        } else {
            T data = head.data;
            head = head.next;
            return data;
        }
    }

    // Removes the last node from the LinkedList, and returns its data value
    // Returns -1 if the list is empty
    public T removeLast() {
        if (head == null){
            return null;
        } else if (head.next == null){
            T val = head.data;
            head = null;
            return val;
        } else {
            Node<T> current = head;
            while(current.next.next != null){
                current = current.next;
            }
            T val = current.next.data;
            current.next = null;
            return val;
        }
    }

    // Removes all instances of val from the IntLinkedList
    public void removeAll(T val) {
        if (head != null){
            while (head.data == val){
                if (head.next != null) {
                    head = head.next;
                } else {
                    head = null;
                    return;
                }
            }
            Node<T> current = head.next;
            Node<T> prev = head;
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
            Node<T> prev = null;
            Node<T> current = head;
            Node<T> next = head.next;

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
        Node<T> turtle = head;
        Node<T> hare = head;

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
        LinkedList<String> stringList = new LinkedList<String>();
        stringList.insertFirst("Apple");
        stringList.insertFirst("Orange");
        stringList.insertLast("Banana");
        System.out.println(stringList); // "Orange" -> "Apple" -> "Banana" -> null

        System.out.println(stringList.search("Apple")); // true
        System.out.println(stringList.search("Pineapple")); // false

        System.out.println(stringList.removeFirst()); // "Orange"
        System.out.println(stringList.removeLast()); // "Banana"
        System.out.println(stringList); // "Apple" -> null

        System.out.println("\n---------------------------------\n");

        LinkedList<Integer> intList = new LinkedList<Integer>();
        intList.insertFirst(2);
        intList.insertFirst(1);
        intList.insertLast(3);
        System.out.println(intList); // 1 -> 2 -> 3 -> null

        System.out.println(intList.search(1)); // true
        System.out.println(intList.search(4)); // false

        System.out.println(intList.removeFirst()); // "1"
        System.out.println(intList.removeLast()); // "3"
        System.out.println(intList); // "2" -> null
    }
}


