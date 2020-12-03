package ua.edu.ucu.immutable;



public class Queue {
    private ImmutableLinkedList list;

    public Queue() {
        list = new ImmutableLinkedList();
    }

    public Object peek() {
        return list.getFirst();
    }

    public Object dequeue() {
        Object el = list.getFirst();
        list = list.removeFirst();
        return el;
    }

    public void enqueue(Object e) {
        list = list.addLast(e);
    }
}