import edu.salle.url.maze.business.MazeSolver;
import edu.salle.url.maze.business.enums.Cell;
import edu.salle.url.maze.business.enums.Direction;
import edu.salle.url.maze.presentation.MazeRenderer;

import java.util.*;

public class DemoMazeSolver implements MazeSolver {

    @Override
    public List<Direction> solve(Cell[][] cells, MazeRenderer mazeRenderer) {
        cells = Problem.CellReader();
        int start_row = 0, start_col = 0, end_row = 0, end_col = 0;

        for (int i=0; i<Globals.rows; i++) {
            for (int j=0; j<Globals.cols; j++) {
                if (cells[i][j] == Cell.START) {
                    start_row = i;
                    start_col = j;
                }
                if (cells[i][j] == Cell.EXIT) {
                    end_row = i;
                    end_col = j;
                }
            }
        }

        Solution sol = new Solution(end_row, end_col);

        List<Direction> directions = sol.solveMaze(cells, sol.getStepped(), start_row, start_col, sol.getDist(), false);
        mazeRenderer.render(cells, directions, 1);

        /* import java.util.List;
import java.util.PriorityQueue;

public class Main {

    // Main procedure, where we'll implement the Branch and Bound algorithm, since it's iterative (not recursive)
    // and we don't really need an extra method
    public static void main(String[] args) {
        // Initialize the minimum distance array
        Globals.init();

        // Lowest distance (cost of the best configuration), starts at "infinity" - For optimization purposes
        int best = Integer.MAX_VALUE;

        // Priority Queue we'll use to navigate the solution space in a "smart" way
        // In Java, the Priority Queue class uses the Comparable interface to sort its items
        PriorityQueue<TSPConfig> queue = new PriorityQueue<>();

        // We create the first configuration and enqueue it
        TSPConfig first = new TSPConfig();
        queue.offer(first);

        // We repeat the following process until there are no more configurations to consider
        while(!queue.isEmpty()) {
            // We take the first configuration out of the queue (highest priority / lowest estimated cost)
            TSPConfig config = queue.poll();

            // We expand it, generating its successors
            List<TSPConfig> children = config.expand();

            // For each successor
            for (TSPConfig child : children) {
                // If it's full (all choices have been made)
                if (child.isFull()) {

                    // Optimization process
                    if (child.getCost() < best) {
                        best = child.getCost();

                        // We print it for debugging purposes
                        System.out.println(child);
                        System.out.println("Best one!");
                    }
                } else {
                // If it's not full (more choices have to be made)
                    // PBCBS (depending on the heuristic, we could consider using the estimated cost here)
                    if (child.getCost() < best) {
                        // We add the child to the queue, which will use its estimated cost to determine when to explore it
                        queue.offer(child);
                    }
                    /*
                    else {
                        // We could print it for debugging purposes (be aware that it slows everything down)
                        System.out.println(child);
                        System.out.println("PBCBS");
                    }
                    /
    }
}
        }
                }
                }

        /**/

        return directions;
    }
}
