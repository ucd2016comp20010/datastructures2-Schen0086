package project20280.tree;

import project20280.interfaces.Position;

import java.util.ArrayList;

/**
 * Concrete implementation of a binary tree using a node-based, linked structure.
 */
public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {

    static java.util.Random rnd = new java.util.Random();

    /**
     * The root of the binary tree.
     */
    protected Node<E> root = null;

    /**
     * The number of nodes in the binary tree.
     */
    private int size = 0;

    /**
     * Constructs an empty binary tree.
     */
    public LinkedBinaryTree() {
    }

    // --- factory methods used elsewhere in the project (already provided) ---

    public static LinkedBinaryTree<Integer> makeRandom(int n) {
        LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<>();
        bt.root = randomTree(null, 1, n);

        // set size without using positions()/isEmpty()
        bt.size = bt.countNodes(bt.root);
        return bt;
    }

    public static <T extends Integer> Node<T> randomTree(Node<T> parent, Integer first, Integer last) {
        if (first > last) return null;
        else {
            Integer treeSize = last - first + 1;
            Integer leftCount = rnd.nextInt(treeSize);
            Node<T> root = new Node<T>((T) ((Integer) (first + leftCount)), parent, null, null);
            root.setLeft(randomTree(root, first, first + leftCount - 1));
            root.setRight(randomTree(root, first + leftCount + 1, last));
            return root;
        }
    }

    /**
     * Factory function to create a new node storing element e.
     */
    protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right) {
        return new Node<E>(e, parent, left, right);
    }

    /**
     * Verifies that a Position is a Node and has not been removed.
     */
    protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if (!(p instanceof Node)) throw new IllegalArgumentException("Not valid position type");
        Node<E> node = (Node<E>) p;
        if (node.getParent() == node)
            throw new IllegalArgumentException("p is no longer in the tree");
        return node;
    }

    // ----------------- accessors -----------------

    @Override
    public int size() {
        return size;
    }

    @Override
    public Position<E> root() {
        return root;
    }

    @Override
    public Position<E> parent(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return node.getParent();
    }

    @Override
    public Position<E> left(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return node.getLeft();
    }

    @Override
    public Position<E> right(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return node.getRight();
    }

    /**
     * Returns an iterable collection of the children of position p.
     * For a binary tree, this is at most two positions: left and right.
     */
    @Override
    public Iterable<Position<E>> children(Position<E> p) throws IllegalArgumentException {

        Node<E> node = validate(p);

        ArrayList<Position<E>> snapshot = new ArrayList<>(2);

        // Add left child if it exists
        if (node.getLeft() != null) {
            snapshot.add(node.getLeft());
        }

        // Add right child if it exists
        if (node.getRight() != null) {
            snapshot.add(node.getRight());
        }

        return snapshot;
    }

    // ----------------- updates -----------------

    /**
     * Places element e at the root of an empty tree and returns its new Position.
     */
    // (d) addRoot: creates the root node in an empty tree and returns its position.
    public Position<E> addRoot(E e) throws IllegalStateException {
        // (d) start by implementing addRoot
        if (!isEmpty()) throw new IllegalStateException("Tree is not empty");
        root = createNode(e, null, null, null);
        size = 1;
        return root;
    }

    /**
     * Optional insert method (not required by your provided unit tests).
     * If you keep it, it should not break anything.
     */
    public void insert(E e) {
        // (e) not required for LinkedBinaryTreeTest; included for completeness
        if (root == null) {
            addRoot(e);
            return;
        }
        root = addRecursive(root, e);
        root.setParent(null);
    }

    /**
     * Recursively insert in Binary Search Tree (BST) style (only meaningful if E is Comparable).
     */
    private Node<E> addRecursive(Node<E> p, E e) {
        // (e) not required by tests; basic BST insertion
        if (!(e instanceof Comparable))
            throw new IllegalArgumentException("insert requires Comparable elements");

        @SuppressWarnings("unchecked")
        Comparable<? super E> key = (Comparable<? super E>) e;

        if (p == null) {
            size++;
            return createNode(e, null, null, null);
        }

        int cmp = key.compareTo(p.getElement());
        if (cmp < 0) {
            Node<E> child = addRecursive(p.getLeft(), e);
            p.setLeft(child);
            if (child != null) child.setParent(p);
        } else if (cmp > 0) {
            Node<E> child = addRecursive(p.getRight(), e);
            p.setRight(child);
            if (child != null) child.setParent(p);
        }
        return p;
    }

    // (h) Counts nodes directly from Node references, without using size()/isEmpty().
    private int countNodes(Node<E> p) {
        if (p == null) return 0;
        return 1 + countNodes(p.getLeft()) + countNodes(p.getRight());
    }

    /**
     * Creates a new left child of Position p storing element e and returns its Position.
     */
    // (d) addLeft: creates and attaches a new left child of p.
    public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException {
        // (d) implement addLeft
        Node<E> parent = validate(p);
        if (parent.getLeft() != null)
            throw new IllegalArgumentException("p already has a left child");

        Node<E> child = createNode(e, parent, null, null);
        parent.setLeft(child);
        size++;
        return child;
    }

    /**
     * Creates a new right child of Position p storing element e and returns its Position.
     */
    // (d) addRight: creates and attaches a new right child of p.
    public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException {
        // (d) implement addRight
        Node<E> parent = validate(p);
        if (parent.getRight() != null)
            throw new IllegalArgumentException("p already has a right child");

        Node<E> child = createNode(e, parent, null, null);
        parent.setRight(child);
        size++;
        return child;
    }

    /**
     * Replaces the element at Position p with element e and returns the replaced element.
     */
    public E set(Position<E> p, E e) throws IllegalArgumentException {
        // (e) standard mutator used by other structures
        Node<E> node = validate(p);
        E old = node.getElement();
        node.setElement(e);
        return old;
    }

    /**
     * Attaches trees t1 and t2 as left and right subtrees of the leaf Position p.
     */
    public void attach(Position<E> p, LinkedBinaryTree<E> t1, LinkedBinaryTree<E> t2)
            throws IllegalArgumentException {
        // (e) not used by your LinkedBinaryTreeTest, but commonly required
        Node<E> node = validate(p);
        if (isInternal(p)) throw new IllegalArgumentException("p must be a leaf");

        if (t1 != null && !t1.isEmpty()) {
            node.setLeft(t1.root);
            t1.root.setParent(node);
            size += t1.size;
            t1.root = null;
            t1.size = 0;
        }

        if (t2 != null && !t2.isEmpty()) {
            node.setRight(t2.root);
            t2.root.setParent(node);
            size += t2.size;
            t2.root = null;
            t2.size = 0;
        }
    }

    /**
     * Removes the node at Position p and replaces it with its child, if any.
     */
    public E remove(Position<E> p) throws IllegalArgumentException {
        // (e) required by tests
        Node<E> node = validate(p);

        Node<E> left = node.getLeft();
        Node<E> right = node.getRight();

        // cannot remove a node with two children using this simple remove
        if (left != null && right != null)
            throw new IllegalArgumentException("p has two children");

        // child is either the single child, or null (if leaf)
        Node<E> child = (left != null ? left : right);

        // reconnect child to node's parent
        if (child != null) {
            child.setParent(node.getParent());
        }

        if (node == root) {
            // removing the root: child becomes new root (or null if tree becomes empty)
            root = child;
        } else {
            Node<E> parent = node.getParent();
            if (parent.getLeft() == node) parent.setLeft(child);
            else parent.setRight(child);
        }

        size--;

        // mark removed node as defunct and help garbage collection
        E old = node.getElement();
        node.setElement(null);
        node.setLeft(null);
        node.setRight(null);
        node.setParent(node); // defunct convention used by validate
        return old;
    }

    /**
     * String form uses positions(), which in this project is inorder (see AbstractBinaryTree).
     */
    // (g) If positions() is changed to return preorder() instead of inorder(),
    //     then toString() and any method using positions() will output the tree
    //     in preorder order rather than inorder order, causing different traversal results.
    public String toString() {
        // (g) toString delegates to inorder positions
        return positions().toString();
    }

    // ----------------- level-order construction -----------------

    /**
     * Builds a tree from a level-order ArrayList representation.
     * Index i has children at 2i+1 and 2i+2.
     */
    public void createLevelOrder(ArrayList<E> l) {
        if (l == null || l.isEmpty()) {
            root = null;
            size = 0;
            return;
        }

        root = createLevelOrderHelper(l, null, 0);

        // (h) set size without relying on positions()/isEmpty()
        size = countNodes(root);
    }

    private Node<E> createLevelOrderHelper(ArrayList<E> l, Node<E> parent, int i) {
        // (e) recursive builder for ArrayList version
        if (i >= l.size()) return null;
        E val = l.get(i);
        if (val == null) return null;

        Node<E> node = createNode(val, parent, null, null);
        node.setLeft(createLevelOrderHelper(l, node, 2 * i + 1));
        node.setRight(createLevelOrderHelper(l, node, 2 * i + 2));
        return node;
    }

    /**
     * Builds a tree from a level-order array representation.
     * This is required by your unit tests.
     */
    public void createLevelOrder(E[] arr) {
        // (e) required by tests
        if (arr == null || arr.length == 0) {
            root = null;
            size = 0;
            return;
        }

        root = createLevelOrderHelper(arr, null, 0);

        // (h) set size without relying on positions()/isEmpty()
        size = countNodes(root);
    }


    private Node<E> createLevelOrderHelper(E[] arr, Node<E> parent, int i) {
        // (e) recursive builder for array version
        if (i >= arr.length) return null;
        if (arr[i] == null) return null;

        Node<E> node = createNode(arr[i], parent, null, null);
        node.setLeft(createLevelOrderHelper(arr, node, 2 * i + 1));
        node.setRight(createLevelOrderHelper(arr, node, 2 * i + 2));
        return node;
    }

    public String toBinaryTreeString() {
        BinaryTreePrinter<E> btp = new BinaryTreePrinter<>(this);
        return btp.print();
    }

    /**
     * Nested static class for a binary tree node.
     */
    // (c) Node<E> class: this stores the element and links to parent/left/right.
    //     It is the basic building block for the linked binary tree.
    public static class Node<E> implements Position<E> {
        private E element;
        private Node<E> left, right, parent;

        public Node(E e, Node<E> p, Node<E> l, Node<E> r) {
            element = e;
            left = l;
            right = r;
            parent = p;
        }

        public E getElement() {
            return element;
        }

        public void setElement(E e) {
            element = e;
        }

        public Node<E> getLeft() {
            return left;
        }

        public void setLeft(Node<E> n) {
            left = n;
        }

        public Node<E> getRight() {
            return right;
        }

        public void setRight(Node<E> n) {
            right = n;
        }

        public Node<E> getParent() {
            return parent;
        }

        public void setParent(Node<E> n) {
            parent = n;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (element == null) {
                sb.append("\u29B0");
            } else {
                sb.append(element);
            }
            return sb.toString();
        }
    }

    // Q2
    // Counts the number of external nodes (leaves) in the subtree rooted at p.
    private int countExternal(Position<E> p) {
        if (p == null) return 0;

        // A node is external if it has no left and no right child.
        if (left(p) == null && right(p) == null) return 1;

        return countExternal(left(p)) + countExternal(right(p));
    }

    // Counts the number of external nodes (leaves) in the whole tree.
    public int numExternal() {
        if (root() == null) return 0;
        return countExternal(root());
    }

    // To test counting number of external nodes works as intended
    public static void main(String[] args) {
        LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<>();
        Position<Integer> r = bt.addRoot(1);
        bt.addLeft(r, 2);
        bt.addRight(r, 3);

        System.out.println(bt.numExternal()); // expected output is 2 (2 and 3)
    }

}
