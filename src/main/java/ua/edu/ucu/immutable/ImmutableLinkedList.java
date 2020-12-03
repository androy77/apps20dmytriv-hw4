package ua.edu.ucu.immutable;

import java.util.Arrays;


public class ImmutableLinkedList implements ImmutableList {
    public static class Node {
        private final Object value;
        private Node next;

        public Node(Object value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    private Node head = null;
    private Node tail = null;
    private int size = 0;

    public ImmutableLinkedList(Object[] items) {
        this.size = items.length;
        this.head = new Node(items[0], null);
        Node prev = this.head;
        for (int i = 1; i < items.length; ++i) {
            Node cur = new Node(items[i], null);
            prev.next = cur;
            prev = cur;
        }
    }

    public ImmutableLinkedList() {
        head = null;
        tail = null;
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size + 1) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public ImmutableLinkedList add(Object e) {
        return add(size, e);
    }

    @Override
    public ImmutableLinkedList add(int index, Object e) {
        checkIndex(index);
        return addAll(index, new Object[]{e});
    }

    @Override
    public ImmutableLinkedList addAll(Object[] c) {
        return addAll(size, c);
    }

    @Override
    public ImmutableLinkedList addAll(int index, Object[] c) {
        checkIndex(index);

        Object[] temp = new Object[this.size + c.length];
        Node current = this.head;
        for (int i = 0; i < index; ++i) {
            temp[i] = current.value;
            current = current.next;
        }
        System.arraycopy(c, 0, temp, index, c.length);
        for (int i = index + c.length; i < this.size + c.length; ++i) {
            temp[i] = current.value;
            current = current.next;
        }
        return new ImmutableLinkedList(temp);
    }

    @Override
    public Object get(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException();
        }
        Node current = this.head;
        for (int i = 0; i < index; ++i) {
            current = current.next;
        }
        return current.value;
    }

    @Override
    public ImmutableLinkedList remove(int index) {
        checkIndex(index);

        Object[] temp = new Object[this.size - 1];
        Node current = this.head;
        for (int i = 0; i < this.size - 1; ++i) {
            if (i == index) current = current.next;
            temp[i] = current.value;
            current = current.next;
        }
        return new ImmutableLinkedList(temp);
    }

    @Override
    public ImmutableLinkedList set(int index, Object e) {
        checkIndex(index);
        Object[] result = new Object[size()];
        Node currentNode = this.head;
        int i = 0;

        while (currentNode != null) {
            if (i != index) {
                result[i] = currentNode.value;
            } else {
                result[i] = e;
            }
            i++;
            currentNode = currentNode.next;
        }
        return new ImmutableLinkedList(result);
    }

    @Override
    public int indexOf(Object e) {
        Node cur = this.head;
        for (int i = 0; i < this.size; ++i) {
            if (e.equals(cur.value)) return i;
            cur = cur.next;
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public ImmutableLinkedList clear() {
        return new ImmutableLinkedList();
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Object[] toArray() {
        Object[] temp = new Object[size()];
        Node currentNode = head;
        int i = 0;
        while (currentNode != null) {
            temp[i] = currentNode.value;
            i++;
            currentNode = currentNode.next;
        }
        return temp;
    }

    public ImmutableLinkedList addFirst(Object object) {
        return add(0, object);
    }

    public ImmutableLinkedList addLast(Object object) {
        return add(object);
    }

    public Object getFirst() {
        return get(0);
    }

    public Object getLast() {
        return get(size - 1);
    }

    public ImmutableLinkedList removeFirst() {
        return remove(0);
    }

    public ImmutableLinkedList removeLast() {
        return remove(size - 1);
    }

    @Override
    public String toString() {
        String str = Arrays.toString(toArray());
        return str.substring(1, str.length() - 1);
    }
}
