import edu.salle.url.maze.business.enums.Cell;
import edu.salle.url.maze.business.enums.Direction;

import java.util.LinkedList;
import java.util.List;

public class Solution {
    private Cell[][] config;
    private int level;
    private int steps;
    private LinkedList<Direction> q = new LinkedList<>();
    private List<Direction> optimalPath;
    private List<Direction> shortestPath;

    public Solution(int steps) {
        this.config = new Cell[25][25];
        this.level = 1;
        this.steps = steps;
        this.optimalPath = new LinkedList<>();
        this.shortestPath = new LinkedList<>();
    }

    public Solution(Solution parent) {
        this.config = new Cell[25][25];
        for (int i = 0; i < config.length; i++) {
            System.arraycopy(parent.config[i], 0, this.config[i], 0, config[i].length);
        }
        this.level = parent.level;
        this.steps = parent.steps;
        this.optimalPath = new LinkedList<>(parent.optimalPath);
        this.shortestPath = new LinkedList<>(parent.shortestPath);
    }

    public List<Direction> getOptimalPath() {
        return optimalPath;
    }

    private void printDirections(List<Direction> shortestPath) {
        for (Direction dir : shortestPath) {
            System.out.println(dir);
        }
    }

    public LinkedList<Direction> solveMaze(Cell[][] cells, int start_row, int start_col) {
        boolean[][] stepped = new boolean[config.length][config[0].length];
        branchAndBound(start_row, start_col, cells, stepped);
        printDirections(shortestPath);
        return new LinkedList<>(shortestPath);
    }

    private static int manhattanDist(int M, int N, int X1, int Y1, int X2, int Y2) {
        return Math.abs(X2 - X1) + Math.abs(Y2 - Y1);
    }

    private void branchAndBound(int row, int col, Cell[][] cells, boolean[][] stepped) {
        stepped[row][col] = true;

        int i = row;
        int j = col;


        if (cells[i][j] == Cell.EXIT) {
            return;
        }

        // UP
        if (cells[i][j + 1] == Cell.EMPTY && !stepped[i][j + 1]) {
            q.addLast(Direction.UP);
            branchAndBound(i, j + 1, cells, stepped);
            q.removeLast();
        }

        // DOWN
        if (cells[i][j - 1] == Cell.EMPTY && !stepped[i][j - 1]) {
            q.addLast(Direction.DOWN);
            branchAndBound(i, j - 1, cells, stepped);
            q.removeLast();
        }

        // LEFT
        if (cells[i - 1][j] == Cell.EMPTY && !stepped[i - 1][j]) {
            q.addLast(Direction.LEFT);
            branchAndBound(i - 1, j, cells, stepped);
            q.removeLast();
        }

        // RIGHT
        if (cells[i + 1][j] == Cell.EMPTY && !stepped[i + 1][j]) {
            q.addLast(Direction.RIGHT);
            branchAndBound(i + 1, j, cells, stepped);
            q.removeLast();
        }

        stepped[row][col] = false;
    }
}
