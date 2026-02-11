package project20280.tree;

import project20280.interfaces.BinaryTree;
import project20280.interfaces.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract base class providing some functionality of the BinaryTree interface.
 *
 * The following five methods remain abstract, and must be implemented
 * by a concrete subclass: size, root, parent, left, right.
 */
public abstract class AbstractBinaryTree<E> extends AbstractTree<E>
        implements BinaryTree<E> {

    /**
     * Returns the Position of p's sibling (or null if no sibling exists).
     */
    @Override
    public Position<E> sibling(Position<E> p) {
        // (b) sibling is the other child of p's parent
        Position<E> parent = parent(p);
        if (parent == null) return null;

        Position<E> leftChild = left(parent);
        if (leftChild == p) return right(parent);
        else return leftChild;
    }

    /**
     * Returns the number of children of Position p.
     */
    @Override
    public int numChildren(Position<E> p) {
        // (b) binary tree: 0, 1, or 2 children
        int count = 0;
        if (left(p) != null) count++;
        if (right(p) != null) count++;
        return count;
    }

    /**
     * Adds positions of the subtree rooted at Position p to the given
     * snapshot using an inorder traversal.
     */
    private void inorderSubtree(Position<E> p, List<Position<E>> snapshot) {
        // (f) inorder: left, node, right
        if (left(p) != null) {
            inorderSubtree(left(p), snapshot);
        }
        snapshot.add(p);
        if (right(p) != null) {
            inorderSubtree(right(p), snapshot);
        }
    }

    /**
     * Returns an iterable collection of positions of the tree, reported in inorder.
     */
    public Iterable<Position<E>> inorder() {
        List<Position<E>> snapshot = new ArrayList<>();
        if (!isEmpty())
            inorderSubtree(root(), snapshot);
        return snapshot;
    }

    /**
     * Returns an iterable collection of the positions of the tree using inorder traversal.
     */
    @Override
    public Iterable<Position<E>> positions() {
        // (f) positions() must call inorder() for this assignment
        return inorder();
    }
}
