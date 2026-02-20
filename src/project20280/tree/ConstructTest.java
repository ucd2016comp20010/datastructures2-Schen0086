package project20280.tree;

public class ConstructTest {
    public static void main(String[] args) {

        Integer[] inorder = {
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17,
                18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30
        };

        Integer[] preorder = {
                18, 2, 1, 14, 13, 12, 4, 3, 9, 6, 5, 8, 7, 10, 11, 15, 16,
                17, 28, 23, 19, 22, 20, 21, 24, 27, 26, 25, 29, 30
        };

        LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<>();
        bt.construct(inorder, preorder);

        System.out.println(bt.toBinaryTreeString());
    }
}
