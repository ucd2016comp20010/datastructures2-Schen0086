package project20280.stacksqueues;

import project20280.interfaces.Queue;
import project20280.list.DoublyLinkedList;

public class LinkedQueue<E> implements Queue<E> {

    private DoublyLinkedList<E> ll;

    public LinkedQueue() {
        ll = new DoublyLinkedList<>();
    }

    @Override
    public int size() {
        return ll.size();
    }

    @Override
    public boolean isEmpty() {
        return ll.isEmpty();
    }

    @Override
    public void enqueue(E e) {
        ll.addLast(e);
    }

    @Override
    public E first() {
        return ll.first();
    }

    @Override
    public E dequeue() {
        return ll.removeFirst();
    }

    public String toString() {
        return ll.toString();
    }

    public static void main(String[] args) {
        Queue<String> q = new LinkedQueue<>();
        q.enqueue("A");
        q.enqueue("B");
        q.enqueue("C");
        System.out.println(q.first());   // A
        System.out.println(q.dequeue()); // A
        System.out.println(q.dequeue()); // B
        System.out.println(q.dequeue()); // C
        System.out.println(q.dequeue()); // null (depending on DLL behavior)
        System.out.println(q);
    }
}
