package project20280.priorityqueue;

import java.util.Random;

public class PQSort {

    /**
     * Sorts array a in nondecreasing order using a priority queue.
     * Uses HeapPriorityQueue<Integer, Integer> where key=value=a[i].
     */
    public static void pqSort(int[] a) {
        HeapPriorityQueue<Integer, Integer> pq = new HeapPriorityQueue<>();
        for (int x : a) {
            pq.insert(x, x);
        }
        for (int i = 0; i < a.length; i++) {
            a[i] = pq.removeMin().getKey();
        }
    }

    // ---------------- timing harness ----------------

    private static int[] randomArray(int n, long seed) {
        Random r = new Random(seed);
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = r.nextInt();
        return a;
    }

    private static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i - 1] > a[i]) return false;
        }
        return true;
    }

    private static long timeOnce(int n, int warmupRuns, int measuredRuns) {
        // Warmup (helps JIT)
        for (int w = 0; w < warmupRuns; w++) {
            int[] a = randomArray(n, 12345L + w);
            pqSort(a);
        }

        long best = Long.MAX_VALUE;
        for (int r = 0; r < measuredRuns; r++) {
            int[] a = randomArray(n, 9999L + r);
            long t0 = System.nanoTime();
            pqSort(a);
            long t1 = System.nanoTime();

            if (!isSorted(a)) throw new RuntimeException("Array not sorted!");
            best = Math.min(best, (t1 - t0));
        }
        return best; // best-of-runs reduces noise
    }

    public static void main(String[] args) {
        int[] ns = new int[] {1000, 2000, 5000, 10000, 20000, 50000,
                100000, 200000, 500000, 1000000};

        int warmup = 2;
        int runs = 5;

        System.out.println("n\tbest_time_ms\t(time/(n log2 n))");
        for (int n : ns) {
            long nanos = timeOnce(n, warmup, runs);
            double ms = nanos / 1e6;

            double nlogn = n * (Math.log(n) / Math.log(2));
            double ratio = (nanos / 1e9) / nlogn; // seconds / (n log n)

            System.out.printf("%d\t%.3f\t\t%.6e%n", n, ms, ratio);
        }
    }
}