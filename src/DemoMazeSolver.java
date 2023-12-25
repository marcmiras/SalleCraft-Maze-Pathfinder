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

        List<Direction> directions = sol.solveMaze(start_row, start_col, end_row, end_col);
        mazeRenderer.render(cells, directions, 1);

        return directions;
    }
}
