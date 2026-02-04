package project20280.exercises;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Wk3Test {

    // Q2 Test Cases
    @Test
    void testEnqueueAndDequeueOrder() {

        Wk3<Integer> q = new Wk3<>();

        // Enqueue elements
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);

        // Should come out in FIFO order
        assertEquals(1, q.dequeue());
        assertEquals(2, q.dequeue());
        assertEquals(3, q.dequeue());

        // Queue should now be empty
        assertNull(q.dequeue());
    }


    @Test
    void testEnqueueOnly() {

        Wk3<String> q = new Wk3<>();

        // Enqueue elements
        q.enqueue("A");
        q.enqueue("B");
        q.enqueue("C");

        // Remove and check
        assertEquals("A", q.dequeue());
        assertEquals("B", q.dequeue());
        assertEquals("C", q.dequeue());
    }


    @Test
    void testDequeueFromEmptyQueue() {

        Wk3<Integer> q = new Wk3<>();

        // Dequeue on empty queue should return null
        assertNull(q.dequeue());
    }


    @Test
    void testMixedOperations() {

        Wk3<Integer> q = new Wk3<>();

        q.enqueue(10);
        q.enqueue(20);

        assertEquals(10, q.dequeue());

        q.enqueue(30);
        q.enqueue(40);

        assertEquals(20, q.dequeue());
        assertEquals(30, q.dequeue());

        q.enqueue(50);

        assertEquals(40, q.dequeue());
        assertEquals(50, q.dequeue());

        assertNull(q.dequeue());
    }


    @Test
    void testIsEmpty() {

        Wk3<Integer> q = new Wk3<>();

        assertTrue(q.isEmpty());

        q.enqueue(5);
        assertFalse(q.isEmpty());

        q.dequeue();
        assertTrue(q.isEmpty());
    }

    // Q3 Test Cases
    @Test
    void testReverseStackBasic() {

        Wk3<Integer> q = new Wk3<>();

        // Push elements onto stack (in)
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);

        // Reverse the stack
        q.reverseStack();

        // Now dequeue should give reversed order
        assertEquals(3, q.dequeue());
        assertEquals(2, q.dequeue());
        assertEquals(1, q.dequeue());

        assertNull(q.dequeue());
    }


    @Test
    void testReverseStackSingleElement() {

        Wk3<Integer> q = new Wk3<>();

        q.enqueue(10);

        q.reverseStack();

        assertEquals(10, q.dequeue());
        assertNull(q.dequeue());
    }


    @Test
    void testReverseStackEmpty() {

        Wk3<Integer> q = new Wk3<>();

        // Reverse empty stack
        q.reverseStack();

        // Should still be empty
        assertNull(q.dequeue());
    }


    @Test
    void testReverseStackAfterMixedOperations() {

        Wk3<Integer> q = new Wk3<>();

        q.enqueue(5);
        q.enqueue(10);
        q.enqueue(15);

        // Remove one element
        assertEquals(5, q.dequeue());

        q.enqueue(20);
        q.enqueue(25);

        // Reverse remaining stack
        q.reverseStack();

        // Remaining elements were: 10, 15, 20, 25
        // After reverse: 25, 20, 15, 10
        assertEquals(25, q.dequeue());
        assertEquals(20, q.dequeue());
        assertEquals(15, q.dequeue());
        assertEquals(10, q.dequeue());

        assertNull(q.dequeue());
    }

}
