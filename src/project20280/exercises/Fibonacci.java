package project20280.exercises;

public class Fibonacci {

    // counts how many times fibNaive is called
    static long naiveCalls = 0;

    // Naive binary recursion
    public static long fibNaive(int n) {
        naiveCalls++;
        if (n <= 0) return 0;
        if (n == 1) return 1;
        return fibNaive(n - 1) + fibNaive(n - 2);
    }

    public static void main(String[] args) {
        int n = 5;
        long value = fibNaive(n);
        System.out.println("Fib(" + n + ") = " + value);
        System.out.println("Naive recursive calls = " + naiveCalls);
    }
}
