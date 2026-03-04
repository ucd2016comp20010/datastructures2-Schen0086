package project20280.priorityqueue;

import java.util.Random;

public class HeapSortInPlace {

    // ---------- In-place heapsort (ascending) ----------

    public static void heapSort(int[] a) {
        int n = a.length;
        buildMaxHeap(a, n);
        for (int end = n - 1; end > 0; end--) {
            swap(a, 0, end);          // move current max to final position
            siftDown(a, 0, end);      // restore heap property on [0, end)
        }
    }

    // Build max heap in O(n)
    private static void buildMaxHeap(int[] a, int n) {
        for (int i = parent(n - 1); i >= 0; i--) {
            siftDown(a, i, n);
        }
    }

    // Sift down node i in heap range [0, n)
    private static void siftDown(int[] a, int i, int n) {
        while (left(i) < n) {
            int l = left(i);
            int r = l + 1;
            int largest = l;

            if (r < n && a[r] > a[l]) largest = r;
            if (a[i] >= a[largest]) break;

            swap(a, i, largest);
            i = largest;
        }
    }

    private static int parent(int i) { return (i - 1) / 2; }
    private static int left(int i) { return 2 * i + 1; }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    // ---------- Helpers for timing ----------

    private static int[] randomArray(int n, long seed) {
        Random r = new Random(seed);
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = r.nextInt();
        return a;
    }

    private static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) if (a[i - 1] > a[i]) return false;
        return true;
    }

    private static long bestTimeNanos(Runnable task, int warmupRuns, int measuredRuns) {
        for (int i = 0; i < warmupRuns; i++) task.run(); // JIT warmup
        long best = Long.MAX_VALUE;
        for (int i = 0; i < measuredRuns; i++) {
            long t0 = System.nanoTime();
            task.run();
            long t1 = System.nanoTime();
            best = Math.min(best, t1 - t0);
        }
        return best;
    }

    // ---------- Compare against PQSort from earlier ----------

    public static void pqSort(int[] a) {
        HeapPriorityQueue<Integer, Integer> pq = new HeapPriorityQueue<>();
        for (int x : a) pq.insert(x, x);
        for (int i = 0; i < a.length; i++) a[i] = pq.removeMin().getKey();
    }

    public static void main(String[] args) {
        int[] ns = new int[] {1000, 2000, 5000, 10000, 20000, 50000,
                100000, 200000, 500000, 1000000};

        int warmup = 2;
        int runs = 5;

        System.out.println("n\tPQSort_ms\tHeapSort_ms");
        for (int n : ns) {
            // Make identical starting data for fairness
            int[] base = randomArray(n, 123456L);

            long pqNanos = bestTimeNanos(() -> {
                int[] a = base.clone();
                pqSort(a);
                if (!isSorted(a)) throw new RuntimeException("PQSort failed");
            }, warmup, runs);

            long hsNanos = bestTimeNanos(() -> {
                int[] a = base.clone();
                heapSort(a);
                if (!isSorted(a)) throw new RuntimeException("HeapSort failed");
            }, warmup, runs);

            System.out.printf("%d\t%.3f\t\t%.3f%n", n, pqNanos / 1e6, hsNanos / 1e6);
        }
    }
}

// Expected time complexity comparison:

// PQSort (your HeapPriorityQueue)
// n inserts: nlogn
// n removeMin: nlogn
// Total: Θ(nlogn)
//
// In-place HeapSort
// build heap bottom-up: Θ(n)
// n times: swap + siftDown O(logn): 0(nlogn)
// Total: Θ(nlogn)
//
// So both are Θ(nlogn).


// What to expect in timings
//
// Even though both are nlogn, in-place heapsort is usually significantly faster than PQSort in this setup because PQSort:
// allocates one Entry object per element (lots of object creation)
// causes more GC pressure
// uses ArrayList<Entry<...>> and comparator calls on objects, not primitives
// does 2n heap operations (n upheaps + n downheaps)
// while in-place heapsort does:
// one heapify pass O(n)
// then only n downheaps

// In-place heapsort also uses:
// no extra memory beyond the array (O(1) auxiliary space)
// better locality (all operations are on int[])

// So you should typically find:
// Both curves grow like nlogn
// In-place heapsort has a smaller constant factor and scales better at large n.