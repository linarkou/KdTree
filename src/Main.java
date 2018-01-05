import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Random r = new Random();
        KdTree tree = new KdTree(2);
        Node t = new Node(new int[]{30,40});
        tree.insert(t);
        tree.insert(new Node(new int[]{5,25}));
        tree.insert(new Node(new int[]{70,70}));
        tree.insert(new Node(new int[]{10,12}));
        tree.insert(new Node(new int[]{50,30}));
        tree.insert(new Node(new int[]{35,45}));
        tree.delete(t);
        System.out.println(tree.search(new Node(new int[]{50,30})));
    }
}
