import javafx.util.Pair;

import java.util.*;

public class KdTree {
    int dim = 2;
    Node root = null;

    public KdTree(int dim) {
        this.dim = dim;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        if (root == null)
            return 0;
        int size = 0;
        Queue<Node> q = new ArrayDeque<>();
        q.offer(root);
        while (!q.isEmpty()) {
            Node t = q.poll();
            size++;
            if (t.left != null) q.offer(t.left);
            if (t.right != null) q.offer(t.right);
        }
        return size;
    }

    public boolean insert(Node t) {
        if (root == null) {
            root = t;
            return true;
        }
        int currDim = 0;
        Node currRoot = root;
        while (true) {
            if (t.point[currDim] < currRoot.point[currDim]) {
                if (currRoot.equals(t)) {
                    System.err.println("Node already exist in tree");
                    return false;
                }
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
            if (t.point[currDim] < currRoot.point[currDim])
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
        boolean isExist = this.search(t);
        if (isExist == false)
            return false;
        root = delete(root, t, 0);
        return true;
    }

    private Node delete(Node currRoot, Node t, int currDim) {
        if (currRoot == null)
            return null;
        currDim = currDim % dim;
        if (t.equals(currRoot)) {
            if (currRoot.right != null) {
                Node rightMin = findMinimum(currRoot.right, currDim, currDim + 1);
                currRoot.point = rightMin.point;
                currRoot.right = delete(currRoot.right, rightMin, currDim + 1);
            } else if (currRoot.left != null) {
                Node leftMin = findMinimum(currRoot.left, currDim, currDim + 1);
                currRoot.point = leftMin.point;
                currRoot.right = delete(currRoot.left, leftMin, currDim + 1);
                currRoot.left = null;
            } else {
                return null;
            }
            if (currRoot.left != null && currRoot.left.point[currDim] >= currRoot.point[currDim] ||
                    currRoot.right != null && currRoot.right.point[currDim] < currRoot.point[currDim])
                System.err.println("SOMETHING WRONG");
            return currRoot;
        }
        if (t.point[currDim] < currRoot.point[currDim]) {
            currRoot.left = delete(currRoot.left, t, currDim + 1);
        } else {
            currRoot.right = delete(currRoot.right, t, currDim + 1);
        }
        return currRoot;
    }

    private Node findMinimum(Node root, int targetDim, int currDim) {
        if (root == null)
            return null;
        if (targetDim == currDim) {
            if (root.left == null)
                return root;
            // else
            return findMinimum(root.left, targetDim, (currDim + 1) % dim);
        }
        Node rightMin = findMinimum(root.right, targetDim, (currDim + 1) % dim);
        Node leftMin = findMinimum(root.left, targetDim, (currDim + 1) % dim);
        Node res = root;
        if (rightMin != null && rightMin.point[targetDim] < res.point[targetDim])
            res = rightMin;
        if (leftMin != null && leftMin.point[targetDim] < res.point[targetDim])
            res = leftMin;
        return res;
    }
}
