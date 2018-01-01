public class KdTree {
    int dim = 2;
    Node root = null;

    public class Node {
        int[] point;
        Node left = null, right = null;

        public Node(int[] point) {
            if (point.length == dim)
                this.point = point;
        }
    }

    public boolean isEmpty() {
        return root == null;
    }

    public boolean insert(Node t) {
        if (root == null) {
            root = t;
            return true;
        }
        int currDim = 0;
        Node currRoot = root;
        while (true) {
            if (t.point[currDim] <= currRoot.point[currDim]) {
                if (currRoot.left == null) {
                    currRoot.left = t;
                    break;
                } else
                    currRoot = currRoot.left;
            } else {
                if (currRoot.right == null) {
                    currRoot.right = t;
                    break;
                } else
                    currRoot = currRoot.right;
            }
            currDim = (currDim + 1) % dim;
        }
        return true;
    }

    public static void main(String[] args) {

    }
}
