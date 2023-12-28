import edu.salle.url.maze.business.MazeSolver;
import edu.salle.url.maze.business.enums.Cell;
import edu.salle.url.maze.business.enums.Direction;
import edu.salle.url.maze.presentation.MazeRenderer;

import java.util.*;

public class DemoMazeSolver implements MazeSolver {
    private static int option;
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

    public DemoMazeSolver(int option) {
        DemoMazeSolver.option = option;
    }

    @Override
    public List<Direction> solve(Cell[][] cells, MazeRenderer mazeRenderer) {
        cells = Problem.CellReader(option);

        int[] startPos = findPos(cells, Cell.START);
        int[] endPos = findPos(cells, Cell.EXIT);

        Solution sol = new Solution(endPos[0], endPos[1], cells);

        List<Direction> directions = sol.solveMaze(startPos[0], startPos[1]);
        mazeRenderer.render(cells, directions, 1);

        return directions;
    }
}
