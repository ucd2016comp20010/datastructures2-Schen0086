package project20280.exercises;

public class Tribonacci {

    public static long tribonacci(int n) {
        if (n == 0) return 0;
        if (n == 1) return 0;
        if (n == 2) return 1;

        long t0 = 0;
        long t1 = 0;
        long t2 = 1;
        long current = 0;

        for (int i = 3; i <= n; i++) {
            current = t0 + t1 + t2;
            t0 = t1;
            t1 = t2;
            t2 = current;
        }

        return current;
    }

    public static void main(String[] args) {
        int n = 9;
        System.out.println("Tribonacci(" + n + ") = " + tribonacci(n));
    }
}