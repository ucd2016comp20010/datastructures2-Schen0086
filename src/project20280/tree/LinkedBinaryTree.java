package project20280.tree;

import project20280.interfaces.Position;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

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

        if (node.getLeft() != null) {
            snapshot.add(node.getLeft());
        }

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
        Node<E> node = validate(p);

        Node<E> left = node.getLeft();
        Node<E> right = node.getRight();

        if (left != null && right != null)
            throw new IllegalArgumentException("p has two children");

        Node<E> child = (left != null ? left : right);

        if (child != null) {
            child.setParent(node.getParent());
        }

        if (node == root) {
            root = child;
        } else {
            Node<E> parent = node.getParent();
            if (parent.getLeft() == node) parent.setLeft(child);
            else parent.setRight(child);
        }

        size--;

        E old = node.getElement();
        node.setElement(null);
        node.setLeft(null);
        node.setRight(null);
        node.setParent(node);
        return old;
    }

    /**
     * String form uses positions(), which in this project is inorder (see AbstractBinaryTree).
     */
    // (g) If positions() is changed to return preorder() instead of inorder(),
    //     then toString() and any method using positions() will output the tree
    //     in preorder order rather than inorder order, causing different traversal results.
    public String toString() {
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
     * Index i has children at 2i+1 and 2i+2.
     *
     * Wk5 Q2: This method constructs the tree from the array used in the question:
     *         {"A","B","C","D","E",null,"F",null,null,"G","H",null,null,null,null}.
     *         Null entries mean "no node at this position".
     */
    // Null entries mean "no node at this position".
    // Children index rules: left = 2*i+1, right = 2*i+2.
    public void createLevelOrder(E[] arr) {
        // Wk5 Q2: handle empty input array (tree becomes empty)
        if (arr == null || arr.length == 0) {
            root = null;
            size = 0;
            return;
        }

        // Wk5 Q2: build the linked structure using the recursive helper and index rules
        root = createLevelOrderHelper(arr, null, 0);

        // Wk5 Q2: update size by counting created nodes (non-null entries that are reachable)
        size = countNodes(root);
    }

    /**
     * Recursive helper for createLevelOrder(E[] arr).
     *
     * Wk5 Q2: Uses level-order index mapping:
     *         left child index  = 2*i + 1
     *         right child index = 2*i + 2
     *         If arr[i] is null, no node is created and recursion stops on that branch.
     */
    // Wk5 Q2: Recursive helper for createLevelOrder.
    // Stops when index is out of bounds or arr[i] is null.
    private Node<E> createLevelOrderHelper(E[] arr, Node<E> parent, int i) {
        // Wk5 Q2: stop if index is outside the array
        if (i >= arr.length) return null;

        // Wk5 Q2: null means no node at this position
        if (arr[i] == null) return null;

        // Wk5 Q2: create the node and recursively build its children
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

    // ----------------- Week 5 questions -----------------

    /**
     * Wk5 Q2: Counts the number of external nodes (leaves) in the subtree rooted at p.
     * A node is external if it has no left child and no right child.
     */
    private int countExternal(Position<E> p) {
        if (p == null) return 0;

        if (left(p) == null && right(p) == null) return 1;

        return countExternal(left(p)) + countExternal(right(p));
    }

    /**
     * Wk5 Q2: Counts the number of external nodes (leaves) in the whole tree.
     */
    public int numExternal() {
        if (root() == null) return 0;
        return countExternal(root());
    }

    /**
     * Wk5 Q2: Demonstration main for building a tree from a level-order array and printing it.
     * This matches the example given in the question and should print the required structure.
     */
    // Wk5 Q2: Demonstration of building a tree from a level-order array and printing it.
   // This matches the example in the question and should print the required structure.
    public static void wk5Q2LevelOrderDemo() {
        LinkedBinaryTree<String> bt = new LinkedBinaryTree<>();
        String[] arr = { "A", "B", "C", "D", "E", null, "F", null, null, "G", "H", null, null, null, null };
        bt.createLevelOrder(arr);
        System.out.println(bt.toBinaryTreeString());
    }

    /**
     * Wk5 Q3:
     * Constructs this tree from inorder and preorder traversals.
     * Assumes:
     *  - All elements are unique
     *  - inorder and preorder contain the same elements
     * After construction, this tree's root/size are updated.
     */
    public void construct(E[] inorder, E[] preorder) {

        if (inorder == null || preorder == null || inorder.length != preorder.length) {
            throw new IllegalArgumentException("Invalid traversal arrays");
        }

        if (inorder.length == 0) {
            root = null;
            size = 0;
            return;
        }

        // Build a quick lookup: value -> index in inorder
        Map<E, Integer> inIndex = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inIndex.put(inorder[i], i);
        }

        // Use an index wrapper so recursion can advance through preorder
        int[] preIdx = new int[]{0};

        // Build the tree
        root = constructHelper(inorder, preorder, 0, inorder.length - 1, preIdx, null, inIndex);

        // Update size without relying on positions()/isEmpty()
        size = countNodes(root);
    }

    /**
     * Wk5 Q3:
     * Recursive helper to build subtree from inorder[inL...inR] using preorder[preIdx[0]...].
     *
     * Algorithm:
     *  - preorder gives the root of the current subtree first
     *  - find that root in inorder to split left/right subtrees
     */
    private Node<E> constructHelper(
            E[] inorder,
            E[] preorder,
            int inL,
            int inR,
            int[] preIdx,
            Node<E> parent,
            Map<E, Integer> inIndex) {

        // No elements in this inorder range means no subtree
        if (inL > inR) return null;

        // Next preorder element is the root of this subtree
        E rootVal = preorder[preIdx[0]];
        preIdx[0]++;

        // Create the node and set its parent link
        Node<E> node = createNode(rootVal, parent, null, null);

        // Split inorder into left and right parts around rootVal
        Integer midObj = inIndex.get(rootVal);
        if (midObj == null) {
            throw new IllegalArgumentException("Traversals contain different elements");
        }
        int mid = midObj;

        // Build left subtree from inorder[inL..mid-1]
        node.setLeft(constructHelper(inorder, preorder, inL, mid - 1, preIdx, node, inIndex));

        // Build right subtree from inorder[mid+1...inR]
        node.setRight(constructHelper(inorder, preorder, mid + 1, inR, preIdx, node, inIndex));

        return node;
    }
    // Test in ConstructTest.java class

    // Wk5 Q4: Returns a list of all root-to-leaf paths (left paths first, then right paths).
    public ArrayList<ArrayList<E>> rootToLeafPaths() {
        ArrayList<ArrayList<E>> result = new ArrayList<>();

        if (root() == null) return result;

        ArrayList<E> currentPath = new ArrayList<>();
        rootToLeafPathsHelper(root(), currentPath, result);

        return result;
    }

    // Wk5 Q4: Recursive helper that builds paths using backtracking.
    private void rootToLeafPathsHelper(Position<E> p,
                                       ArrayList<E> currentPath,
                                       ArrayList<ArrayList<E>> result) {

        if (p == null) return;

        // Add current node to the path
        currentPath.add(p.getElement());

        // If this node is a leaf, store a copy of the current path
        if (left(p) == null && right(p) == null) {
            result.add(new ArrayList<>(currentPath));
        } else {
            // Explore left subtree first, then right subtree
            if (left(p) != null) rootToLeafPathsHelper(left(p), currentPath, result);
            if (right(p) != null) rootToLeafPathsHelper(right(p), currentPath, result);
        }

        // Remove current node before returning to parent (backtracking)
        currentPath.remove(currentPath.size() - 1);
    }
    // Test in RandomTreeDemo for a random tree or in LinkedBinaryTreeTest exactly as given example

    // Helper record-like class to return both height and diameter from recursion.
    private static class HD {
        int height;    // height in number of nodes
        int diameter;  // diameter in number of nodes

        HD(int h, int d) {
            height = h;
            diameter = d;
        }
    }

    /**
     * Returns the diameter (width) of the tree, measured as number of nodes
     * on the longest path between any two nodes.
     */
    public int diameterNodes() {
        if (root() == null) return 0;
        return diameterNodesHelper(root()).diameter;
    }

    /**
     * Recursively computes height and diameter for the subtree rooted at p.
     * Height is measured in number of nodes.
     */
    private HD diameterNodesHelper(Position<E> p) {
        // Base case for empty subtree
        if (p == null) return new HD(0, 0);

        // Compute results for left and right subtrees
        HD leftRes = diameterNodesHelper(left(p));
        HD rightRes = diameterNodesHelper(right(p));

        // Height in nodes: 1 for current node + max child height
        int height = 1 + Math.max(leftRes.height, rightRes.height);

        // Longest path through this node (in nodes)
        int through = leftRes.height + rightRes.height + 1;

        // Best diameter in this subtree
        int diameter = Math.max(through, Math.max(leftRes.diameter, rightRes.diameter));

        return new HD(height, diameter);
    }
    // Test with example given in DiameterDemo.java class

    // To test counting number of external nodes works as intended
    public static void main(String[] args) {
        LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<>();
        Position<Integer> r = bt.addRoot(1);
        bt.addLeft(r, 2);
        bt.addRight(r, 3);
        System.out.println(bt.numExternal()); // expected 2

        // Wk5 Q2: uncomment to run the level-order printing demo
        wk5Q2LevelOrderDemo();
    }

}
