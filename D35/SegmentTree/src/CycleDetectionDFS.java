import java.util.ArrayList;
import java.util.List;

public class CycleDetectionDFS {

    private enum State { UNVISITED, VISITING, VISITED }

    public static boolean hasCycle(List<List<Integer>> adj) {
        int V = adj.size();
        State[] states = new State[V];
        for (int i = 0; i < V; i++) {
            states[i] = State.UNVISITED;
        }

        for (int i = 0; i < V; i++) {
            if (states[i] == State.UNVISITED) {
                if (dfs(i, adj, states)) {
                    return true; // Cycle detected
                }
            }
        }
        return false;
    }

    private static boolean dfs(int u, List<List<Integer>> adj, State[] states) {
        states[u] = State.VISITING; // Mark current node as being in the recursion stack

        for (int v : adj.get(u)) {
            if (states[v] == State.VISITING) {
                // We've found a back edge to a node currently in the recursion stack
                return true;
            }
            if (states[v] == State.UNVISITED) {
                if (dfs(v, adj, states)) {
                    return true;
                }
            }
        }

        states[u] = State.VISITED; // Done with this node and all its descendants
        return false;
    }

    public static void main(String[] args) {
        // Graph with a cycle: 0 -> 1 -> 2 -> 0
        int V1 = 3;
        List<List<Integer>> adj1 = new ArrayList<>();
        for (int i = 0; i < V1; i++) adj1.add(new ArrayList<>());
        adj1.get(0).add(1);
        adj1.get(1).add(2);
        adj1.get(2).add(0);
        System.out.println("Graph 1 has cycle: " + hasCycle(adj1)); // Expected: true

        // DAG (no cycle): 0 -> 1, 1 -> 2
        int V2 = 3;
        List<List<Integer>> adj2 = new ArrayList<>();
        for (int i = 0; i < V2; i++) adj2.add(new ArrayList<>());
        adj2.get(0).add(1);
        adj2.get(1).add(2);
        System.out.println("Graph 2 has cycle: " + hasCycle(adj2)); // Expected: false
    }
}