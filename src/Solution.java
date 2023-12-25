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

        public boolean isFinished() {
            return finished;
        }

        public void setFinished(boolean finished) {
            this.finished = finished;
        }

        public Solution(int finalX, int finalY) {
            this.maze = Problem.CellReader();
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
            this.maze = new Cell[25][25];
            for (int i = 0; i < 25; i++) {
                System.arraycopy(parent.maze[i], 0, this.maze[i], 0, 25);
            }
            this.path = new ArrayList<>(parent.path);
            this.posX = parent.posX;
            this.posY = parent.posY;
            this.finalX = parent.finalX;
            this.finalY = parent.finalY;
            this.stepped = new boolean[25][25];
            for (int i = 0; i < 25; i++) {
                System.arraycopy(parent.stepped[i], 0, this.stepped[i], 0, 25);
            }
            this.finished = parent.finished;
            this.level = parent.level;
            this.dist = parent.dist;
        }

        private int getCost() {
            return Math.abs(this.finalX - this.posX) + Math.abs(this.finalY - this.posY);
        }

        private boolean isValid(int x, int y) {
            return x >= 0 && y >= 0 && x < 25 && y < 25;
        }

        private List<Solution> expand() {
            List<Solution> children = new ArrayList<>();

            int nextX, nextY, lastX, lastY, x, y;
            x = this.posX;
            y = this.posY;
            nextX = this.posX+1;
            nextY = this.posY+1;
            lastX = this.posX-1;
            lastY = this.posY-1;

            // DOWN
            if (isValid(nextX, y) && maze[nextX][y] != Cell.WALL && !stepped[nextX][y]) {//System.out.print("DOWN ");

               // Create child
               Solution child = new Solution(this);

               child.dist++;
               child.level++;
               child.stepped[x][y] = true;
               child.path.add(Direction.DOWN);
               child.posX++;

               if (maze[nextX][y] == Cell.EXIT ||
                       checkDeadEnd(child.maze, child.posX-1, child.posY-1,
                               child.posX+1, child.posY+1, child.posX, child.posY)) {
                   child.finished = true;
               }

               children.add(child);
            }

            // UP
            if (isValid(lastX, y) && maze[lastX][y] != Cell.WALL && !stepped[lastX][y]) {
                //System.out.print("UP ");

                // Create child
                Solution child = new Solution(this);

                child.dist++;
                child.level++;
                child.stepped[x][y] = true;
                child.path.add(Direction.UP);
                child.posX--;

                if (maze[lastX][y] == Cell.EXIT ||
                        checkDeadEnd(child.maze, child.posX-1, child.posY-1,
                                child.posX+1, child.posY+1, child.posX, child.posY)) {
                    child.finished = true;
                }

                children.add(child);
            }

            // RIGHT
            if (isValid(x, nextY) && maze[x][nextY] != Cell.WALL && !stepped[x][nextY]) {
                //System.out.print("RIGHT ");

                // Create child
                Solution child = new Solution(this);

                child.dist++;
                child.level++;
                child.stepped[x][y] = true;
                child.path.add(Direction.RIGHT);
                child.posY++;

                if (maze[x][nextY] == Cell.EXIT ||
                        checkDeadEnd(child.maze, child.posX-1, child.posY-1,
                                child.posX+1, child.posY+1, child.posX, child.posY)) {
                    child.finished = true;
                }

                children.add(child);
            }

            // LEFT
            if (isValid(x, lastY) && maze[x][lastY] != Cell.WALL && !stepped[x][lastY]) {
                //System.out.print("LEFT ");

                // Create child
                Solution child = new Solution(this);

                child.dist++;
                child.level++;
                child.stepped[x][y] = true;
                child.path.add(Direction.LEFT);
                child.posY--;

                if (maze[x][lastY] == Cell.EXIT ||
                        checkDeadEnd(child.maze, child.posX-1, child.posY-1,
                                child.posX+1, child.posY+1, child.posX, child.posY)) {
                    child.finished = true;
                }

                children.add(child);
            }

            // If no valid moves, backtrack (mark the current state as finished)
            if (children.isEmpty()) {
                this.finished = true;
            }

            return children;
        }

        private boolean checkDeadEnd(Cell[][] maze, int lastX, int lastY, int nextX, int nextY, int x, int y) {
            return maze[nextX][y] == Cell.WALL && maze[lastX][y] == Cell.WALL
                    && maze[x][nextY] == Cell.WALL && maze[x][lastY] == Cell.WALL;
        }

        public List<Direction> solveMaze(int startX, int startY, int endX, int endY) {

            int best = Integer.MAX_VALUE;
            List<Direction> shortestPath = new ArrayList<>();
            List<Solution> listSolutions = new ArrayList<>();

            this.finalX = endX;
            this.finalY = endY;

            PriorityQueue<Solution> queue = new PriorityQueue<>();
            Solution first = new Solution(finalX, finalY);
            queue.offer(first);

            first.setPosX(startX);
            first.setPosY(startY);

            int bestMH = first.getCost();

            while(!queue.isEmpty()) {
                int stepsDone = Integer.MAX_VALUE;
                Solution sol = queue.poll();
                List<Solution> children = sol.expand();

                for (Solution child : children) {
                    if (child.isFinished()) {
                        listSolutions.add(child);
                    } else {
                        int approxCost = child.getCost();
                        int stepsTaken = child.getDist();

                        if ((approxCost <= bestMH) && (stepsTaken <= stepsDone)) {
                            stepsDone = child.getDist();
                            bestMH = approxCost;
                            queue.offer(child);
                        }
                    }
                }
            }

            for (Solution sol : listSolutions) {
                if (sol.getDist() < best) {
                    best = sol.getDist();
                    shortestPath = sol.path;
                }
            }

            return shortestPath;
        }

        @Override
        public int compareTo(Solution o) {
            return this.getDist() - o.getDist();
        }
    }
