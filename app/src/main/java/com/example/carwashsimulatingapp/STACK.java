package com.example.carwashsimulatingapp;

public class STACK implements IQueuable{
    // declare
    private int front;
    private int rear;
    private int capacity;
    private String[] stack;

    // constructor
    public STACK() {
        this.front = 0;
        this.rear = 0;
        this.capacity = 100;
        this.stack = new String[capacity];
    }

    // enqueue
    @Override
    public void enqueue(String value) {
        // check stack is full or not
        if (rear == capacity) {
            System.out.printf("\nStack is full\n");
        }
        else {
            //insert value at the rear of the stack array
            stack[rear] = value;
            rear++;
        }
    }

    // dequeue
    @Override
    public String dequeue() {
        String dequeuedValue;
        //check if stack is empty
        if (front == rear){
            System.out.printf("\nStack is empty\n");
            return null;
        }
        else {
            //rear decrement
            rear--;
            //store the dequeued value (last value)
            dequeuedValue = stack[rear];
            // remove the last value by setting to null
            stack[rear] = null;
        }
        return dequeuedValue;
    }

    // get the stack
    @Override
    public String[] getQueue() {
        //check if stack is empty
        if (front == rear) {
            System.out.printf("\nStack is Empty\n");
            return null;
        }
        else {
            int end = rear-1;
            String[] tempStack = new String[rear];
            for (int i = 0; i < rear; i++) {
                tempStack[i] = stack[end];
                end--;
            }
            return tempStack;
        }
    }

    // get the stack size
    @Override
    public int size() {
        return rear;
    }
}
