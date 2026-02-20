package project20280.tree;

// WK5 Q4 random tree demonstration:
public class RandomTreeDemo {

    public static void main(String[] args) {

        LinkedBinaryTree<Integer> bt = LinkedBinaryTree.makeRandom(10);

        System.out.println(bt.toBinaryTreeString());
        System.out.println(bt.rootToLeafPaths());
    }
}
