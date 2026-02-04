package project20280.exercises;

import java.util.ArrayDeque;
import java.util.Deque;

public class Wk3<E> {

    // Q2
    // Stack used for enqueue (adding elements)
    private final Deque<E> in = new ArrayDeque<>();

    // Stack used for dequeue (removing elements)
    private final Deque<E> out = new ArrayDeque<>();


    // Adds an element to the back of the queue
    // New elements are always pushed onto the "in" stack
    public void enqueue(E e) {

        // Push element onto in stack
        in.push(e);
    }


    // Removes and returns the front element of the queue
    // If out stack is empty, elements are moved from in to out
    // This reverses their order and puts the oldest element on top
    public E dequeue() {

        // If out stack is empty, refill it
        if (out.isEmpty()) {

            // Move all elements from in to out
            while (!in.isEmpty()) {
                out.push(in.pop());
            }
        }

        // If both stacks are empty, queue is empty
        if (out.isEmpty()) {
            return null;
        }

        // Remove and return the front element
        return out.pop();
    }


    // Returns true if the queue is empty
    public boolean isEmpty() {
        return in.isEmpty() && out.isEmpty();
    }


    // Q3
    // Reverses the elements in the queue using two additional stacks
    // Only stacks are used (no other data structures)
    // After reversing, dequeue() returns the elements in the opposite order as before
    public void reverseStack() {

        // temp1 collects all elements from the queue
        Deque<E> temp1 = new ArrayDeque<>();

        // temp2 is used to transfer elements from "in" in correct queue order
        Deque<E> temp2 = new ArrayDeque<>();

        // Step 1:
        // Move everything from out into temp1
        // out.pop() removes from the front of the queue, so temp1 receives elements front-to-back
        while (!out.isEmpty()) {
            temp1.push(out.pop());
        }

        // Step 2:
        // Elements in "in" are stored with newest on top, which is the back of the queue
        // To move them in correct queue order (oldest to newest), we first reverse "in" into temp2
        while (!in.isEmpty()) {
            temp2.push(in.pop());
        }

        // Now temp2 has the oldest element on top, so popping temp2 gives correct queue order
        // Push those onto temp1 so temp1 contains the whole queue (front-to-back)
        while (!temp2.isEmpty()) {
            temp1.push(temp2.pop());
        }

        // Step 3:
        // Rebuild the queue in reversed order by pushing everything into "in"
        // This sets up the queue so that the next dequeue will return the old back first
        while (!temp1.isEmpty()) {
            in.push(temp1.pop());
        }

        // out remains empty; dequeue() will refill it from in when needed
    }


    // Main method to test enqueue, dequeue, and reverseStack
    public static void main(String[] args) {

        Wk3<Integer> q = new Wk3<>();

        // Add elements to the queue
        q.enqueue(10);
        q.enqueue(20);
        q.enqueue(30);

        // Remove and print elements
        System.out.println("Dequeue: " + q.dequeue()); // Should be 10
        System.out.println("Dequeue: " + q.dequeue()); // Should be 20

        // Add more elements
        q.enqueue(40);
        q.enqueue(50);

        // Reverse remaining queue
        // Remaining before reverse: 30, 40, 50
        // After reverse: 50, 40, 30
        q.reverseStack();

        // Continue removing
        System.out.println("Dequeue: " + q.dequeue()); // Should be 50
        System.out.println("Dequeue: " + q.dequeue()); // Should be 40
        System.out.println("Dequeue: " + q.dequeue()); // Should be 30

        // Check if empty
        System.out.println("Empty? " + q.isEmpty());   // true

        // Try removing from empty queue
        System.out.println("Dequeue: " + q.dequeue()); // null
    }
}
