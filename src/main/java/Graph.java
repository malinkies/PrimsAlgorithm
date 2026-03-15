import java.util.ArrayList;
import java.util.List;

public class Graph {
    int V;
    List<List<Edge>> adj;

    public Graph(int V) {
        this.V = V;
        this.adj = new ArrayList<>(V);
        for (int i = 0; i < V; i++) adj.add(new ArrayList<>());
    }

    // для ввода данных
    void addEdge(int u, int v, int w) {
        adj.get(u).add(new Edge(v, w));
        adj.get(v).add(new Edge(u, w));
    }

    // для вывода
    int countEdges() {
        int count = 0;
        for (List<Edge> list : adj) count += list.size();
        return count / 2; // неориентированный граф
    }
}