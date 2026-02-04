package project20280.list;

import project20280.interfaces.List;
import java.util.Iterator;

public class SinglyLinkedList<E> implements List<E>, Iterable<E> {

    private static class Node<E> {

        private final E element;   // data stored in this node
        private Node<E> next;      // reference to the next node

        // create a node with an element and next reference
        public Node(E e, Node<E> n) {
            this.element = e;
            this.next = n;
        }

        // return the stored element
        public E getElement() {
            return element;
        }

        // return the next node
        public Node<E> getNext() {
            return next;
        }

        // update the next reference
        public void setNext(Node<E> n) {
            this.next = n;
        }
    }

    private Node<E> head = null;   // first node in the list
    private int size = 0;          // number of elements

    public SinglyLinkedList() { }

    // return number of elements in the list
    @Override
    public int size() {
        return size;
    }

    // check if the list is empty
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // return element at a given position
    @Override
    public E get(int position) {
        if (position < 0 || position >= size) return null;

        Node<E> curr = head;
        // move forward position times
        for (int i = 0; i < position; i++) {
            curr = curr.getNext();
        }
        return curr.getElement();
    }

    // insert element at a specific position
    @Override
    public void add(int position, E e) {
        if (position < 0 || position > size) return;

        // inserting at the front
        if (position == 0) {
            addFirst(e);
            return;
        }

        Node<E> prev = head;
        // find node before insertion point
        for (int i = 0; i < position - 1; i++) {
            prev = prev.getNext();
        }

        // link new node into the list
        Node<E> newest = new Node<>(e, prev.getNext());
        prev.setNext(newest);
        size++;
    }

    // insert element at the beginning
    @Override
    public void addFirst(E e) {
        head = new Node<>(e, head);  // new node points to old head
        size++;
    }

    // insert element at the end
    @Override
    public void addLast(E e) {
        Node<E> newest = new Node<>(e, null);

        // empty list case
        if (head == null) {
            head = newest;
            size++;
            return;
        }

        // move to the last node
        Node<E> curr = head;
        while (curr.getNext() != null) {
            curr = curr.getNext();
        }

        curr.setNext(newest);
        size++;
    }

    // remove element at a specific position
    @Override
    public E remove(int position) {
        if (position < 0 || position >= size) return null;

        // removing first element
        if (position == 0) return removeFirst();

        // removing last element
        if (position == size - 1) return removeLast();

        Node<E> prev = head;
        // find node before the one to remove
        for (int i = 0; i < position - 1; i++) {
            prev = prev.getNext();
        }

        Node<E> target = prev.getNext();
        prev.setNext(target.getNext()); // bypass removed node
        size--;
        return target.getElement();
    }

    // remove and return first element
    @Override
    public E removeFirst() {
        if (head == null) return null;

        E val = head.getElement();
        head = head.getNext();  // move head forward
        size--;
        return val;
    }

    // remove and return last element
    @Override
    public E removeLast() {
        if (head == null) return null;

        // single element case
        if (head.getNext() == null) return removeFirst();

        Node<E> prev = head;
        // move to second-last node
        while (prev.getNext().getNext() != null) {
            prev = prev.getNext();
        }

        Node<E> last = prev.getNext();
        prev.setNext(null);  // remove last node
        size--;
        return last.getElement();
    }

    // return iterator for the list
    @Override
    public Iterator<E> iterator() {
        return new SinglyLinkedListIterator<>();
    }

    // iterator implementation
    private class SinglyLinkedListIterator<T> implements Iterator<T> {
        Node<T> curr = (Node<T>) head;

        // check if more elements exist
        @Override
        public boolean hasNext() {
            return curr != null;
        }

        // return current element and move forward
        @Override
        public T next() {
            T res = curr.getElement();
            curr = curr.next;
            return res;
        }
    }

    // convert list to string format
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = head;
        while (curr != null) {
            sb.append(curr.getElement());
            if (curr.getNext() != null)
                sb.append(", ");
            curr = curr.getNext();
        }
        sb.append("]");
        return sb.toString();
    }

    // Q10 - Reverse: change links, not data, using three pointers
    public void reverse() {
        Node<E> prev = null;        // will become the new head
        Node<E> curr = head;        // start from current head
        Node<E> next;        // temporary pointer

        while (curr != null) {
            next = curr.getNext();  // store next node
            curr.setNext(prev);     // reverse the link
            prev = curr;            // move prev forward
            curr = next;            // move curr forward
        }

        head = prev;                // update head to new front
    }

    // Q11 - Clone: create a new list and copy elements one by one
    public SinglyLinkedList<E> copy() {
        SinglyLinkedList<E> twin = new SinglyLinkedList<E>();
        Node<E> tmp = head;
        while (tmp != null) {
            twin.addLast(tmp.getElement());
            tmp = tmp.next;
        }
        return twin;
    }

}
