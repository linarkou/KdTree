public class Node {
    int[] point;
    Node left = null, right = null;

    public Node(int[] point) {
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

    public boolean isLeaf() {
        return left == null && right == null;
    }
}