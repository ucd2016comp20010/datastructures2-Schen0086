package project20280.tree;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Wk6 Q10 - Inorder Scaling Experiment
 *
 * Measures execution time of inorder() traversal
 * for random binary trees.
 *
 * Generates trees for n = 10..10000 step 100.
 * For each n, builds multiple trees and averages runtime.
 * Writes results to a CSV file for Excel/Google Sheets.
 */
public class InorderScalingExperiment {

    private static final int N_START = 10;
    private static final int N_END = 10000;
    private static final int N_STEP = 100;

    private static final int TRIALS = 50;   // number of trees per n

    public static void main(String[] args) throws IOException {

        String outFile = "inorder_scaling.csv";

        try (PrintWriter out = new PrintWriter(new FileWriter(outFile))) {

            // CSV header
            out.println("n,avgTimeNano");

            for (int n = N_START; n <= N_END; n += N_STEP) {

                long totalTime = 0;

                for (int t = 0; t < TRIALS; t++) {

                    LinkedBinaryTree<Integer> bt = LinkedBinaryTree.makeRandom(n);

                    long start = System.nanoTime();

                    bt.inorder();  // measure inorder traversal

                    long end = System.nanoTime();

                    totalTime += (end - start);
                }

                double avgTime = (double) totalTime / TRIALS;

                out.printf("%d,%.2f%n", n, avgTime);

                // Progress indicator
                System.out.println("n=" + n + " avgTime(ns)=" + avgTime);
            }
        }

        System.out.println("Wrote results to inorder_scaling.csv");
    }
}