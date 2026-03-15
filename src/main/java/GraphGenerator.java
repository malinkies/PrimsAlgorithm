import java.io.*;
import java.util.*;

public class GraphGenerator {
    public static void main(String[] args) {
        File testDir = new File("tests");
        if (!testDir.exists()) {
            testDir.mkdirs();  // Создаем директорию
            System.out.println("Создана папка: tests/");
        }

        int minV = 100;
        int maxV = 10000;
        int testCount = 100;

        Random random = new Random(42);

        for (int i = 0; i < testCount; i++) {
            int V = minV + (int) ((double) i / (testCount - 1) * (maxV - minV));

            // кол-во ребер ~ V * 5
            int E = V * 5;

            String filename = String.format("tests/graph_%03d.txt", i); // чтобы красиво пронумеровать

            try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
                pw.println(V + " " + E);

                // Гарантируем связность
                for (int v = 1; v < V; v++) {
                    int w = random.nextInt(100) + 1;
                    pw.println((v - 1) + " " + v + " " + w);
                }

                int added = 0;
                while (added < E - (V - 1)) {
                    int u = random.nextInt(V);
                    int v = random.nextInt(V);
                    if (u != v) {
                        int w = random.nextInt(100) + 1;
                        pw.println(u + " " + v + " " + w);
                        added++;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}