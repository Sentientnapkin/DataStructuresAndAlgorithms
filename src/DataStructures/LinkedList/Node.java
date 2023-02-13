package DataStructures.LinkedList;

public class Node<T> {
  // The instance variables for an IntNode
  // Each IntNode will store a data value as well as
  // a next value
  T data;
  Node<T> next;

  // The constructor for an IntNode
  // This makes an IntNode that stores the given data value
  public Node(T data) {
    this.data = data;
    this.next = null;
  }

  // A method that prints out a Linked List, given a head IntNode
  public static void printList(Node head) {
    Node current = head;
    while (current != null) {
      System.out.print(current.data + " ");
      current = current.next;
    }
    System.out.println();
  }

  // The main method, where we will test our code!
  public static void main(String[] args) {
    // Code to make the first example list: 4 -> 2 -> null
    // Make the nodes
    Node<Integer> head1 = new Node(4);
    Node<Integer> A = new Node(2);
    // Make the connections
    head1.next = A;

    // Code to make the second example list: 3 -> 10 -> 2 -> null
    // Make the nodes
    Node<Integer> head2 = new Node(3);
    Node<Integer> X = new Node(10);
    Node<Integer> Y = new Node(2);
    // Make the connections
    head2.next = X;
    X.next = Y;

    // Print our lists
    printList(head1); // 2
    printList(head2); // 3
  }
}
