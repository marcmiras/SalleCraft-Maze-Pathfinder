import edu.salle.url.maze.business.enums.Cell;
import edu.salle.url.maze.business.enums.Direction;

import java.util.*;

public class Solution implements Comparable<Solution>{
    private Cell[][] maze;
    private List<Direction> path;
    private int posX;
    private int posY;
    private int finalX;
    private int finalY;
    private boolean[][] stepped;
    private boolean finished;
    private int level;
    private int dist;
    //private LinkedList<Direction> q = new LinkedList<>();

    public boolean[][] getStepped() {
        return stepped;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getFinalX() {
        return finalX;
    }

    public void setFinalX(int finalX) {
        this.finalX = finalX;
    }

    public int getFinalY() {
        return finalY;
    }

    public void setFinalY(int finalY) {
        this.finalY = finalY;
    }

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public Solution(int finalX, int finalY) {
        this.maze = new Cell[25][25];
        this.path = new ArrayList<>();
        this.posX = 0;
        this.posY = 0;
        this.finalX = finalX;
        this.finalY = finalY;
        this.stepped = new boolean[25][25];
        this.finished = false;
        this.level = 1;
        this.dist = 0;
    }

    public Solution(Solution parent) {
        this.maze = parent.maze;
        this.path = parent.path;
        this.posX = parent.posX;
        this.posY = parent.posY;
        this.finalX = parent.finalX;
        this.finalY = parent.finalY;
        this.stepped = parent.stepped;
        this.finished = parent.finished;
        this.level = parent.level;
        this.dist = parent.dist;
    }

    public int getCost() {
        return Math.abs(this.finalX - this.posX) + Math.abs(this.finalY - this.posY);
    }

    public List<Solution> expand(Cell[][] maze, boolean[][] stepped, int row, int col, int dist, boolean finished) {
        List<Solution> children = new ArrayList<>();

        this.posX = row;
        this.posY = col;

        if (maze[row][col] == Cell.EXIT) {
            finished = true;
        }

        // UP
        if (maze[row+1][col] == Cell.EMPTY
                && !stepped[row+1][col]) {
            dist++;
            this.level++;
            row++;
            stepped[row][col] = true;
            path.add(Direction.UP);

            // Create child
            Solution child = new Solution(this);
            children.add(child);

            // Backtrack
            dist--;
            this.level--;
            row--;
            stepped[row][col] = false;
            path.removeLast();
        }

        // DOWN
        if (maze[row-1][col] == Cell.EMPTY
                && !stepped[row-1][col]) {
            dist++;
            this.level++;
            row--;
            stepped[row][col] = true;
            path.add(Direction.DOWN);

            // Create child
            Solution child = new Solution(this);
            children.add(child);

            // Backtrack
            dist--;
            this.level--;
            row++;
            stepped[row][col] = false;
            path.removeLast();
        }

        // RIGHT
        if (maze[row][col+1] == Cell.EMPTY
                && !stepped[row][col+1]) {
            dist++;
            this.level++;
            col++;
            stepped[row][col] = true;
            path.add(Direction.RIGHT);

            // Create child
            Solution child = new Solution(this);
            children.add(child);

            // Backtrack
            dist--;
            this.level--;
            col--;
            stepped[row][col] = false;
            path.removeLast();
        }

        // LEFT
        if (maze[row][col-1] == Cell.EMPTY
                && !stepped[row][col-1]) {
            dist++;
            this.level++;
            col--;
            stepped[row][col] = true;
            path.add(Direction.LEFT);

            // Create child
            Solution child = new Solution(this);
            children.add(child);

            // Backtrack
            dist--;
            this.level--;
            col++;
            stepped[row][col] = false;
            path.removeLast();
        }

        return children;
    }


    public List<Direction> solveMaze(Cell[][] maze, boolean[][] stepped, int row, int col, int dist, boolean finished) {

        int best = Integer.MAX_VALUE;
        List<Direction> shortestPath = new ArrayList<>();

        this.finalX = row;
        this.finalY = col;

        PriorityQueue<Solution> queue = new PriorityQueue<>();
        Solution first = new Solution(finalX, finalY);
        queue.offer(first);

        int bestMH = first.getCost();

        while(!queue.isEmpty()) {
            Solution sol = queue.poll();
            List<Solution> children = sol.expand(maze, stepped, row, col, dist, finished);

            for (Solution child : children) {

                System.out.println();
                if (child.finished) {
                    if (child.dist < best) {
                        best = child.dist;
                        shortestPath = child.path;
                    }
                } else {
                    if (child.getCost() < bestMH) {
                        queue.offer(child);
                    }
                }
            }
        }

        return shortestPath;
    }

    @Override
    public int compareTo(Solution o) {
        return this.getCost() - o.getCost();
    }
}
