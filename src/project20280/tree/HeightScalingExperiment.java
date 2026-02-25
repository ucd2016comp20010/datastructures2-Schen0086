package project20280.tree;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

// Wk5 Q6

/**
 * Generates random binary trees for n = 50..5000 step 50.
 * For each n, builds 100 random trees and computes average height.
 * Writes results to a CSV file for Excel/Google Sheets.
 */
public class HeightScalingExperiment {

    private static final int N_START = 50;
    private static final int N_END = 5000;
    private static final int N_STEP = 50;

    private static final int TRIALS = 100;

    public static void main(String[] args) throws IOException {

        // Change filename if you want it somewhere else
        String outFile = "height_scaling.csv";

        try (PrintWriter out = new PrintWriter(new FileWriter(outFile))) {

            // CSV header row
            out.println("n,avgHeight");

            for (int n = N_START; n <= N_END; n += N_STEP) {

                long sumHeights = 0;

                for (int t = 0; t < TRIALS; t++) {
                    LinkedBinaryTree<Integer> bt = LinkedBinaryTree.makeRandom(n);

                    // height() in your project returns height measured in edges
                    // That is fine as long as you use it consistently
                    int h = bt.height();
                    sumHeights += h;
                }

                double avg = (double) sumHeights / TRIALS;

                // Write one row per n
                out.printf("%d,%.6f%n", n, avg);

                // Optional progress indicator
                System.out.println("n=" + n + " avgHeight=" + avg);
            }
        }

        System.out.println("Wrote results to height_scaling.csv");
    }
}
