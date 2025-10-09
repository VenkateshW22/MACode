import java.util.*;

public class GridBFS {

    private static final int[][] DIRS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // R, D, L, U

    record Point(int r, int c) {}

    public static List<Point> findShortestPath(char[][] grid, Point start, Point end) {
        int rows = grid.length;
        if (rows == 0) return Collections.emptyList();
        int cols = grid[0].length;

        if (grid[start.r][start.c] == '#' || grid[end.r][end.c] == '#') {
            return Collections.emptyList(); // Start or end is an obstacle
        }

        Queue<Point> queue = new ArrayDeque<>();
        Map<Point, Point> parentMap = new HashMap<>();
        Set<Point> visited = new HashSet<>();

        queue.offer(start);
        visited.add(start);
        parentMap.put(start, null);

        boolean found = false;
        while (!queue.isEmpty()) {
            Point current = queue.poll();

            if (current.equals(end)) {
                found = true;
                break; // Early exit
            }

            for (int[] dir : DIRS) {
                int newR = current.r + dir[0];
                int newC = current.c + dir[1];
                Point neighbor = new Point(newR, newC);

                if (isValid(newR, newC, rows, cols) && grid[newR][newC] != '#' && !visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                    queue.offer(neighbor);
                }
            }
        }

        if (!found) {
            return Collections.emptyList(); // No path found
        }

        return reconstructPath(end, parentMap);
    }

    private static boolean isValid(int r, int c, int rows, int cols) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }

    private static List<Point> reconstructPath(Point end, Map<Point, Point> parentMap) {
        LinkedList<Point> path = new LinkedList<>();
        Point current = end;
        while (current != null) {
            path.addFirst(current);
            current = parentMap.get(current);
        }
        return path;
    }


    public static void main(String[] args) {
        char[][] grid = {
                {'S', '.', '.', '#', '.', '.'},
                {'.', '#', '.', '.', '.', '.'},
                {'.', '#', 'E', '.', '#', '.'},
                {'.', '.', '.', '.', '#', '.'},
                {'#', '#', '#', '.', '.', '.'}
        };

        Point start = new Point(0, 0);
        Point end = new Point(2, 2);

        System.out.println("Finding shortest path from " + start + " to " + end);
        List<Point> path = findShortestPath(grid, start, end);

        if (path.isEmpty()) {
            System.out.println("No path found.");
        } else {
            System.out.println("Path length: " + (path.size() - 1));
            System.out.println("Path: " + path);
        }
        // Expected Path Length: 8
        // Expected Path: [(0, 0), (0, 1), (0, 2), (1, 2), (1, 3), (2, 3), (2, 2)] -- Oops, my manual trace was wrong.
        // Let's trace it: S(0,0)->(0,1)->(0,2) NO. S(0,0)->(1,0)->(2,0)->(3,0)->(3,1)->(3,2)->(3,3)->(2,3)->(2,2) Path of 8.
        // Expected Path: [Point[r=0, c=0], Point[r=1, c=0], Point[r=2, c=0], Point[r=3, c=0], Point[r=3, c=1], Point[r=3, c=2], Point[r=3, c=3], Point[r=2, c=3], Point[r=2, c=2]]
    }
}
