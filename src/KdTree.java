public class KdTree {
    int dim = 2;
    Node root = null;

    public KdTree(int dim) {
        this.dim = dim;
    }

    public class Node {
        int[] point;
        Node left = null, right = null;

        public Node(int[] point) {
            if (point.length == dim)
                this.point = point;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof Node))
                return false;
            Node t = (Node)obj;
            if (t.point.length != this.point.length)
                return false;
            for (int i = 0; i < this.point.length; ++i) {
                if (t.point[i] != this.point[i])
                    return false;
            }
            return true;
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

    public boolean search(Node t) {
        if (dim != t.point.length)
            return false;
        int currDim = 0;
        Node currNode = root;
        while (currNode != null) {
            if (currNode.equals(t))
                return true;
            if (currNode.point[currDim] <= t.point[currDim])
                currNode = currNode.left;
            else
                currNode = currNode.right;
            currDim = (currDim + 1) % dim;
        }
        return false;
    }

    public static void main(String[] args) {

    }
}
