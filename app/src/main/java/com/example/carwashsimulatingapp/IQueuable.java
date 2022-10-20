package com.example.carwashsimulatingapp;

public interface IQueuable {

    //adds value to queue and returns new queue
    public void enqueue(String value);

    //removes item from queue, and returns the item removed
    public String dequeue();

    //returns a list of all the items in the queue
    public String[] getQueue();

    //returns the number of items in the queue
    public int size();

}
