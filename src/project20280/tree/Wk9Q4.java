package project20280.tree;

import project20280.interfaces.Entry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Wk9Q4 {

    // Wk9 Q4: key used so duplicate values can still be sorted with a map
    static class SortKey implements Comparable<SortKey> {
        int value;
        int serial;

        SortKey(int value, int serial) {
            this.value = value;
            this.serial = serial;
        }

        @Override
        public int compareTo(SortKey other) {
            int c = Integer.compare(this.value, other.value);
            if (c != 0) return c;
            return Integer.compare(this.serial, other.serial);
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof SortKey sk)) return false;
            return value == sk.value && serial == sk.serial;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, serial);
        }
    }

    // Wk9 Q4: sort using your unbalanced TreeMap
    public static List<Integer> bstSort(int[] data) {
        TreeMap<SortKey, Integer> map = new TreeMap<>();

        for (int i = 0; i < data.length; i++) {
            map.put(new SortKey(data[i], i), data[i]);
        }

        List<Integer> out = new ArrayList<>(data.length);
        for (Entry<SortKey, Integer> e : map.entrySet()) {
            out.add(e.getValue());
        }
        return out;
    }

    // Wk9 Q4: sort using your AVL tree
    public static List<Integer> avlSort(int[] data) {
        AVLTreeMap<SortKey, Integer> map = new AVLTreeMap<>();

        for (int i = 0; i < data.length; i++) {
            map.put(new SortKey(data[i], i), data[i]);
        }

        List<Integer> out = new ArrayList<>(data.length);
        for (Entry<SortKey, Integer> e : map.entrySet()) {
            out.add(e.getValue());
        }
        return out;
    }

    // Wk9 Q4: sort using java.util.TreeMap
    public static List<Integer> javaTreeMapSort(int[] data) {
        java.util.TreeMap<SortKey, Integer> map = new java.util.TreeMap<>();

        for (int i = 0; i < data.length; i++) {
            map.put(new SortKey(data[i], i), data[i]);
        }

        return new ArrayList<>(map.values());
    }

    private static int[] randomArray(int n, Random rnd) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = rnd.nextInt(1_000_000);
        }
        return a;
    }

    private static boolean isSorted(List<Integer> a) {
        for (int i = 1; i < a.size(); i++) {
            if (a.get(i - 1) > a.get(i)) return false;
        }
        return true;
    }

    private static long timeBST(int[] data) {
        long start = System.nanoTime();
        List<Integer> out = bstSort(data);
        long end = System.nanoTime();
        if (!isSorted(out)) throw new RuntimeException("BST sort failed");
        return end - start;
    }

    private static long timeAVL(int[] data) {
        long start = System.nanoTime();
        List<Integer> out = avlSort(data);
        long end = System.nanoTime();
        if (!isSorted(out)) throw new RuntimeException("AVL sort failed");
        return end - start;
    }

    private static long timeJavaTreeMap(int[] data) {
        long start = System.nanoTime();
        List<Integer> out = javaTreeMapSort(data);
        long end = System.nanoTime();
        if (!isSorted(out)) throw new RuntimeException("java.util.TreeMap sort failed");
        return end - start;
    }

    public static void main(String[] args) {
        Random rnd = new Random(42);

        int[] sizes = {1000, 2000, 5000, 10000, 20000};
        int repeats = 5;

        System.out.printf("%10s %18s %18s %18s%n",
                "n", "BST(ms)", "AVL(ms)", "javaTreeMap(ms)");

        for (int n : sizes) {
            long bstTotal = 0;
            long avlTotal = 0;
            long javaTotal = 0;

            for (int r = 0; r < repeats; r++) {
                int[] data = randomArray(n, rnd);

                bstTotal += timeBST(Arrays.copyOf(data, data.length));
                avlTotal += timeAVL(Arrays.copyOf(data, data.length));
                javaTotal += timeJavaTreeMap(Arrays.copyOf(data, data.length));
            }

            System.out.printf("%10d %18.3f %18.3f %18.3f%n",
                    n,
                    bstTotal / repeats / 1_000_000.0,
                    avlTotal / repeats / 1_000_000.0,
                    javaTotal / repeats / 1_000_000.0);
        }
    }
}

// Wk9 Q4: Sorting with a BST works by inserting all elements into the tree and then
// reading them using inorder traversal, which returns the elements in sorted order.
//
// Wk9 Q4: From the results, java.util.TreeMap is the fastest, AVLTreeMap is slightly
// slower, and the ordinary BST is the least predictable. The balanced trees show more
// consistent performance as n increases, matching the expected O(n log n) complexity.
//
// Wk9 Q4: The ordinary BST performs reasonably on random data, but its performance is
// less stable and can degrade to O(n^2) in the worst case. Overall, the results agree
// with the expected theoretical behaviour.