package project20280.tree;

import project20280.interfaces.Entry;
import project20280.interfaces.Position;

import java.util.Comparator;

/**
 * An implementation of a sorted map using an AVL tree.
 */
public class AVLTreeMap<K, V> extends TreeMap<K, V> {

    public AVLTreeMap() {
        super();
    }

    public AVLTreeMap(Comparator<K> comp) {
        super(comp);
    }

    /**
     * Returns the height of the given tree position.
     */
    protected int height(Position<Entry<K, V>> p) {
        // Wk9 Q1: treat null like an empty subtree
        if (p == null) return 0;
        return tree.getAux(p);
    }

    /**
     * Recomputes the height of the given position based on its children's heights.
     */
    protected void recomputeHeight(Position<Entry<K, V>> p) {
        // Wk9 Q1: update stored AVL height
        if (p == null) return;
        tree.setAux(p, 1 + Math.max(height(left(p)), height(right(p))));
    }

    /**
     * Returns whether a position has balance factor between -1 and 1 inclusive.
     */
    protected boolean isBalanced(Position<Entry<K, V>> p) {
        // Wk9 Q1: AVL balance condition
        if (p == null) return true;
        return Math.abs(height(left(p)) - height(right(p))) <= 1;
    }

    /**
     * Returns a child of p with height no smaller than that of the other child.
     */
    protected Position<Entry<K, V>> tallerChild(Position<Entry<K, V>> p) {
        // Wk9 Q1: break ties based on p's orientation
        if (p == null) return null;

        if (height(left(p)) > height(right(p))) return left(p);
        if (height(left(p)) < height(right(p))) return right(p);

        if (isRoot(p)) return left(p);
        return (p == left(parent(p))) ? left(p) : right(p);
    }

    /**
     * Utility used to rebalance after an insert or removal operation.
     * This traverses the path upward from p, performing a trinode restructuring when
     * imbalance is found, continuing until balance is restored.
     */
    protected void rebalance(Position<Entry<K, V>> p) {
        // Wk9 Q1: walk upward and fix AVL imbalances
        while (p != null) {
            recomputeHeight(p);

            if (!isBalanced(p)) {
                p = restructure(tallerChild(tallerChild(p)));
                recomputeHeight(left(p));
                recomputeHeight(right(p));
                recomputeHeight(p);
            }

            p = parent(p);
        }
    }

    @Override
    protected void rebalanceInsert(Position<Entry<K, V>> p) {
        rebalance(p);
    }

    @Override
    protected void rebalanceDelete(Position<Entry<K, V>> p) {
        // Wk9 Q1: deletions can also unbalance ancestors
        if (p != null) rebalance(p);
    }

    private boolean sanityCheck() {
        for (Position<Entry<K, V>> p : tree.positions()) {
            if (isInternal(p)) {
                if (p.getElement() == null)
                    System.out.println("VIOLATION: Internal node has null entry");
                else if (height(p) != 1 + Math.max(height(left(p)), height(right(p)))) {
                    System.out.println("VIOLATION: AVL unbalanced node with key " + p.getElement().getKey());
                    dump();
                    return false;
                }
            }
        }
        return true;
    }

    public String toBinaryTreeString() {
        BinaryTreePrinter<Entry<K, V>> btp = new BinaryTreePrinter<>(this.tree);
        return btp.print();
    }

    public static void main(String[] args) {
        AVLTreeMap<Integer, Integer> avl = new AVLTreeMap<>();

        Integer[] arr = new Integer[]{5, 3, 10, 2, 4, 7, 11, 1, 6, 9, 12, 8};

        for (Integer i : arr) {
            if (i != null) avl.put(i, i);
            System.out.println("root " + avl.root());
        }
        System.out.println(avl.toBinaryTreeString());

        avl.remove(5);
        System.out.println(avl.toBinaryTreeString());
    }
}