package project20280.tree;

import project20280.interfaces.Entry;
import project20280.interfaces.Position;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.function.Consumer;

/**
 * An implementation of a sorted map using a binary search tree.
 */
public class TreeMap<K, V> extends AbstractSortedMap<K, V> {

    // ---------------- nested BalanceableBinaryTree class ----------------

    /**
     * A specialized version of the LinkedBinaryTree class with additional mutators
     * to support binary search tree operations, and a specialized node class that
     * includes an auxiliary instance variable for balancing data.
     */
    protected static class BalanceableBinaryTree<K, V> extends LinkedBinaryTree<Entry<K, V>> {
        // -------------- nested BSTNode class --------------
        protected static class BSTNode<E> extends Node<E> {
            int aux = 0;

            BSTNode(E e, Node<E> parent, Node<E> leftChild, Node<E> rightChild) {
                super(e, parent, leftChild, rightChild);
            }

            public int getAux() {
                return aux;
            }

            public void setAux(int value) {
                aux = value;
            }
        }

        public int getAux(Position<Entry<K, V>> p) {
            return ((BSTNode<Entry<K, V>>) p).getAux();
        }

        public void setAux(Position<Entry<K, V>> p, int value) {
            ((BSTNode<Entry<K, V>>) p).setAux(value);
        }

        @Override
        protected Node<Entry<K, V>> createNode(Entry<K, V> e, Node<Entry<K, V>> parent, Node<Entry<K, V>> left,
                                               Node<Entry<K, V>> right) {
            return new BSTNode<>(e, parent, left, right);
        }

        /**
         * Relinks a parent node with its oriented child node.
         */
        private void relink(Node<Entry<K, V>> parent, Node<Entry<K, V>> child, boolean makeLeftChild) {
            // Wk9 Q1: attach child to the correct side of parent
            if (makeLeftChild) {
                parent.setLeft(child);
            } else {
                parent.setRight(child);
            }

            if (child != null) {
                child.setParent(parent);
            }
        }

        /**
         * Rotates Position p above its parent.
         */
        public void rotate(Position<Entry<K, V>> p) {
            // Wk9 Q1: standard single rotation
            Node<Entry<K, V>> x = validate(p);
            Node<Entry<K, V>> y = x.getParent();
            Node<Entry<K, V>> z = y.getParent();

            if (z == null) {
                root = x;
                x.setParent(null);
            } else {
                relink(z, x, y == z.getLeft());
            }

            if (x == y.getLeft()) {
                relink(y, x.getRight(), true);
                relink(x, y, false);
            } else {
                relink(y, x.getLeft(), false);
                relink(x, y, true);
            }
        }

        /**
         * Returns the Position that becomes the root of the restructured subtree.
         */
        public Position<Entry<K, V>> restructure(Position<Entry<K, V>> x) {
            // Wk9 Q1: single rotation for matching orientation, double otherwise
            Position<Entry<K, V>> y = parent(x);
            Position<Entry<K, V>> z = parent(y);

            if ((x == right(y)) == (y == right(z))) {
                rotate(y);
                return y;
            } else {
                rotate(x);
                rotate(x);
                return x;
            }
        }
    } // ----------- end of nested BalanceableBinaryTree class -----------

    protected BalanceableBinaryTree<K, V> tree = new BalanceableBinaryTree<>();

    public TreeMap() {
        super();
        tree.addRoot(null); // sentinel leaf
    }

    public TreeMap(Comparator<K> comp) {
        super(comp);
        tree.addRoot(null); // sentinel leaf
    }

    @Override
    public int size() {
        return (tree.size() - 1) / 2;
    }

    protected Position<Entry<K, V>> restructure(Position<Entry<K, V>> x) {
        return tree.restructure(x);
    }

    protected void rebalanceInsert(Position<Entry<K, V>> p) {
        // LEAVE EMPTY
    }

    protected void rebalanceDelete(Position<Entry<K, V>> p) {
        // LEAVE EMPTY
    }

    protected void rebalanceAccess(Position<Entry<K, V>> p) {
        // LEAVE EMPTY
    }

    private void expandExternal(Position<Entry<K, V>> p, Entry<K, V> entry) {
        tree.set(p, entry);
        tree.addLeft(p, null);
        tree.addRight(p, null);
    }

    protected Position<Entry<K, V>> root() {
        return tree.root();
    }

    protected Position<Entry<K, V>> parent(Position<Entry<K, V>> p) {
        return tree.parent(p);
    }

    protected Position<Entry<K, V>> left(Position<Entry<K, V>> p) {
        return tree.left(p);
    }

    protected Position<Entry<K, V>> right(Position<Entry<K, V>> p) {
        return tree.right(p);
    }

    protected Position<Entry<K, V>> sibling(Position<Entry<K, V>> p) {
        return tree.sibling(p);
    }

    protected boolean isRoot(Position<Entry<K, V>> p) {
        return tree.isRoot(p);
    }

    protected boolean isExternal(Position<Entry<K, V>> p) {
        return tree.isExternal(p);
    }

    protected boolean isInternal(Position<Entry<K, V>> p) {
        return tree.isInternal(p);
    }

    protected void set(Position<Entry<K, V>> p, Entry<K, V> e) {
        tree.set(p, e);
    }

    protected Entry<K, V> remove(Position<Entry<K, V>> p) {
        return tree.remove(p);
    }

    /**
     * Returns the position in p's subtree having the given key
     * (or else the terminal leaf).
     */
    private Position<Entry<K, V>> treeSearch(Position<Entry<K, V>> p, K key) {
        // Wk9 Q1: recursive BST search
        if (isExternal(p)) return p;

        int comp = compare(key, p.getElement().getKey());
        if (comp == 0) return p;
        if (comp < 0) return treeSearch(left(p), key);
        return treeSearch(right(p), key);
    }

    /**
     * Returns position with the minimal key in the subtree rooted at p.
     */
    protected Position<Entry<K, V>> treeMin(Position<Entry<K, V>> p) {
        // Wk9 Q1: keep moving left until the next left child is external
        Position<Entry<K, V>> walk = p;
        while (isInternal(left(walk))) {
            walk = left(walk);
        }
        return walk;
    }

    /**
     * Returns the position with the maximum key in the subtree rooted at p.
     */
    protected Position<Entry<K, V>> treeMax(Position<Entry<K, V>> p) {
        // Wk9 Q1: keep moving right until the next right child is external
        Position<Entry<K, V>> walk = p;
        while (isInternal(right(walk))) {
            walk = right(walk);
        }
        return walk;
    }

    @Override
    public V get(K key) throws IllegalArgumentException {
        // Wk9 Q1: search for the key and return its value if found
        checkKey(key);
        Position<Entry<K, V>> p = treeSearch(root(), key);
        rebalanceAccess(p);

        if (isExternal(p)) return null;
        return p.getElement().getValue();
    }

    @Override
    public V put(K key, V value) throws IllegalArgumentException {
        // Wk9 Q1: overwrite existing key, otherwise expand the failed-search leaf
        checkKey(key);
        Position<Entry<K, V>> p = treeSearch(root(), key);

        if (isInternal(p)) {
            V old = p.getElement().getValue();
            set(p, new MapEntry<>(key, value));
            rebalanceAccess(p);
            return old;
        }

        expandExternal(p, new MapEntry<>(key, value));
        rebalanceInsert(p);
        return null;
    }

    @Override
    public V remove(K key) throws IllegalArgumentException {
        // Wk9 Q1: standard BST delete with external leaves
        checkKey(key);
        Position<Entry<K, V>> p = treeSearch(root(), key);

        if (isExternal(p)) {
            rebalanceAccess(p);
            return null;
        }

        V old = p.getElement().getValue();

        if (isInternal(left(p)) && isInternal(right(p))) {
            Position<Entry<K, V>> replacement = treeMax(left(p));
            set(p, replacement.getElement());
            p = replacement;
        }

        Position<Entry<K, V>> leaf = isExternal(left(p)) ? left(p) : right(p);
        Position<Entry<K, V>> sib = sibling(leaf);

        tree.remove(leaf);
        tree.remove(p);

        // Wk9 Q1: rebalance from the surviving sibling after deletion
        if (sib != null) {
            rebalanceDelete(sib);
        } else if (!isEmpty()) {
            rebalanceDelete(root());
        }

        return old;
    }

    @Override
    public Entry<K, V> firstEntry() {
        if (isEmpty()) return null;
        return treeMin(root()).getElement();
    }

    @Override
    public Entry<K, V> lastEntry() {
        if (isEmpty()) return null;
        return treeMax(root()).getElement();
    }

    // --- helpers for navigation methods ---

    protected Position<Entry<K, V>> treeSuccessor(Position<Entry<K, V>> p) {
        if (isInternal(right(p))) return treeMin(right(p));

        Position<Entry<K, V>> walk = p;
        Position<Entry<K, V>> up = parent(walk);

        while (up != null && walk == right(up)) {
            walk = up;
            up = parent(walk);
        }
        return up;
    }

    protected Position<Entry<K, V>> treePredecessor(Position<Entry<K, V>> p) {
        if (isInternal(left(p))) return treeMax(left(p));

        Position<Entry<K, V>> walk = p;
        Position<Entry<K, V>> up = parent(walk);

        while (up != null && walk == left(up)) {
            walk = up;
            up = parent(walk);
        }
        return up;
    }

    @Override
    public Entry<K, V> ceilingEntry(K key) throws IllegalArgumentException {
        // Wk9 Q1: smallest key >= given key
        checkKey(key);
        Position<Entry<K, V>> p = treeSearch(root(), key);

        if (isInternal(p)) return p.getElement();

        Position<Entry<K, V>> walk = p;
        Position<Entry<K, V>> up = parent(walk);

        while (up != null && walk == right(up)) {
            walk = up;
            up = parent(walk);
        }

        return up == null ? null : up.getElement();
    }

    @Override
    public Entry<K, V> floorEntry(K key) throws IllegalArgumentException {
        // Wk9 Q1: greatest key <= given key
        checkKey(key);
        Position<Entry<K, V>> p = treeSearch(root(), key);

        if (isInternal(p)) return p.getElement();

        Position<Entry<K, V>> walk = p;
        Position<Entry<K, V>> up = parent(walk);

        while (up != null && walk == left(up)) {
            walk = up;
            up = parent(walk);
        }

        return up == null ? null : up.getElement();
    }

    @Override
    public Entry<K, V> lowerEntry(K key) throws IllegalArgumentException {
        // Wk9 Q1: greatest key < given key
        checkKey(key);
        Position<Entry<K, V>> p = treeSearch(root(), key);

        if (isInternal(p)) {
            Position<Entry<K, V>> pred = treePredecessor(p);
            return pred == null ? null : pred.getElement();
        }

        Position<Entry<K, V>> walk = p;
        Position<Entry<K, V>> up = parent(walk);

        while (up != null && walk == left(up)) {
            walk = up;
            up = parent(walk);
        }

        return up == null ? null : up.getElement();
    }

    @Override
    public Entry<K, V> higherEntry(K key) throws IllegalArgumentException {
        // Wk9 Q1: smallest key > given key
        checkKey(key);
        Position<Entry<K, V>> p = treeSearch(root(), key);

        if (isInternal(p)) {
            Position<Entry<K, V>> succ = treeSuccessor(p);
            return succ == null ? null : succ.getElement();
        }

        Position<Entry<K, V>> walk = p;
        Position<Entry<K, V>> up = parent(walk);

        while (up != null && walk == right(up)) {
            walk = up;
            up = parent(walk);
        }

        return up == null ? null : up.getElement();
    }

    @Override
    public Iterable<Entry<K, V>> entrySet() {
        ArrayList<Entry<K, V>> buffer = new ArrayList<>(size());
        for (Position<Entry<K, V>> p : tree.inorder()) {
            if (isInternal(p)) {
                buffer.add(p.getElement());
            }
        }
        return buffer;
    }

    public String toString() {
        return tree.toString();
    }

    @Override
    public Iterable<Entry<K, V>> subMap(K fromKey, K toKey) throws IllegalArgumentException {
        // Wk9 Q1: inorder scan and keep entries in [fromKey, toKey)
        checkKey(fromKey);
        checkKey(toKey);

        ArrayList<Entry<K, V>> buffer = new ArrayList<>();
        for (Entry<K, V> e : entrySet()) {
            if (compare(e.getKey(), fromKey) >= 0 && compare(e.getKey(), toKey) < 0) {
                buffer.add(e);
            }
        }
        return buffer;
    }

    protected void rotate(Position<Entry<K, V>> p) {
        tree.rotate(p);
    }

    protected void dump() {
        dumpRecurse(root(), 0);
    }

    private void dumpRecurse(Position<Entry<K, V>> p, int depth) {
        String indent = (depth == 0 ? "" : String.format("%" + (2 * depth) + "s", ""));
        if (isExternal(p))
            System.out.println(indent + "leaf");
        else {
            System.out.println(indent + p.getElement());
            dumpRecurse(left(p), depth + 1);
            dumpRecurse(right(p), depth + 1);
        }
    }

    public String toBinaryTreeString() {
        BinaryTreePrinter<Entry<K, V>> btp = new BinaryTreePrinter<>(this.tree);
        return btp.print();
    }

    public static void main(String[] args) {
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();

        Random rnd = new Random();
        int n_max = 50;

        Consumer<Integer> modify = x -> {
            if (rnd.nextFloat() > 0.5)
                treeMap.put(x, 0);
            else
                treeMap.remove(x);
        };

        BinaryTreePrinter<Entry<Integer, Integer>> btp = new BinaryTreePrinter<>(treeMap.tree);
        System.out.println(btp.print());

        rnd.ints(1, n_max).limit(10_000).boxed().forEach(modify);
        System.out.println(btp.print());
    }
}