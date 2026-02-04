package project20280.stacksqueues;

import project20280.interfaces.Deque;

public class LinkedDeque<E> implements Deque<E> {

    public static final int CAPACITY = 1000;

    private E[] data;
    private int front = 0;
    private int size = 0;

    @SuppressWarnings("unchecked")
    public LinkedDeque(int capacity) {
        data = (E[]) new Object[capacity];
    }

    public LinkedDeque() {
        this(CAPACITY);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E first() {
        return isEmpty() ? null : data[front];
    }

    @Override
    public E last() {
        if (isEmpty()) return null;
        int idx = (front + size - 1) % data.length;
        return data[idx];
    }

    @Override
    public void addFirst(E e) {
        if (size == data.length) throw new IllegalStateException("Deque is full");
        front = (front - 1 + data.length) % data.length;
        data[front] = e;
        size++;
    }

    @Override
    public void addLast(E e) {
        if (size == data.length) throw new IllegalStateException("Deque is full");
        int avail = (front + size) % data.length;
        data[avail] = e;
        size++;
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) return null;
        E ans = data[front];
        data[front] = null;
        front = (front + 1) % data.length;
        size--;
        return ans;
    }

    @Override
    public E removeLast() {
        if (isEmpty()) return null;
        int idx = (front + size - 1) % data.length;
        E ans = data[idx];
        data[idx] = null;
        size--;
        return ans;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(data[(front + i) % data.length]);
            if (i != size - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        Deque<Integer> d = new LinkedDeque<>(6);

        d.addLast(1);
        d.addLast(2);
        d.addLast(3);
        System.out.println(d);         // [1, 2, 3]
        d.addFirst(0);
        System.out.println(d);         // [0, 1, 2, 3]
        System.out.println(d.first()); // 0
        System.out.println(d.last());  // 3

        System.out.println(d.removeFirst()); // 0
        System.out.println(d.removeLast());  // 3
        System.out.println(d);               // [1, 2]

        d.addFirst(9);
        d.addLast(8);
        System.out.println(d);               // wrap-around check
    }
}
