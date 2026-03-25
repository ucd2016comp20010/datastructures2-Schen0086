package project20280.hashtable;

import project20280.interfaces.Entry;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class WordFrequency {

    public static void main(String[] args) throws FileNotFoundException {
        // Wk8 Q5: open the input file containing the sample text
        File f = new File("sample_text.txt");

        // Wk8 Q5: create a ChainHashMap to store each word and its frequency
        ChainHashMap<String, Integer> counter = new ChainHashMap<String, Integer>();

        // Wk8 Q5: use Scanner to read the file one word at a time
        Scanner scanner = new Scanner(f);

        while (scanner.hasNext()) {
            String word = scanner.next();

            // Wk8 Q5: convert to lowercase and remove punctuation/non-letter characters
            word = word.toLowerCase().replaceAll("[^a-z]", "");

            // Wk8 Q5: skip empty strings after cleaning the word
            if (word.isEmpty()) {
                continue;
            }

            // Wk8 Q5: if word is not already in the map, add it with count 1
            if (counter.get(word) == null) {
                counter.put(word, 1);
            } else {
                // Wk8 Q5: otherwise increment the existing word count
                counter.put(word, counter.get(word) + 1);
            }
        }

        scanner.close();

        // Wk8 Q5: copy all entries from the hash map into an ArrayList for sorting
        ArrayList<Entry<String, Integer>> entries = new ArrayList<>();
        for (Entry<String, Integer> e : counter.entrySet()) {
            entries.add(e);
        }

        // Wk8 Q5: sort entries by frequency in descending order
        // Wk8 Q5: if frequencies are equal, sort alphabetically by word
        entries.sort((e1, e2) -> {
            int valueCompare = Integer.compare(e2.getValue(), e1.getValue());
            if (valueCompare != 0) {
                return valueCompare;
            }
            return e1.getKey().compareTo(e2.getKey());
        });

        // Wk8 Q5: print the top 10 most frequent words
        System.out.println("Top 10 most frequent words:");
        for (int i = 0; i < 10 && i < entries.size(); i++) {
            Entry<String, Integer> e = entries.get(i);
            System.out.println((i + 1) + ". " + e.getKey() + " -> " + e.getValue());
        }
    }
}