package project20280.list;

import project20280.interfaces.List;
import java.util.Iterator;

public class DoublyLinkedList<E> implements List<E>, Iterable<E> {

    // Node for doubly linked list
    private static class Node<E> {
        private final E data;     // element stored in the node
        private Node<E> next;     // reference to next node
        private Node<E> prev;     // reference to previous node

        // create a node with links to previous and next
        public Node(E e, Node<E> p, Node<E> n) {
            data = e;
            prev = p;
            next = n;
        }

        public E getData() {
            return data;
        }

        public Node<E> getNext() {
            return next;
        }

        public Node<E> getPrev() {
            return prev;
        }

        // update next reference
        public void setNext(Node<E> n) {
            next = n;
        }

        // update previous reference
        public void setPrev(Node<E> p) {
            prev = p;
        }
    }

    private final Node<E> head;   // header sentinel node
    private final Node<E> tail;   // trailer sentinel node
    private int size = 0;         // number of real elements

    // create empty list with head and tail linked together
    public DoublyLinkedList() {
        head = new Node<>(null, null, null);
        tail = new Node<>(null, head, null);
        head.setNext(tail);
    }

    // insert element between two existing nodes
    private void addBetween(E e, Node<E> pred, Node<E> succ) {
        Node<E> newest = new Node<>(e, pred, succ);
        pred.setNext(newest);
        succ.setPrev(newest);
        size++;
    }

    // return number of elements
    @Override
    public int size() {
        return size;
    }

    // check if list is empty
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // get node at index i
    private Node<E> nodeAt(int i) {
        if (i < 0 || i >= size) return null;

        // traverse from head or tail depending on index
        if (i <= size / 2) {
            Node<E> curr = head.getNext();
            for (int k = 0; k < i; k++) curr = curr.getNext();
            return curr;
        } else {
            Node<E> curr = tail.getPrev();
            for (int k = size - 1; k > i; k--) curr = curr.getPrev();
            return curr;
        }
    }

    // return element at index i
    @Override
    public E get(int i) {
        Node<E> n = nodeAt(i);
        return (n == null) ? null : n.getData();
    }

    // insert element at index i
    @Override
    public void add(int i, E e) {
        if (i < 0 || i > size) return;

        Node<E> succ = (i == size) ? tail : nodeAt(i);
        Node<E> pred = succ.getPrev();

        addBetween(e, pred, succ);
    }

    // remove element at index i
    @Override
    public E remove(int i) {
        Node<E> n = nodeAt(i);
        return (n == null) ? null : remove(n);
    }

    // iterator for traversing the list
    private class DoublyLinkedListIterator implements Iterator<E> {
        Node<E> curr = head.getNext();

        @Override
        public boolean hasNext() {
            return curr != tail;
        }

        @Override
        public E next() {
            E res = curr.getData();
            curr = curr.getNext();
            return res;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new DoublyLinkedListIterator();
    }

    // unlink a node and return its data
    private E remove(Node<E> n) {
        Node<E> pred = n.getPrev();
        Node<E> succ = n.getNext();

        pred.setNext(succ);
        succ.setPrev(pred);

        size--;
        return n.getData();
    }

    // return first element
    public E first() {
        if (isEmpty()) return null;
        return head.getNext().getData();
    }

    // return last element
    public E last() {
        if (isEmpty()) return null;
        return tail.getPrev().getData();
    }

    // remove and return first element
    @Override
    public E removeFirst() {
        if (isEmpty()) return null;
        return remove(head.getNext());
    }

    // remove and return last element
    @Override
    public E removeLast() {
        if (isEmpty()) return null;
        return remove(tail.getPrev());
    }

    // add element at the end
    @Override
    public void addLast(E e) {
        addBetween(e, tail.getPrev(), tail);
    }

    // add element at the beginning
    @Override
    public void addFirst(E e) {
        addBetween(e, head, head.getNext());
    }

    // return string representation of list
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = head.getNext();
        while (curr != tail) {
            sb.append(curr.getData());
            curr = curr.getNext();
            if (curr != tail) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
