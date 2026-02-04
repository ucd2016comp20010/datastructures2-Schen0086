package project20280.list;

import project20280.interfaces.List;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CircularlyLinkedList<E> implements List<E>, Iterable<E> {

    private class Node<T> {
        private final T data;      // element stored in this node
        private Node<T> next;      // next node in the circle

        public Node(T e, Node<T> n) {
            data = e;
            next = n;
        }

        public T getData() {
            return data;
        }

        public void setNext(Node<T> n) {
            next = n;
        }

        public Node<T> getNext() {
            return next;
        }
    }

    // tail points to the last node; head is tail.next
    private Node<E> tail = null;
    private int size = 0;

    public CircularlyLinkedList() { }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    // helper: return head node (or null if empty)
    private Node<E> head() {
        return (tail == null) ? null : tail.getNext();
    }

    @Override
    public E get(int i) {
        if (i < 0 || i >= size) return null;

        Node<E> curr = head();
        // walk forward i steps from head
        for (int k = 0; k < i; k++) {
            curr = curr.getNext();
        }
        return curr.getData();
    }

    /**
     * Inserts the given element at the specified index of the list.
     */
    @Override
    public void add(int i, E e) {
        if (i < 0 || i > size) return;

        // same behaviour as singly linked list: handle ends directly
        if (i == 0) {
            addFirst(e);
            return;
        }
        if (i == size) {
            addLast(e);
            return;
        }

        // find the node before index i (i-1)
        Node<E> prev = head();
        for (int k = 0; k < i - 1; k++) {
            prev = prev.getNext();
        }

        // insert new node between prev and prev.next
        Node<E> newest = new Node<>(e, prev.getNext());
        prev.setNext(newest);
        size++;
    }

    @Override
    public E remove(int i) {
        if (i < 0 || i >= size) return null;

        if (i == 0) return removeFirst();

        // find the node before the one we remove
        Node<E> prev = head();
        for (int k = 0; k < i - 1; k++) {
            prev = prev.getNext();
        }

        Node<E> target = prev.getNext();
        E val = target.getData();

        // bypass target
        prev.setNext(target.getNext());

        // if we removed the tail, update tail
        if (target == tail) {
            tail = prev;
        }

        size--;
        return val;
    }

    // rotate so that old head becomes new tail (tail moves forward by one)
    public void rotate() {
        if (tail != null) {
            tail = tail.getNext();
        }
    }

    private class CircularlyLinkedListIterator<T> implements Iterator<T> {
        private Node<T> curr = (Node<T>) ((tail == null) ? null : tail.getNext()); // start at head
        private int remaining = size; // stop after size elements

        @Override
        public boolean hasNext() {
            return remaining > 0;
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            T res = curr.data;
            curr = curr.next;
            remaining--;
            return res;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new CircularlyLinkedListIterator<E>();
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) return null;

        Node<E> h = head();
        E val = h.getData();

        // one element: list becomes empty
        if (size == 1) {
            tail = null;
            size = 0;
            return val;
        }

        // skip over old head
        tail.setNext(h.getNext());
        size--;
        return val;
    }

    @Override
    public E removeLast() {
        if (isEmpty()) return null;
        if (size == 1) return removeFirst();

        // find the node before tail
        Node<E> prev = head();
        while (prev.getNext() != tail) {
            prev = prev.getNext();
        }

        E val = tail.getData();
        prev.setNext(tail.getNext()); // new tail must still point to head
        tail = prev;
        size--;
        return val;
    }

    @Override
    public void addFirst(E e) {
        // empty list: node points to itself and becomes tail
        if (tail == null) {
            tail = new Node<>(e, null);
            tail.setNext(tail);
            size = 1;
            return;
        }

        // insert new node after tail (becomes new head)
        Node<E> newest = new Node<>(e, tail.getNext());
        tail.setNext(newest);
        size++;
    }

    @Override
    public void addLast(E e) {
        // add at front, then move tail forward to the new node
        addFirst(e);
        tail = tail.getNext();
    }

    public String toString() {
        if (isEmpty()) return "[]";

        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = tail;
        do {
            curr = curr.next;          // move to head first, then onwards
            sb.append(curr.data);
            if (curr != tail) {
                sb.append(", ");
            }
        } while (curr != tail);
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        CircularlyLinkedList<Integer> ll = new CircularlyLinkedList<Integer>();
        for (int i = 10; i < 20; ++i) {
            ll.addLast(i);
        }

        System.out.println(ll);

        ll.removeFirst();
        System.out.println(ll);

        ll.removeLast();
        System.out.println(ll);

        ll.rotate();
        System.out.println(ll);

        ll.removeFirst();
        ll.rotate();
        System.out.println(ll);

        ll.removeLast();
        ll.rotate();
        System.out.println(ll);

        for (Integer e : ll) {
            System.out.println("value: " + e);
        }
    }
}
