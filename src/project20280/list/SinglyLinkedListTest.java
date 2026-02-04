package project20280.list;

import org.junit.jupiter.api.Test;
import project20280.interfaces.List;

import static org.junit.jupiter.api.Assertions.*;


class SinglyLinkedListTest {

    @Test
    void testIsEmpty() {
        List<Integer> ll = new SinglyLinkedList<Integer>();
        assertTrue(ll.isEmpty());
        assertEquals("[]", ll.toString());

        ll.addLast(1);
        assertFalse(ll.isEmpty());

        ll.removeLast();
        assertTrue(ll.isEmpty());
    }

    @Test
    void testGet() {
        List<Integer> ll = new SinglyLinkedList<Integer>();
        ll.addLast(1);
        ll.addLast(2);
        ll.addLast(3);

        Integer r = ll.get(2);
        assertEquals(3, r, "did not get right element" + r);
    }

    @Test
    void testAdd() {
        List<Integer> ll = new SinglyLinkedList<Integer>();
        ll.addLast(1);
        ll.addLast(2);
        ll.addLast(3);

        ll.add(1, 100);

        assertEquals("[1, 100, 2, 3]", ll.toString(), "item not added correctly");
    }

    @Test
    void testRemove() {
        List<Integer> ll = new SinglyLinkedList<Integer>();
        ll.addLast(1);
        ll.addLast(2);
        ll.addLast(3);

        assertEquals(3, ll.remove(2), "the removed value should be 3");
        assertEquals(2, ll.size(), "the size should be 2");
    }

    @Test
    void testSize() {
        List<Integer> ll = new SinglyLinkedList<Integer>();
        assertEquals(0, ll.size());

        ll.addFirst(1);
        assertEquals(1, ll.size());

        ll.removeFirst();
        assertEquals(0, ll.size());
    }

    @Test
    void testRemoveFirst() {
        List<Integer> ll = new SinglyLinkedList<Integer>();
		assertNull(ll.removeFirst());

        ll.addLast(1);
        ll.addLast(2);
        ll.addLast(3);
        Integer r = ll.removeFirst();
        assertEquals(1, r);
        assertEquals(2, ll.size());
    }

    @Test
    void testRemoveLast() {
        List<Integer> ll = new SinglyLinkedList<Integer>();
        ll.addLast(1);
        ll.addLast(2);
        ll.addLast(3);
        assertEquals(3, ll.removeLast());
        assertEquals(2, ll.size());
    }

    @Test
    void testAddFirst() {
        List<Integer> ll = new SinglyLinkedList<Integer>();
        ll.addLast(-1);
        ll.addFirst(1);

        assertEquals(2, ll.size());
        assertEquals("[1, -1]", ll.toString());
    }

    @Test
    void testAddLast() {
        List<Integer> ll = new SinglyLinkedList<Integer>();
        ll.addFirst(1);
        ll.addLast(-1);

        assertEquals(2, ll.size());
        assertEquals("[1, -1]", ll.toString());
    }

    @Test
    void testToString() {
        List<Integer> ll = new SinglyLinkedList<Integer>();
        ll.addLast(1);
        ll.addLast(2);
        ll.addLast(3);
        assertEquals("[1, 2, 3]", ll.toString());
    }

    // Q10 & Q11 Junit Tests
    @Test
    void testReverseEmpty() {
        SinglyLinkedList<Integer> ll = new SinglyLinkedList<>();
        ll.reverse();
        assertEquals("[]", ll.toString());
        assertEquals(0, ll.size());
    }

    @Test
    void testReverseSingle() {
        SinglyLinkedList<Integer> ll = new SinglyLinkedList<>();
        ll.addLast(42);

        ll.reverse();

        assertEquals("[42]", ll.toString());
        assertEquals(1, ll.size());
    }

    @Test
    void testReverseMultiple() {
        SinglyLinkedList<Integer> ll = new SinglyLinkedList<>();
        ll.addLast(1);
        ll.addLast(2);
        ll.addLast(3);
        ll.addLast(4);

        ll.reverse();

        assertEquals("[4, 3, 2, 1]", ll.toString());
        assertEquals(4, ll.size());
    }

    @Test
    void testReverseTwiceReturnsOriginal() {
        SinglyLinkedList<Integer> ll = new SinglyLinkedList<>();
        ll.addLast(1);
        ll.addLast(2);
        ll.addLast(3);

        ll.reverse();
        ll.reverse();

        assertEquals("[1, 2, 3]", ll.toString());
        assertEquals(3, ll.size());
    }

    @Test
    void testCopyEmpty() {
        SinglyLinkedList<Integer> ll = new SinglyLinkedList<>();
        SinglyLinkedList<Integer> twin = ll.copy();

        assertNotSame(ll, twin);
        assertEquals("[]", twin.toString());
        assertEquals(0, twin.size());
    }

    @Test
    void testCopyContentsSame() {
        SinglyLinkedList<Integer> ll = new SinglyLinkedList<>();
        ll.addLast(1);
        ll.addLast(2);
        ll.addLast(3);

        SinglyLinkedList<Integer> twin = ll.copy();

        assertNotSame(ll, twin);
        assertEquals(ll.toString(), twin.toString());
        assertEquals(ll.size(), twin.size());
    }

    @Test
    void testCopyIsIndependent() {
        SinglyLinkedList<Integer> ll = new SinglyLinkedList<>();
        ll.addLast(1);
        ll.addLast(2);
        ll.addLast(3);

        SinglyLinkedList<Integer> twin = ll.copy();

        // change original, clone should not change
        ll.removeFirst();
        assertEquals("[2, 3]", ll.toString());
        assertEquals("[1, 2, 3]", twin.toString());

        // change clone, original should not change
        twin.addLast(99);
        assertEquals("[1, 2, 3, 99]", twin.toString());
        assertEquals("[2, 3]", ll.toString());
    }
}
