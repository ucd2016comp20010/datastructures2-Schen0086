package project20280.tree;

public class HeightExperiment {

    public static void main(String[] args) {

        Integer[] arr = new Integer[] {
                1,
                2,3,
                4,5,6,7,
                8,9,10,11,12,13,14,15,
                16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,
                null,null,null,35
        };

        LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<>();
        bt.createLevelOrder(arr);

        // height is 5 because the deepest node is at depth 5
        System.out.println("Height = " + bt.height());

        // call count equals number of nodes because each node’s height is computed once and null children aren’t recursed into
        System.out.println("Recursive calls = " + bt.getHeightCallCount());

        // diameter is 9 because the longest path is from node 35 (depth 5) to node 31 (depth 4),
        // passing through the root, so the path length is 5 + 4 = 9 edges
        // if wanted in number of nodes, then: length = edges + 1 = 10 nodes
        System.out.println("Diameter = " + bt.diameter());


    }
}
