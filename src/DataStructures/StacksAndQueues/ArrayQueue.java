package DataStructures.StacksAndQueues;

public class ArrayQueue implements Queue {
  public int[] queue;
  private int nextOpenIndex;

  public ArrayQueue () {
    queue = new int[0];
    nextOpenIndex = 0;
  }

  @Override
  public void enqueue(int data) {
    if(nextOpenIndex >= queue.length){
      int[] newArr = new int[Math.max(queue.length*2, 1)];
      System.arraycopy(queue, 0, newArr, 0, queue.length);
      newArr[nextOpenIndex]=data;
      queue =newArr;
    } else {
      queue[nextOpenIndex] = data;
    }
    nextOpenIndex++;
  }

  @Override
  public int dequeue() {
    if (isEmpty()) {
      return -1;
    }
    int data = queue[0];
    int[] newArr = new int[queue.length-1];
    System.arraycopy(queue, 1, newArr, 0, newArr.length);
    queue = newArr;
    nextOpenIndex--;
    return data;
  }

  @Override
  public int peek() {
    if (isEmpty()){
      return -1;
    }
    return queue[0];
  }

  @Override
  public boolean isEmpty() {
    return nextOpenIndex==0;
  }

  public static void main(String[] args) {
    // --------------------------
    // Test 1: Basic Queue Functionality
    // --------------------------
    System.out.println("-------------------");
    System.out.println("Test 1: Basic Queue Functionality");
    System.out.println("Expected:");
    System.out.println("10");
    System.out.println("10");
    System.out.println("10");
    System.out.println("5");
    System.out.println("\nGot:");
    // Add 10 and 5 to queue and peek twice
    Queue queue = new ArrayQueue();
    queue.enqueue(10);
    System.out.println(queue.peek()); // 10
    queue.enqueue(5);
    System.out.println(queue.peek()); // 10
    // Remove 10 from the queue and peek again
    System.out.println(queue.dequeue()); // 10
    System.out.println(queue.peek()); // 5
    // --------------------------
    // Test 2: More Queue Testing
    // --------------------------
    System.out.println("-------------------");
    System.out.println("Test 2: More Queue Testing");
    System.out.println("Expected:");
    System.out.println("10");
    System.out.println("5");
    System.out.println("2");
    System.out.println("7");
    System.out.println("\nGot:");
    // Add 10, 5, 2 and 7
    queue = new ArrayQueue();
    queue.enqueue(10);
    queue.enqueue(5);
    queue.enqueue(2);
    queue.enqueue(7);
    // Dequeue the queue completely
    while (!queue.isEmpty()) {
      System.out.println(queue.dequeue());
    }
    // 10
    // 5
    // 2
    // 7
    // --------------------------
    // Test 3: Larger Queue
    // --------------------------
    System.out.println("-------------------");
    System.out.println("Test 3: Larger");
    System.out.println("Expected:");
    System.out.println("This test should print out all numbers between 0 and 199");
    System.out.println("\nGot:");
    for (int i = 0; i < 200; i++) {
      queue.enqueue(i);
    }
    while (!queue.isEmpty()) {
      System.out.println(queue.dequeue());
    }
  }
}
