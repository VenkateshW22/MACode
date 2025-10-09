import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Using a record for a concise weighted edge representation
record Edge(int to, int weight) {}

class Graph {
    private final List<List<Edge>> adj;
    private final int V;

    public Graph(int V) {
        this.V = V;
        adj = new ArrayList<>(V);
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v, int weight) {
        // For directed graph
        adj.get(u).add(new Edge(v, weight));
    }

    public void addUndirectedEdge(int u, int v, int weight) {
        addEdge(u, v, weight);
        addEdge(v, u, weight);
    }

    public List<Edge> getNeighbors(int u) {
        return adj.get(u);
    }

    public int getV() {
        return V;
    }

    // Helper for reconstructing path from parent pointers
    public static List<Integer> reconstructPath(int target, int[] parent) {
        List<Integer> path = new ArrayList<>();
        int current = target;
        while (current != -1) {
            path.add(current);
            current = parent[current];
        }
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        System.out.println("--- Graph Representation Demo ---");
        Graph g = new Graph(4);
        g.addUndirectedEdge(0, 1, 10);
        g.addUndirectedEdge(0, 2, 5);
        g.addUndirectedEdge(1, 3, 2);
        g.addUndirectedEdge(2, 3, 8);

        System.out.println("Neighbors of vertex 0:");
        for (Edge edge : g.getNeighbors(0)) {
            System.out.println(" -> " + edge.to() + " (weight " + edge.weight() + ")");
        }

        System.out.println("\n--- Path Reconstruction Demo ---");
        // Example parent array from a hypothetical search from 0 to 3
        // Path: 0 -> 1 -> 3
        int[] parent = new int[4];
        parent[0] = -1; // Source parent is -1
        parent[1] = 0;
        parent[3] = 1;

        List<Integer> path = reconstructPath(3, parent);
        System.out.println("Path from 0 to 3: " + path); // Expected: [0, 1, 3]
    }
}
