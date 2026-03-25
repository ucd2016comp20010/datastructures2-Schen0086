package project20280.hashtable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class HashFunctionAnalysis {

    public static void main(String[] args) throws FileNotFoundException {
        // Wk8 Q6: open the file containing the list of words
        File f = new File("words.txt");

        // Wk8 Q6: read all words from the file into an ArrayList
        java.util.ArrayList<String> words = new java.util.ArrayList<>();
        Scanner scanner = new Scanner(f);

        while (scanner.hasNext()) {
            words.add(scanner.next());
        }
        scanner.close();

        // Wk8 Q6: compute collisions for polynomial accumulation with a = 41
        int poly41Collisions = countCollisionsPoly(words, 41);
        System.out.println("(a) Collisions using polynomial accumulation with a = 41: " + poly41Collisions);

        // Wk8 Q6: compute collisions for polynomial accumulation with a = 17
        int poly17Collisions = countCollisionsPoly(words, 17);
        System.out.println("(b) Collisions using polynomial accumulation with a = 17: " + poly17Collisions);

        // Wk8 Q6: compute collisions for cyclic shift with shift = 7
        int cyclic7Collisions = countCollisionsCyclic(words, 7);
        System.out.println("(c) Collisions using cyclic shift with shift = 7: " + cyclic7Collisions);

        // Wk8 Q6: test all cyclic shift values from 0 to 31 and find the smallest collision count
        int bestShift = 0;
        int minCollisions = Integer.MAX_VALUE;

        System.out.println("(d) Collisions for cyclic shift values 0 to 31:");
        for (int shift = 0; shift <= 31; shift++) {
            int collisions = countCollisionsCyclic(words, shift);
            System.out.println("shift = " + shift + " -> " + collisions + " collisions");

            if (collisions < minCollisions) {
                minCollisions = collisions;
                bestShift = shift;
            }
        }

        System.out.println("Best shift value: " + bestShift);
        System.out.println("Smallest number of collisions: " + minCollisions);

        // Wk8 Q6: compute collisions using the old Java hash code function
        int oldJavaCollisions = countCollisionsOldJava(words);
        System.out.println("(e) Collisions using old Java hashCode function: " + oldJavaCollisions);
    }

    // Wk8 Q6: count collisions for polynomial accumulation hash
    public static int countCollisionsPoly(java.util.ArrayList<String> words, int a) {
        HashSet<Integer> seen = new HashSet<>();
        int collisions = 0;

        for (String word : words) {
            int h = hashPoly(word, a);

            // Wk8 Q6: if hash value already exists, this word caused a collision
            if (seen.contains(h)) {
                collisions++;
            } else {
                seen.add(h);
            }
        }

        return collisions;
    }

    // Wk8 Q6: count collisions for cyclic shift hash
    public static int countCollisionsCyclic(java.util.ArrayList<String> words, int shift) {
        HashSet<Integer> seen = new HashSet<>();
        int collisions = 0;

        for (String word : words) {
            int h = hashCyclic(word, shift);

            // Wk8 Q6: if hash value already exists, this word caused a collision
            if (seen.contains(h)) {
                collisions++;
            } else {
                seen.add(h);
            }
        }

        return collisions;
    }

    // Wk8 Q6: count collisions for the old Java hash code function
    public static int countCollisionsOldJava(java.util.ArrayList<String> words) {
        HashSet<Integer> seen = new HashSet<>();
        int collisions = 0;

        for (String word : words) {
            int h = oldJavaHashCode(word);

            // Wk8 Q6: if hash value already exists, this word caused a collision
            if (seen.contains(h)) {
                collisions++;
            } else {
                seen.add(h);
            }
        }

        return collisions;
    }

    // Wk8 Q6: polynomial accumulation hash function
    public static int hashPoly(String s, int a) {
        int h = 0;
        int n = s.length();

        for (int i = 0; i < n; i++) {
            char s_i = s.charAt(i);
            int v = (int) (s_i * Math.pow(a, n - i - 1));
            h += v;
        }

        return h;
    }

    // Wk8 Q6: cyclic shift hash function
    public static int hashCyclic(String s, int shift) {
        int h = 0;

        for (int i = 0; i < s.length(); ++i) {
            // Wk8 Q6: rotate bits left by the given shift amount
            h = (h << shift) | (h >>> (32 - shift));
            h += (int) s.charAt(i);
        }

        return h;
    }

    // Wk8 Q6: old Java hash code function
    public static int oldJavaHashCode(String s) {
        int hash = 0;
        int skip = Math.max(1, s.length() / 8);

        for (int i = 0; i < s.length(); i += skip) {
            hash = (hash * 37) + s.charAt(i);
        }

        return hash;
    }
}