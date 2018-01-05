public class KdTree {
    int dim = 2;
    Node root = null;

    public KdTree(int dim) {
        this.dim = dim;
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
        Node currRoot = root;
        while (currRoot != null) {
            if (currRoot.equals(t))
                return true;
            if (t.point[currDim] <= currRoot.point[currDim])
                currRoot = currRoot.left;
            else
                currRoot = currRoot.right;
            currDim = (currDim + 1) % dim;
        }
        return false;
    }

    public boolean delete(Node t) {
        if (dim != t.point.length || root == null)
            return false;
        delete(root, t, 0);
        return true;
    }

    private Node delete(Node currRoot, Node t, int currDim) {
        if (currRoot == null)
            return null;
        currDim = currDim % dim;
        if (t.equals(currRoot)) {
            if (currRoot.right != null) {
                Node rightMin = findMinimum(currRoot.right, currDim, currDim + 1);
                swap(rightMin, currRoot);
                currRoot.right = delete(currRoot.right, rightMin, currDim + 1);
            } else if (currRoot.left != null) {
                Node leftMin = findMinimum(currRoot.left, currDim, currDim + 1);
                swap(leftMin, currRoot);
                currRoot.right = delete(currRoot.left, leftMin, currDim + 1);
            } else {
                return null;
            }
            return currRoot;
        }
        if (t.point[currDim] <= currRoot.point[currDim]) {
            currRoot.left = delete(currRoot.left, t, currDim + 1);
        } else {
            currRoot.right = delete(currRoot.right, t, currDim + 1);
        }
        return currRoot;
    }

    private Node findMinimum(Node root, int targetDim, int currDim) {
        if (root.left == null) {
            if (root.right == null || currDim == targetDim)
                return root;
            // else
            return findMinimum(root.right, targetDim, (currDim + 1) % dim);
        } else {
            Node leftMin = findMinimum(root.left, targetDim, (currDim + 1) % dim);
            if (root.right == null || currDim == targetDim)
                return leftMin;
            // else
            Node rightMin = findMinimum(root.right, targetDim, (currDim + 1) % dim);
            if (rightMin.point[targetDim] < leftMin.point[targetDim])
                return rightMin;
            else
                return leftMin;
        }
    }

    private void swap(Node t1, Node t2) {
        int[] t1point = t1.point;
        t1.point = t2.point;
        t2.point= t1point;
    }
}
