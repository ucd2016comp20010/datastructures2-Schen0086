package project20280.tree;

import project20280.interfaces.Position;
import project20280.interfaces.Tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * An abstract base class providing some functionality of the Tree interface.
 *
 * The following three methods remain abstract, and must be
 * implemented by a concrete subclass: root, parent, children.
 */
public abstract class AbstractTree<E> implements Tree<E> {

    // (h) Used to count how many recursive calls height_recursive makes
    protected long heightCallCount = 0;

    // (i) Helper class to store height and diameter together
    protected static class HDPair {
        int height;
        int diameter;

        HDPair(int h, int d) {
            height = h;
            diameter = d;
        }
    }

    /**
     * Returns true if Position p has one or more children.
     */
    @Override
    public boolean isInternal(Position<E> p) {
        // (b) Tree ADT helper: internal nodes have at least one child
        return numChildren(p) > 0;
    }

    /**
     * Returns true if Position p does not have any children.
     */
    @Override
    public boolean isExternal(Position<E> p) {
        // (b) Tree ADT helper: external nodes have zero children
        return numChildren(p) == 0;
    }

    /**
     * Returns true if Position p represents the root of the tree.
     */
    @Override
    public boolean isRoot(Position<E> p) {
        // (b) Root is the unique position that equals root()
        return p == root();
    }

    /**
     * Returns the number of children of Position p.
     */
    @Override
    public int numChildren(Position<E> p) {
        // (b) Count the iterable returned by children(p)
        int count = 0;
        for (Position<E> c : children(p)) {
            count++;
        }
        return count;
    }

    /**
     * Returns the number of nodes in the tree.
     *
     * This generic implementation counts positions(). Concrete trees may
     * override size() for O(1).
     */
    @Override
    public int size() {
        int count = 0;
        for (Position p : positions()) count++;
        return count;
    }

    /**
     * Tests whether the tree is empty.
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    //---------- support for computing depth of nodes and height of (sub)trees ----------

    /**
     * Returns the number of levels separating Position p from the root.
     */
    public int depth(Position<E> p) throws IllegalArgumentException {
        // (b) Standard recursive definition of depth
        if (isRoot(p)) return 0;
        return 1 + depth(parent(p));
    }

    /**
     * Returns the height of the subtree rooted at Position p.
     *
     * (h) Also increments a counter each time this method is entered.
     * Height is measured in edges: a leaf has height 0.
     */
    public int height_recursive(Position<E> p) {
        // (h) count each recursive call
        heightCallCount++;

        int h = 0;
        for (Position<E> c : children(p)) {
            h = Math.max(h, 1 + height_recursive(c));
        }
        return h;
    }

    /**
     * Returns the height of the tree.
     */
    public int height() throws IllegalArgumentException {
        // (h) reset call counter each time height() is computed
        heightCallCount = 0;

        if (isEmpty()) return 0;
        return height_recursive(root());
    }

    /**
     * (h) Exposes the call count for experiments.
     */
    public long getHeightCallCount() {
        return heightCallCount;
    }

    //---------- support for various iterations of a tree ----------

    //---------------- nested ElementIterator class ----------------
    /* This class adapts the iteration produced by positions() to return elements. */
    private class ElementIterator implements Iterator<E> {
        Iterator<Position<E>> posIterator = positions().iterator();

        public boolean hasNext() {
            return posIterator.hasNext();
        }

        public E next() {
            return posIterator.next().getElement();
        }

        public void remove() {
            posIterator.remove();
        }
    }

    /**
     * Returns an iterator of the elements stored in the tree.
     */
    @Override
    public Iterator<E> iterator() {
        return new ElementIterator();
    }

    /**
     * Returns an iterable collection of the positions of the tree.
     *
     * Default is preorder for general trees. Binary trees override this in AbstractBinaryTree.
     */
    @Override
    public Iterable<Position<E>> positions() {
        return preorder();
    }

    /**
     * Adds positions of the subtree rooted at Position p to the given
     * snapshot using a preorder traversal.
     */
    private void preorderSubtree(Position<E> p, List<Position<E>> snapshot) {
        // (b) preorder: visit node, then visit children
        snapshot.add(p);
        for (Position<E> c : children(p)) {
            preorderSubtree(c, snapshot);
        }
    }

    /**
     * Returns an iterable collection of positions of the tree, reported in preorder.
     */
    public Iterable<Position<E>> preorder() {
        // (b) build list using recursive helper
        List<Position<E>> snapshot = new ArrayList<>();
        if (!isEmpty()) {
            preorderSubtree(root(), snapshot);
        }
        return snapshot;
    }

    /**
     * Adds positions of the subtree rooted at Position p to the given
     * snapshot using a postorder traversal.
     */
    private void postorderSubtree(Position<E> p, List<Position<E>> snapshot) {
        // (b) postorder: visit children, then visit node
        for (Position<E> c : children(p)) {
            postorderSubtree(c, snapshot);
        }
        snapshot.add(p);
    }

    /**
     * Returns an iterable collection of the tree's positions in postorder.
     */
    public Iterable<Position<E>> postorder() {
        List<Position<E>> snapshot = new ArrayList<>();
        if (!isEmpty())
            postorderSubtree(root(), snapshot);
        return snapshot;
    }

    /**
     * Returns an iterable collection of the tree's positions in breadth-first order.
     */
    public Iterable<Position<E>> breadthfirst() {
        // (b) BFS traversal using a queue
        List<Position<E>> snapshot = new ArrayList<>();
        if (isEmpty()) return snapshot;

        ArrayDeque<Position<E>> q = new ArrayDeque<>();
        q.add(root());

        while (!q.isEmpty()) {
            Position<E> p = q.remove();
            snapshot.add(p);
            for (Position<E> c : children(p)) {
                q.add(c);
            }
        }
        return snapshot;
    }

    /**
     * (i) Computes height and diameter together for subtree rooted at p.
     */
    protected HDPair diameterHelper(Position<E> p) {

        // Base case: null subtree (should not normally happen in our tree,
        // but included for safety)
        if (p == null) {
            return new HDPair(-1, 0);
        }

        int maxHeight = -1;
        int maxDiameter = 0;

        int firstMax = -1;   // largest child height
        int secondMax = -1;  // second largest child height

        // Process all children
        for (Position<E> c : children(p)) {

            HDPair child = diameterHelper(c);

            // Update diameter
            maxDiameter = Math.max(maxDiameter, child.diameter);

            // Track two largest heights
            if (child.height > firstMax) {
                secondMax = firstMax;
                firstMax = child.height;
            } else if (child.height > secondMax) {
                secondMax = child.height;
            }

            maxHeight = Math.max(maxHeight, child.height);
        }

        // Height of this node
        int height = maxHeight + 1;

        // Diameter through this node
        int throughRoot = firstMax + secondMax + 2;

        int diameter = Math.max(maxDiameter, throughRoot);

        return new HDPair(height, diameter);
    }

    /**
     * (i) Returns the diameter of the tree.
     */
    public int diameter() {

        if (isEmpty()) return 0;

        HDPair result = diameterHelper(root());

        return result.diameter;
    }

}
