package project20280.tree;

import project20280.interfaces.Entry;
import project20280.interfaces.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Wk9Q3 {

    // Wk9 Q3: compute height in edges, ignoring external sentinel leaves
    private static int height(TreeMap<Integer, Integer> map, Position<Entry<Integer, Integer>> p) {
        if (p == null || map.isExternal(p)) return -1;
        return 1 + Math.max(height(map, map.left(p)), height(map, map.right(p)));
    }

    private static int height(TreeMap<Integer, Integer> map) {
        return height(map, map.root());
    }

    private static TreeMap<Integer, Integer> buildRandomBST(int n, int n_max, Random rnd) {
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();

        // Wk9 Q3: build an initial random BST with n distinct keys
        rnd.ints(1, n_max)
                .distinct()
                .limit(n)
                .boxed()
                .forEach(x -> treeMap.put(x, x));

        return treeMap;
    }

    private static AVLTreeMap<Integer, Integer> buildRandomAVL(int n, int n_max, Random rnd) {
        AVLTreeMap<Integer, Integer> treeMap = new AVLTreeMap<>();

        // Wk9 Q3: build an initial random AVL tree with n distinct keys
        rnd.ints(1, n_max)
                .distinct()
                .limit(n)
                .boxed()
                .forEach(x -> treeMap.put(x, x));

        return treeMap;
    }

    private static double averageHeightDuringUpdates(TreeMap<Integer, Integer> treeMap,
                                                     int targetSize,
                                                     int n_max,
                                                     int n_trials,
                                                     int sampleEvery,
                                                     Random rnd) {

        long heightSum = 0;
        int samples = 0;

        for (int i = 0; i < n_trials; ++i) {
            var keyset = treeMap.keySet();
            List<Integer> target = new ArrayList<>();
            keyset.forEach(target::add);

            // ensure we put() a node which doesn't already exist in the tree
            // ensure we remove() a node which does exist in the tree

            // Wk9 Q3: keep the tree size close to targetSize
            boolean doPut;

            if (treeMap.size() == 0) {
                doPut = true;
            } else if (treeMap.size() < targetSize) {
                // Wk9 Q3: if the tree is too small, bias toward put()
                doPut = rnd.nextFloat() < 0.75f;
            } else if (treeMap.size() > targetSize) {
                // Wk9 Q3: if the tree is too large, bias toward remove()
                doPut = rnd.nextFloat() < 0.25f;
            } else {
                // Wk9 Q3: at the target size, choose put/remove with 50% probability
                doPut = rnd.nextFloat() > 0.5f;
            }

            if (treeMap.size() < n_max && doPut) {
                while (true) {
                    Integer x = rnd.nextInt(n_max);
                    if (!target.contains(x)) {
                        treeMap.put(x, x);
                        break;
                    }
                }
            } else {
                if (treeMap.size() == 0) continue;
                Integer x = target.get(rnd.nextInt(target.size()));
                treeMap.remove(x);
            }

            // Wk9 Q3: sample the height regularly during the random updates
            if ((i + 1) % sampleEvery == 0 && treeMap.size() > 0) {
                heightSum += height(treeMap);
                samples++;
            }
        }

        return samples == 0 ? 0.0 : (double) heightSum / samples;
    }

    private static double averageHeightDuringUpdates(AVLTreeMap<Integer, Integer> treeMap,
                                                     int targetSize,
                                                     int n_max,
                                                     int n_trials,
                                                     int sampleEvery,
                                                     Random rnd) {

        long heightSum = 0;
        int samples = 0;

        for (int i = 0; i < n_trials; ++i) {
            var keyset = treeMap.keySet();
            List<Integer> target = new ArrayList<>();
            keyset.forEach(target::add);

            // ensure we put() a node which doesn't already exist in the tree
            // ensure we remove() a node which does exist in the tree

            // Wk9 Q3: keep the tree size close to targetSize
            boolean doPut;

            if (treeMap.size() == 0) {
                doPut = true;
            } else if (treeMap.size() < targetSize) {
                // Wk9 Q3: if the tree is too small, bias toward put()
                doPut = rnd.nextFloat() < 0.75f;
            } else if (treeMap.size() > targetSize) {
                // Wk9 Q3: if the tree is too large, bias toward remove()
                doPut = rnd.nextFloat() < 0.25f;
            } else {
                // Wk9 Q3: at the target size, choose put/remove with 50% probability
                doPut = rnd.nextFloat() > 0.5f;
            }

            if (treeMap.size() < n_max && doPut) {
                while (true) {
                    Integer x = rnd.nextInt(n_max);
                    if (!target.contains(x)) {
                        treeMap.put(x, x);
                        break;
                    }
                }
            } else {
                if (treeMap.size() == 0) continue;
                Integer x = target.get(rnd.nextInt(target.size()));
                treeMap.remove(x);
            }

            // Wk9 Q3: sample the height regularly during the random updates
            if ((i + 1) % sampleEvery == 0 && treeMap.size() > 0) {
                heightSum += height(treeMap);
                samples++;
            }
        }

        return samples == 0 ? 0.0 : (double) heightSum / samples;
    }

    public static void main(String[] args) {
        Random rnd = new Random(42);

        // Wk9 Q3: use larger n values so scaling is easier to see
        int[] nValues = {100, 200, 400, 800, 1600, 3200, 6400};

        // Wk9 Q3: use more repeats to reduce noise
        int repeats = 100;

        // Wk9 Q3: use lots of updates so the tree reaches a steady random regime
        int n_trials = 20000;

        // Wk9 Q3: sample height every fixed number of operations
        int sampleEvery = 100;

        System.out.printf(
                "%8s %18s %18s %18s %18s %18s %18s%n",
                "n",
                "BST avg height",
                "BST h/sqrt(n)",
                "BST h/log(n)",
                "AVL avg height",
                "AVL h/sqrt(n)",
                "AVL h/log(n)"
        );

        for (int n : nValues) {
            int n_max = 4 * n;

            double bstHeightTotal = 0.0;
            double avlHeightTotal = 0.0;

            for (int r = 0; r < repeats; r++) {
                TreeMap<Integer, Integer> bst = buildRandomBST(n, n_max, rnd);
                AVLTreeMap<Integer, Integer> avl = buildRandomAVL(n, n_max, rnd);

                bstHeightTotal += averageHeightDuringUpdates(bst, n, n_max, n_trials, sampleEvery, rnd);
                avlHeightTotal += averageHeightDuringUpdates(avl, n, n_max, n_trials, sampleEvery, rnd);
            }

            double bstAvgHeight = bstHeightTotal / repeats;
            double avlAvgHeight = avlHeightTotal / repeats;

            double sqrtN = Math.sqrt(n);
            double logN = Math.log(n);

            System.out.printf(
                    "%8d %18.3f %18.3f %18.3f %18.3f %18.3f %18.3f%n",
                    n,
                    bstAvgHeight,
                    bstAvgHeight / sqrtN,
                    bstAvgHeight / logN,
                    avlAvgHeight,
                    avlAvgHeight / sqrtN,
                    avlAvgHeight / logN
            );
        }
    }
}

// Wk9 Q3: Yes, there is a clear difference in height. The ordinary BST is consistently
// taller than the AVL tree, and the difference becomes larger as n increases. This shows
// that the AVL tree remains much more balanced during random updates.
//
// Wk9 Q3: The O(sqrt(n)) scaling does not become clearly evident from these results.
// The BST ratio h / sqrt(n) is not approximately constant, while h / log(n) is much
// more stable. Therefore, the data shows slow growth in height, but not strong evidence
// for sqrt(n) scaling over the tested range.