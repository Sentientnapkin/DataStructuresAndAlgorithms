package StacksAndQueues;

public interface Queue {
  // Enqueues a value to the end of the queue
  public void enqueue(int data);
  // Dequeues a value from the front of the queue
  public int dequeue();
  // Returns the data value at the front of the queue without dequeuing
  public int peek();
  // Checks whether or not the queue is empty
  public boolean isEmpty();
}