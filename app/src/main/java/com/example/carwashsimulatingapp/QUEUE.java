package com.example.carwashsimulatingapp;

public class QUEUE implements IQueuable{

    // declare
    private int front;
    private int rear;
    private int capacity;
    private String[] queue;

    // constructor
    public QUEUE() {
        this.front = 0;
        this.rear = 0;
        this.capacity = 100;
        this.queue = new String[capacity];
    }

    // enqueue
    @Override
    public void enqueue(String value) {
        // check queue is full or not
        if (rear == capacity) {
            System.out.printf("\nQueue is full\n");
        }
        else {
            //insert value at the rear of the queue array
            queue[rear] = value;
            rear++;
        }
    }

    // dequeue
    @Override
    public String dequeue() {
        String dequeuedValue;
        //if queue is empty
        if (front == rear){
            System.out.printf("\nQueue is empty\n");
            return null;
        }
        // queue is not empty
        else {
            //store the dequeued value (first value)
            dequeuedValue = queue[front];
            // remove the first value by moving all values [index 2 until rear] one position before its position
            for (int i = 0; i < rear; i++) {
                queue[i] = queue[i + 1];
            }
            //rear decrement
            rear--;
        }
        return dequeuedValue;
    }

    // get the queue list
    @Override
    public String[] getQueue() {
        if (front == rear) {
            System.out.printf("\nQueue is Empty\n");
            return null;
        }
        else {
            String[] tempQueue = new String[rear];
            for (int i = 0; i < rear; i++) {
                tempQueue[i] = queue[i];
            }
            return tempQueue;
        }
    }

    // get the queue size
    @Override
    public int size() {
        return rear;
    }

}
