import org.junit.Assert;
import org.junit.Test;

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
}
