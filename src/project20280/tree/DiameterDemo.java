package project20280.tree;

public class DiameterDemo {

    public static void main(String[] args) {

        Integer[] inorder = {
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18,
                19, 20, 21, 22
        };

        Integer[] preorder = {
                6, 5, 3, 2, 1, 0, 4, 17, 10, 9, 8, 7, 16, 14, 13, 12, 11, 15, 21,
                20, 19, 18, 22
        };

        LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<>();
        bt.construct(inorder, preorder);

        System.out.println(bt.toBinaryTreeString());

        // The question states the width (diameter in nodes) for this tree is 13
        System.out.println("Diameter (nodes) = " + bt.diameterNodes());
    }
}
