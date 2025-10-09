import java.util.*;

public class AStarSearch {

    // Represents a node in the grid and in the priority queue
    static class Node implements Comparable<Node> {
        int r, c;
        int g; // Cost from start to this node
        int h; // Heuristic cost from this node to end
        int f; // f = g + h
        Node parent;

        public Node(int r, int c) {
            this.r = r;
            this.c = c;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.f, other.f);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Node node = (Node) obj;
            return r == node.r && c == node.c;
        }

        @Override
        public int hashCode() {
            return Objects.hash(r, c);
        }
    }

    private static final int[][] DIRS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    // Manhattan Distance Heuristic (admissible for grid movement)
    private static int heuristic(int r1, int c1, int r2, int c2) {
        return Math.abs(r1 - r2) + Math.abs(c1 - c2);
    }

    public static List<Node> findPath(char[][] grid, Node start, Node end) {
        int rows = grid.length;
        int cols = grid[0].length;

        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Set<Node> closedSet = new HashSet<>();

        start.g = 0;
        start.h = heuristic(start.r, start.c, end.r, end.c);
        start.f = start.g + start.h;
        openSet.add(start);

        int nodesExpanded = 0;

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            nodesExpanded++;

            if (current.equals(end)) {
                System.out.println("A* expanded " + nodesExpanded + " nodes.");
                return reconstructPath(current);
            }

            closedSet.add(current);

            for (int[] dir : DIRS) {
                int newR = current.r + dir[0];
                int newC = current.c + dir[1];

                if (newR < 0 || newR >= rows || newC < 0 || newC >= cols || grid[newR][newC] == '#') {
                    continue;
                }

                Node neighbor = new Node(newR, newC);
                if (closedSet.contains(neighbor)) {
                    continue;
                }

                int tentativeG = current.g + 1; // All steps have cost 1

                // Check if neighbor is in open set with a higher g score
                boolean inOpenSet = openSet.contains(neighbor);
                if (!inOpenSet || tentativeG < neighbor.g) {
                    neighbor.g = tentativeG;
                    neighbor.h = heuristic(neighbor.r, neighbor.c, end.r, end.c);
                    neighbor.f = neighbor.g + neighbor.h;
                    neighbor.parent = current;

                    if (!inOpenSet) {
                        openSet.add(neighbor);
                    } else {
                        // In Java's PQ, we must re-insert to update priority
                        openSet.remove(neighbor);
                        openSet.add(neighbor);
                    }
                }
            }
        }
        System.out.println("A* expanded " + nodesExpanded + " nodes.");
        return Collections.emptyList(); // No path found
    }

    private static List<Node> reconstructPath(Node current) {
        LinkedList<Node> path = new LinkedList<>();
        while (current != null) {
            path.addFirst(current);
            current = current.parent;
        }
        return path;
    }

    public static void main(String[] args) {
        char[][] grid = {
                {'S', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '#', '#', '#', '#', '#', '#', '.'},
                {'.', '.', '.', '.', '.', '.', '#', '.'},
                {'.', '#', '#', '#', '#', '.', '#', '.'},
                {'.', '#', 'E', '.', '.', '.', '#', '.'},
                {'.', '#', '#', '#', '#', '#', '#', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'}
        };

        Node start = new Node(0, 0);
        Node end = new Node(4, 2);

        List<Node> path = findPath(grid, start, end);
        if (path.isEmpty()) {
            System.out.println("No path found.");
        } else {
            System.out.println("Path found with length " + (path.size() - 1));
        }
    }
}