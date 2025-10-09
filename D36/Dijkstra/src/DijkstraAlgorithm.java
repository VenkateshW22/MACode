import java.util.*;

public class DijkstraAlgorithm {

    record Node(int vertex, int distance) implements Comparable<Node> {
        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.distance, other.distance);
        }
    }
    record Edge(int to, int weight){}

    public static class Graph{
        private final List<List<Edge>> adj;
        public final int V;

        public Graph(int V) {
            adj = new ArrayList<>();
            this.V =V;
            for(int i = 0; i < V; i++){
                adj.add(new ArrayList<>());
            }
        }

        public void addEdge(int u, int v, int w){
            adj.get(u).add(new Edge(v, w));
        }
    }

    public static class ShortestPathResult{
        public final int[] dist;
        public final int[] parent;

        public ShortestPathResult(int[] dist, int[] parent) {
            this.dist = dist;
            this.parent = parent;
        }
    }

    public static ShortestPathResult dijkstra(Graph g, int startNode){
        int V = g.V;
        int[] dist = new int[V];
        int[] parent = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        dist[startNode] = 0;
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(startNode, 0));

        while (!pq.isEmpty()){
            Node currentNode = pq.poll();
            int u = currentNode.vertex;
            int d = currentNode.distance;
            if (d > dist[u]) continue;
            for (Edge e : g.adj.get(u)){
                int v = e.to;
                int w = e.weight;
                if (dist[u] + w < dist[v]){
                    dist[v] = dist[u] + w;
                    parent[v] = u;
                    pq.add(new Node(v, dist[v]));
                }
            }
        }
        return new ShortestPathResult(dist, parent);
    }

    //helper to reconstruct path
    public static List<Integer> reconstructPath(int target, int[] parent){
        LinkedList<Integer> path = new LinkedList<>();
        if (parent[target] == -1 && target != 0) return Collections.emptyList(); // No path
        for (int at = target; at != -1; at = parent[at]) {
            path.addFirst(at);
        }
        return path;
    }
    public static void main(String[] args) {
        int V = 6;
        Graph g = new Graph(V);
        g.addEdge(0, 1, 4);
        g.addEdge(0, 2, 2);
        g.addEdge(1, 3, 5);
        g.addEdge(2, 1, 1);
        g.addEdge(2, 3, 8);
        g.addEdge(2, 4, 10);
        g.addEdge(3, 4, 2);
        g.addEdge(3, 5, 6);
        g.addEdge(4, 5, 3);

        int startNode = 0;
        ShortestPathResult result = dijkstra(g, startNode);

        System.out.println("Dijkstra's Algorithm from source: " + startNode);
        for (int i = 0; i < V; i++) {
            List<Integer> path = reconstructPath(i, result.parent);
            System.out.printf("Path to %d: (Cost: %s) %s%n",
                    i,
                    (result.dist[i] == Integer.MAX_VALUE ? "INF" : result.dist[i]),
                    path);
        }
    }

}
