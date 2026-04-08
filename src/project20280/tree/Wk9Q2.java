package project20280.tree;

import project20280.interfaces.Entry;

import java.util.Random;

public class Wk9Q2 {

    public static void main(String[] args) {
        TreeMap<Integer, Integer> bst = new TreeMap<>();
        Random rnd = new Random();

        int n_max = 50;
        int n = 20;

        // Wk9 Q2: insert 20 distinct random keys into the BST
        rnd.ints(1, n_max)
                .limit(n)
                .distinct()
                .boxed()
                .forEach(x -> bst.put(x, x));

        // Wk9 Q2: print a picture of the tree
        System.out.println("BST structure:");
        System.out.println(bst.toBinaryTreeString());

        // Wk9 Q2: inorder traversal should visit nodes in sorted order
        System.out.println("Inorder traversal:");
        System.out.println(bst.tree.inorder());
    }
}