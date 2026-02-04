package project20280.list;

import project20280.interfaces.List;

public class LinkedListUtils {

    // Merge two sorted lists into one sorted list
    public static List<Integer> sortedMerge(List<Integer> l1, List<Integer> l2) {

        // result list that will contain all elements in sorted order
        List<Integer> result = new SinglyLinkedList<>();

        int i = 0, j = 0; // indices for l1 and l2

        // compare elements from both lists while neither is exhausted
        while (i < l1.size() && j < l2.size()) {
            if (l1.get(i) <= l2.get(j)) {
                result.addLast(l1.get(i)); // add smaller element from l1
                i++;
            } else {
                result.addLast(l2.get(j)); // add smaller element from l2
                j++;
            }
        }

        // add remaining elements from l1, if any
        while (i < l1.size()) {
            result.addLast(l1.get(i));
            i++;
        }

        // add remaining elements from l2, if any
        while (j < l2.size()) {
            result.addLast(l2.get(j));
            j++;
        }

        // return the merged sorted list
        return result;
    }

    // simple test to demonstrate sortedMerge
    public static void main(String[] args) {

        // create two sorted linked lists
        List<Integer> l1 = new SinglyLinkedList<>();
        List<Integer> l2 = new SinglyLinkedList<>();

        // populate first list
        l1.addLast(2);
        l1.addLast(6);
        l1.addLast(20);
        l1.addLast(24);

        // populate second list
        l2.addLast(1);
        l2.addLast(3);
        l2.addLast(5);
        l2.addLast(8);
        l2.addLast(12);
        l2.addLast(19);
        l2.addLast(25);

        // merge the two lists
        List<Integer> result = LinkedListUtils.sortedMerge(l1, l2);

        // print the merged list
        System.out.println(result);
    }
}
