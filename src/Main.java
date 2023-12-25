import edu.salle.url.maze.Maze;
import edu.salle.url.maze.MazeBuilder;
import edu.salle.url.maze.business.enums.Cell;

public class Main {
    public static void main(String[] args) {
        Maze maze = new MazeBuilder()
                .setMazeColumns(25)
                .setMazeRows(25)
                .setSeed(42)
                .setMazeSolver(new DemoMazeSolver())
                .buildDungeonMaze();
        maze.run();
    }
}