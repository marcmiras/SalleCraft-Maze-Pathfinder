import edu.salle.url.maze.business.MazeSolver;
import edu.salle.url.maze.business.enums.Cell;
import edu.salle.url.maze.business.enums.Direction;
import edu.salle.url.maze.presentation.MazeRenderer;

import java.util.*;

public class DemoMazeSolver implements MazeSolver {
    private static int option;
    private static int size;
    private int[] findPos(Cell[][] maze, Cell targetCell) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j] == targetCell) {
                    return new int[]{i, j};
                }
            }
        }
        throw new IllegalArgumentException(targetCell + " not found in the maze.");
    }

    public DemoMazeSolver(int option, int size) {
        DemoMazeSolver.option = option;
        DemoMazeSolver.size = size;
    }

    @Override
    public List<Direction> solve(Cell[][] cells, MazeRenderer mazeRenderer) {
        int[] startPos = findPos(cells, Cell.START);
        int[] endPos = findPos(cells, Cell.EXIT);

        Solution sol = new Solution(endPos[0], endPos[1], cells);

        long startTime = System.currentTimeMillis();
        List<Direction> directions = sol.solveMaze(startPos[0], startPos[1]);
        long endTime = System.currentTimeMillis();

        long elapsedTime = endTime - startTime;
        System.out.println("Time taken to solve the maze: " + elapsedTime + " milliseconds");

        mazeRenderer.render(cells, directions, 1);

        return directions;
    }
}
