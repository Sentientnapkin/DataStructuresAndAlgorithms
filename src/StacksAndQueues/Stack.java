package StacksAndQueues;

public interface Stack {
  // Pushes some data value to the top of the Stack
  public void push(int data);
  // Pops some data value off the top of the Stack
  public int pop();
  // Returns the data value at the top of the stack without popping
  public int peek();
  // Checks whether or not the stack is empty
  public boolean isEmpty();
}