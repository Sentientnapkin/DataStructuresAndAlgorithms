package StacksAndQueues;

public class ArrayStack implements Stack {
  private int[] stack;
  private int nextOpenIndex;

  public ArrayStack (){
    stack = new int[0];
    nextOpenIndex = 0;
  }

  @Override
  public void push(int data) {
    if(nextOpenIndex >= stack.length){
      int[] newArr = new int[Math.max(stack.length*2, 1)];
      System.arraycopy(stack, 0, newArr, 0, stack.length);
      newArr[nextOpenIndex]=data;
      stack =newArr;
    } else {
      stack[nextOpenIndex] = data;
    }
    nextOpenIndex++;
  }

  @Override
  public int pop() {
    if (isEmpty()) {
      return -1;
    }
    int data = stack[nextOpenIndex-1];
    int[] newArr = new int[stack.length-1];
    System.arraycopy(stack, 0, newArr, 0, newArr.length);
    stack = newArr;
    nextOpenIndex--;
    return data;
  }

  @Override
  public int peek() {
    if(isEmpty()){
      return -1;
    }
    return stack[nextOpenIndex-1];
  }

  @Override
  public boolean isEmpty() {
    return nextOpenIndex==0;
  }

  public static void main(String[] args) {
    Stack stack = new ArrayStack();
    stack.push(10);
    stack.push(5);
    stack.push(2);
    stack.push(7);

    System.out.println(stack.peek()); // 7

    while (!stack.isEmpty()) {
      System.out.println(stack.pop());
    }
    // 7
    // 2
    // 5
    // 10
  }
}
