package project20280.stacksqueues;

import project20280.interfaces.Stack;
import project20280.list.DoublyLinkedList;

public class LinkedStack<E> implements Stack<E> {

    private DoublyLinkedList<E> ll;

    public LinkedStack() {
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
    public void push(E e) {
        ll.addFirst(e);
    }

    @Override
    public E top() {
        return ll.first();
    }

    @Override
    public E pop() {
        return ll.removeFirst();
    }

    public String toString() {
        return ll.toString();
    }

    public static void main(String[] args) {
        Stack<Integer> s = new LinkedStack<>();
        System.out.println(s.pop()); // null (if your DLL returns null on removeFirst when empty)
        s.push(10);
        s.push(20);
        System.out.println(s.top()); // 20
        System.out.println(s.pop()); // 20
        System.out.println(s.pop()); // 10
        System.out.println(s.pop()); // null
        System.out.println(s);
    }
}
