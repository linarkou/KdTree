import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class JUnitTest {

    KdTree createTree() {
        KdTree tree = new KdTree(2);
        tree.insert(new Node(new int[]{30, 40}));
        tree.insert(new Node(new int[]{5, 25}));
        tree.insert(new Node(new int[]{70, 70}));
        tree.insert(new Node(new int[]{10, 12}));
        tree.insert(new Node(new int[]{50, 30}));
        tree.insert(new Node(new int[]{35, 45}));
        return tree;
    }

    @Test
    public void insertionToEmtyTreeTest() {
        KdTree tree = new KdTree(2);
        tree.insert(new Node(new int[]{30, 40}));
        Assert.assertNotNull(tree.root);
        Assert.assertArrayEquals(new int[]{30, 40}, tree.root.point);
    }

    @Test
    public void insertionTest() {
        KdTree tree = createTree();
        Assert.assertArrayEquals(new int[]{30, 40}, tree.root.point);
        Assert.assertArrayEquals(new int[]{5, 25}, tree.root.left.point);
        Assert.assertArrayEquals(new int[]{10, 12}, tree.root.left.left.point);
        Assert.assertArrayEquals(new int[]{70, 70}, tree.root.right.point);
        Assert.assertArrayEquals(new int[]{50, 30}, tree.root.right.left.point);
        Assert.assertArrayEquals(new int[]{35, 45}, tree.root.right.left.left.point);
    }

    @Test
    public void deleteAndSearchTest() {
        KdTree tree = createTree();
        Node t = new Node(new int[]{10,20});
        tree.insert(t);
        Assert.assertTrue(tree.search(t));
        tree.delete(t);
        Assert.assertFalse(tree.search(t));
        Node root = new Node(tree.root.point);
        Assert.assertTrue(tree.search(root));
        tree.delete(root);
        Assert.assertFalse(tree.search(root));
    }

    @Test
    public void searchInEmptyTreeTest() {
        int k = 5;
        KdTree tree = new KdTree(k);
        Node t = createRandomNodes(5,1)[0];
        Assert.assertFalse(tree.search(t));
    }

    @Test
    public void deleteFromEmptyTreeTest() {
        int k = 5;
        KdTree tree = new KdTree(k);
        Node t = createRandomNodes(5,1)[0];
        Assert.assertFalse(tree.delete(t));
    }

    KdTree createRandomTree(int k, int n) {
        KdTree tree = new KdTree(k);
        Node[] nodes = createRandomNodes(k,n);
        for (Node t : nodes)
            tree.insert(t);
        return tree;
    }

    KdTree createRandomTree(Node[] nodes) {
        if (nodes.length == 0)
            return null;
        int k = nodes[0].point.length;
        KdTree tree = new KdTree(k);
        for (Node t : nodes)
            tree.insert(t);
        return tree;
    }

    Node[] createRandomNodes(int k, int n) {
        Random r = new Random();
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; ++i) {
            int[] point = new int[k];
            for (int j = 0; j < k; ++j) {
                point[j] = r.nextInt();
            }
            nodes[i] = new Node(point);
        }
        return nodes;
    }

    @Test
    public void searchRandomTest() {
        Random r = new Random();
        int k = 2+r.nextInt(10);
        int n = 1000 + r.nextInt(100000);
        Node[] nodes = createRandomNodes(k,n);
        KdTree tree = createRandomTree(nodes);
        for (Node t : nodes)
            Assert.assertTrue(tree.search(t));
    }

    @Test
    public void deleteAndSearchRandomTest() {
        Random r = new Random();
        int k = 2;//+r.nextInt(10);
        int n = 10;//00 + r.nextInt(100000);
        Node[] nodes = createRandomNodes(k,n);
        KdTree tree = createRandomTree(nodes);
        int sz = nodes.length;
        for (int i = 0; i < n; ++i)
            nodes[i] = new Node(nodes[i].point);
        for (int i = 0; i < n; ++i) {
            Node t = nodes[i];
            Assert.assertTrue(tree.delete(t));
            sz--;
            Assert.assertEquals(sz,tree.size());
            Assert.assertFalse(tree.search(t));
        }
    }
}
