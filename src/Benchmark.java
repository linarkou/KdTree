import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class Benchmark {
    static boolean isWorstCase = false;  //set true for test in worst case
    static int[] dims = new int[]{2,5,10,20,100};
    public static void main(String[] args) {
        isWorstCase = false;
        insertionBenchmark(1000000);
        searchBenchmark(100000);
        deleteBenchmark(100000);
        isWorstCase = true;
        insertionBenchmark(10000);
        searchBenchmark(10000);
        deleteBenchmark(10000);
    }

    public static void insertionBenchmark(int N) {
        KdTree tree;
        Random r = new Random();
        int T = 1000;
        long[][] time = new long[dims.length][N/T+1];
        for (int repeats = 1; repeats <= 100; ++repeats) {
            System.out.println(repeats);
            for (int d = 0; d < dims.length; ++d) {
                tree = new KdTree(dims[d]);
                Node[] nodes = generateRandomNodes(N, d);
                for (int i = 1; i <= N; ++i) {
                    long start = System.nanoTime();
                    tree.insert(nodes[i]);
                    long end = System.nanoTime();
                    if (i % T == 0)
                        time[d][i/T] = ((end - start) + time[d][i/T] * (repeats-1)) / repeats;
                }
            }
        }
        try {
            FileWriter writer = new FileWriter(isWorstCase ? "insertBenchmarkWorstCase.csv" : "insertBenchmark.csv");
            writeToFile(writer, time);
            writer.close();
        } catch (IOException ioex) {
            System.err.println(ioex.getMessage());
        }
    }

    public static void searchBenchmark(int N) {
        Random r = new Random();
        KdTree tree;
        int K;
        int T = 1000;
        long[][] time = new long[dims.length][N/T+1];
        for (int repeats = 1; repeats <= 100; ++repeats) {
            System.out.println(repeats);
            for (int d = 0; d < dims.length; ++d) {
                System.out.println(d);
                tree = new KdTree(dims[d]);
                Node[] nodes = generateRandomNodes(N, d);
                for (int i = 1; i <= N; ++i) {
                    tree.insert(nodes[i]);
                    if (i % T == 0) {
                        long start = System.nanoTime();
                        for (int j = 1; j <= i; ++j)
                            tree.search(nodes[j]);
                        long end = System.nanoTime();
                        time[d][i / T] = ((end - start) + time[d][i / T] * (repeats - 1)) / repeats;
                    }
                }
            }
        }
        try {
            FileWriter writer = new FileWriter(isWorstCase ? "searchBenchmarkWorstCase.csv" : "searchBenchmark.csv" );
            writeToFile(writer, time);
            writer.close();
        } catch (IOException ioex) {
            System.err.println(ioex.getMessage());
        }
    }

    public static void deleteBenchmark(int N) {
        Random r = new Random();
        KdTree tree;
        int K;
        int T = 1000;
        long[][] time = new long[dims.length][N/T+1];
        for (int repeats = 1; repeats <= 100; ++repeats) {
            System.out.println(repeats);
            for (int d = 0; d < dims.length; ++d) {
                System.out.println(d);
                tree = new KdTree(dims[d]);
                Node[] nodes = generateRandomNodes(N, d);
                for (int i = 1; i <= N; ++i) {
                    tree.insert(nodes[i]);
                    if (i % T == 0) {
                        List<Integer> perm = new ArrayList<>(i);
                        for (int j = 0; j < i; ++j)
                            perm.add(j);
                        Collections.shuffle(perm);
                        //int sz = tree.size();
                        long start = System.nanoTime();
                        for (int j = 1; j <= i; ++j) {
                            tree.delete(new Node(nodes[perm.get(j - 1) + 1].point));
                        }
                        long end = System.nanoTime();
                        time[d][i / T] = ((end - start) + time[d][i / T] * (repeats - 1)) / repeats;
                        for (int j = 1; j <= i; ++j)
                            tree.delete(nodes[i]);
                    }
                }
            }
        }
        try {
            FileWriter writer = new FileWriter(isWorstCase ? "deleteBenchmarkWorstCase.csv" : "deleteBenchmark.csv" );
            writeToFile(writer, time);
            writer.close();
        } catch (IOException ioex) {
            System.err.println(ioex.getMessage());
        }
    }

    public static void writeToFile (FileWriter writer, long[][] data) {
        try {
            writer.write("N\\D;");
            for (int j = 0; j < dims.length; ++j)
                writer.write(dims[j]+";");
            writer.write("\r\n");
            for (int i = 0; i < data[0].length; i++) {
                writer.write(i+"");
                writer.write(";");
                for (int j = 0; j < data.length; ++j) {
                    writer.write(String.valueOf(data[j][i]));
                    if (j == data.length-1)
                        writer.write("\r\n");
                    else
                        writer.write(";");
                }
            }
        } catch (IOException ioex) {
            System.err.println(ioex.getMessage());
        }
    }

    private static Node[] generateRandomNodes(int n, int dimension) {
        Random r = new Random();
        Node[] nodes = new Node[n+1];
        for (int i = 1; i <= n; ++i) {
            int[] point = new int[dims[dimension]];
            for (int j = 0; j < dims[dimension]; ++j) {
                if (isWorstCase)
                    point[j] = i;
                else
                    point[j] = r.nextInt();
            }
            nodes[i] = new Node(point);
        }
        return nodes;
    }
}
