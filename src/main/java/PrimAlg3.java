import java.io.*;
import java.util.*;

public class PrimAlg3 {
    // класс результата для вывода
    static class Result {
        long timeNs;
        int iterations;
        int mstWeight;

        public Result(long timeNs, int iterations, int mstWeight) {
            this.timeNs = timeNs;
            this.iterations = iterations;
            this.mstWeight = mstWeight;
        }
    }

    // Алгоритм Прима с замерами
    public static Result prim(Graph graph) {
        int V = graph.V;
        boolean[] visited = new boolean[V]; // посещенные вершины
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        long startTime = System.nanoTime(); // замер времени

        int res = 0; // сумма весов ребер
        int iterations = 0; // замер итераций

        // пусть первая вершина нулевая
        visited[0] = true;
        for (Edge e : graph.adj.get(0)) {
            pq.add(new Edge(e.to, e.weight));
            pq.add(new Edge(e.to, e.weight));
        }

        while (!pq.isEmpty()) {
            iterations++; // Подсчёт итераций
            Edge current = pq.poll(); // берем самое маленькое ребро

            if (visited[current.to]) continue; // скип если оно уже посещено

            visited[current.to] = true; // посещаем
            res += current.weight; // добав к сумме

            for (Edge e : graph.adj.get(current.to)) { // добавляем новые возможности
                if (!visited[e.to]) pq.add(new Edge(e.to, e.weight));

            }
        }
        long endTime = System.nanoTime(); // замер
        return new Result(endTime - startTime, iterations, res);
    }

    // читаем что в файлах
    public static Graph readGraphFromFile(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));

        // считаем сколько чего у нас есть
        String[] firstLine = br.readLine().split(" ");
        int V = Integer.parseInt(firstLine[0]);
        int E = Integer.parseInt(firstLine[1]);

        Graph graph = new Graph(V); // создаем граф

        // добавляем ребра в граф
        for (int i = 0; i < E; i++) {
            String[] line = br.readLine().split(" ");
            int u = Integer.parseInt(line[0]);
            int v = Integer.parseInt(line[1]);
            int w = Integer.parseInt(line[2]);
            graph.addEdge(u, v, w);
        }
        br.close();
        return graph;
    }

    // находим файлы
    public static List<String> getGraphFiles(String directory) {
        List<String> files = new ArrayList<>();
        File dir = new File(directory);

        File[] graphFiles = dir.listFiles();

        for (File f : graphFiles) {
            files.add(f.getPath());
        }
        return files;
    }

    // запуск
    public static void main(String[] args) {
        List<String> graphFiles = getGraphFiles("tests"); // Получаем список всех файлов

        // записываем результаты
        try (PrintWriter csvWriter = new PrintWriter(new FileWriter("results.csv"));) {
            csvWriter.println("Vertices;Edges;Time_ms;Iterations;MST_Weight");

            for (String filename : graphFiles) {
                try {
                    Graph graph = readGraphFromFile(filename);
                    Result result = prim(graph);

                    csvWriter.printf("%d;%d;%.2f;%d;%d\n",
                            graph.V,
                            graph.countEdges(),
                            result.timeNs / 1_000_000.0,
                            result.iterations,
                            result.mstWeight);

                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}