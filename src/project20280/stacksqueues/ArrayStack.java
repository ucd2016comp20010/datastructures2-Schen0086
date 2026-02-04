package project20280.stacksqueues;

import project20280.interfaces.Stack;

public class ArrayStack<E> implements Stack<E> {

    public static final int CAPACITY = 100;

    private E[] data;
    private int t = -1;

    public ArrayStack() {
        this(CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public ArrayStack(int capacity) {
        data = (E[]) new Object[capacity];
    }

    @Override
    public int size() {
        return t + 1;
    }

    @Override
    public boolean isEmpty() {
        return t == -1;
    }

    @Override
    public void push(E e) {
        if (size() == data.length) throw new IllegalStateException("Stack is full");
        data[++t] = e;
    }

    @Override
    public E top() {
        return isEmpty() ? null : data[t];
    }

    @Override
    public E pop() {
        if (isEmpty()) return null;
        E ans = data[t];
        data[t] = null;
        t--;
        return ans;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        if (!isEmpty()) {
            for (int i = t; i >= 0; --i) {
                sb.append(data[i]);
                if (i != 0) sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
