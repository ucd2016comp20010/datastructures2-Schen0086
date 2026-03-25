package project20280.hashtable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnsortedTableMapTest {

    @Test
    void testNewMapIsEmpty() {
        // Test that a newly created map has size 0
        UnsortedTableMap<String, Integer> map = new UnsortedTableMap<>();
        assertEquals(0, map.size());

        // Test that get on an empty map returns null
        assertNull(map.get("A"));

        // Test that remove on an empty map returns null
        assertNull(map.remove("A"));
    }

    @Test
    void testPutNewKeyReturnsNull() {
        // Test that putting a new key returns null
        UnsortedTableMap<String, Integer> map = new UnsortedTableMap<>();
        assertNull(map.put("A", 1));

        // Test that the value was stored correctly
        assertEquals(1, map.get("A"));

        // Test that the map size increased after inserting one entry
        assertEquals(1, map.size());
    }

    @Test
    void testPutExistingKeyReturnsOldValue() {
        // Test that updating an existing key returns the old value
        UnsortedTableMap<String, Integer> map = new UnsortedTableMap<>();
        map.put("A", 1);
        assertEquals(1, map.put("A", 10));

        // Test that the value was updated to the new value
        assertEquals(10, map.get("A"));

        // Test that updating an existing key does not increase size
        assertEquals(1, map.size());
    }

    @Test
    void testGetReturnsCorrectValue() {
        // Test that get returns the correct value for stored keys
        UnsortedTableMap<String, Integer> map = new UnsortedTableMap<>();
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", 3);

        assertEquals(1, map.get("A"));
        assertEquals(2, map.get("B"));
        assertEquals(3, map.get("C"));
    }

    @Test
    void testGetMissingKeyReturnsNull() {
        // Test that get returns null for a key not in the map
        UnsortedTableMap<String, Integer> map = new UnsortedTableMap<>();
        map.put("A", 1);

        assertNull(map.get("X"));
    }

    @Test
    void testRemoveExistingKey() {
        // Test that remove returns the value of the removed key
        UnsortedTableMap<String, Integer> map = new UnsortedTableMap<>();
        map.put("A", 1);
        map.put("B", 2);

        assertEquals(1, map.remove("A"));

        // Test that the removed key is no longer in the map
        assertNull(map.get("A"));

        // Test that size decreases after removal
        assertEquals(1, map.size());
    }

    @Test
    void testRemoveMissingKeyReturnsNull() {
        // Test that removing a key not in the map returns null
        UnsortedTableMap<String, Integer> map = new UnsortedTableMap<>();
        map.put("A", 1);

        assertNull(map.remove("X"));

        // Test that size stays the same when remove fails
        assertEquals(1, map.size());
    }

    @Test
    void testRemoveLastElementAndMiddleElement() {
        // Test removal works correctly even though remove replaces with last element
        UnsortedTableMap<String, Integer> map = new UnsortedTableMap<>();
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", 3);

        // Wk8: remove a middle element
        assertEquals(2, map.remove("B"));
        assertNull(map.get("B"));
        assertEquals(2, map.size());

        // Test that the other entries are still accessible after internal replacement
        assertEquals(1, map.get("A"));
        assertEquals(3, map.get("C"));
    }

    @Test
    void testMultiplePutsAndRemoves() {
        // Test a sequence of inserts, updates, and removals
        UnsortedTableMap<String, Integer> map = new UnsortedTableMap<>();

        assertNull(map.put("A", 1));
        assertNull(map.put("B", 2));
        assertNull(map.put("C", 3));
        assertEquals(3, map.size());

        // Test updating an existing entry
        assertEquals(2, map.put("B", 20));
        assertEquals(20, map.get("B"));

        // Test removing entries one by one
        assertEquals(1, map.remove("A"));
        assertEquals(20, map.remove("B"));
        assertEquals(3, map.remove("C"));

        // Test that map is empty after all removals
        assertEquals(0, map.size());
        assertNull(map.get("A"));
        assertNull(map.get("B"));
        assertNull(map.get("C"));
    }

    @Test
    void testEntrySetContainsInsertedEntries() {
        // Test that entrySet iterates through all inserted entries
        UnsortedTableMap<String, Integer> map = new UnsortedTableMap<>();
        map.put("A", 1);
        map.put("B", 2);

        boolean foundA = false;
        boolean foundB = false;

        for (var entry : map.entrySet()) {
            // Wk8: check that key A with value 1 appears in the entry set
            if (entry.getKey().equals("A") && entry.getValue().equals(1)) {
                foundA = true;
            }

            // Wk8: check that key B with value 2 appears in the entry set
            if (entry.getKey().equals("B") && entry.getValue().equals(2)) {
                foundB = true;
            }
        }

        assertTrue(foundA);
        assertTrue(foundB);
    }
}