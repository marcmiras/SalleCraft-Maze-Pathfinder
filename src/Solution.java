    import edu.salle.url.maze.business.enums.Cell;
    import edu.salle.url.maze.business.enums.Direction;

    import java.util.*;

    public class Solution {
        private Cell[][] maze;
        private List<Direction> path;
        private int posX;
        private int posY;
        private static int finalX;
        private static int finalY;
        private boolean finished;
        private int dist;

        public void setPosX(int posX) {
            this.posX = posX;
        }

        public void setPosY(int posY) {
            this.posY = posY;
        }

        public int getDist() {
            return dist;
        }

        public boolean isFinished() {
            return finished;
        }

        public Solution(int finalX, int finalY, Cell[][] maze) {
            this.maze = maze;
            this.path = new ArrayList<>();
            this.posX = 0;
            this.posY = 0;
            Solution.finalX = finalX;
            Solution.finalY = finalY;
            this.finished = false;
            this.dist = 0;
        }

        public Solution(Solution parent) {
            this.maze = new Cell[25][25];
            for (int i = 0; i < 25; i++) {
                System.arraycopy(parent.maze[i], 0, this.maze[i], 0, 25);
            }
            this.path = new ArrayList<>(parent.path);
            this.posX = parent.posX;
            this.posY = parent.posY;
            this.finished = parent.finished;
            this.dist = parent.dist;
        }

        private int getCost() {
            int heuristicCost = Math.abs(finalX - this.posX) + Math.abs(finalY - this.posY);
            return this.dist + heuristicCost;
        }

        private boolean isValid(int x, int y) {
            return x >= 0 && y >= 0 && x < 25 && y < 25;
        }

        private List<Solution> expand() {
            List<Solution> children = new ArrayList<>();

            int x = this.posX;
            int y = this.posY;
            int nextX = this.posX+1;
            int nextY = this.posY+1;
            int lastX = this.posX-1;
            int lastY = this.posY-1;

            // DOWN
            if (isValid(nextX, y) && maze[nextX][y] != Cell.WALL) {
               // Create child
               Solution child = new Solution(this);

               child.dist++;
               child.maze[x][y] = Cell.WALL;
               child.path.add(Direction.DOWN);
               child.posX++;

               if (maze[nextX][y] == Cell.EXIT) {
                   child.finished = true;
               }

               children.add(child);
            }

            // UP
            if (isValid(lastX, y) && maze[lastX][y] != Cell.WALL) {
                // Create child
                Solution child = new Solution(this);

                child.dist++;
                child.maze[x][y] = Cell.WALL;
                child.path.add(Direction.UP);
                child.posX--;

                if (maze[lastX][y] == Cell.EXIT) {
                    child.finished = true;
                }

                children.add(child);
            }

            // RIGHT
            if (isValid(x, nextY) && maze[x][nextY] != Cell.WALL) {
                // Create child
                Solution child = new Solution(this);

                child.dist++;
                child.maze[x][y] = Cell.WALL;
                child.path.add(Direction.RIGHT);
                child.posY++;

                if (maze[x][nextY] == Cell.EXIT) {
                    child.finished = true;
                }

                children.add(child);
            }

            // LEFT
            if (isValid(x, lastY) && maze[x][lastY] != Cell.WALL) {
                // Create child
                Solution child = new Solution(this);

                child.dist++;
                child.maze[x][y] = Cell.WALL;
                child.path.add(Direction.LEFT);
                child.posY--;

                if (maze[x][lastY] == Cell.EXIT) {
                    child.finished = true;
                }

                children.add(child);
            }

            return children;
        }

        public List<Direction> solveMaze(int startX, int startY) {

            int bestCost = Integer.MAX_VALUE;
            List<Direction> shortestPath = new ArrayList<>();

            PriorityQueue<Solution> queue = new PriorityQueue<>(Comparator.comparingInt(Solution::getCost));
            Solution first = new Solution(finalX, finalY, this.maze);
            queue.offer(first);

            first.setPosX(startX);
            first.setPosY(startY);

            while (!queue.isEmpty()) {
                Solution sol = queue.poll();
                List<Solution> children = sol.expand();

                for (Solution child : children) {
                    if (!child.isFinished()) {
                        if (child.getCost() < bestCost) {
                            queue.offer(child);
                        }
                    } else {
                        if (child.getDist() < bestCost) {
                            bestCost = child.getDist();
                            shortestPath = child.path;
                        }
                    }
                }
            }

            return shortestPath;
        }

    }
