import edu.salle.url.maze.Maze;
import edu.salle.url.maze.MazeBuilder;
import edu.salle.url.maze.business.enums.Cell;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Display menu to user
        System.out.println();
        System.out.println("|| Welcome to SalleCraft! ||");
        System.out.println("What map do you want to test?");
        System.out.println("1. Cave");
        System.out.println("2. Dungeon");

        // To change the size of the maze, just change this variable.
        int size = 25;

        int option;

        do {
            System.out.println();
            System.out.print("Please, enter a number between 1 and 2: ");
            option = scanner.nextInt();
        } while (option < 1 || option > 2);

        System.out.print("Please, enter a seed (set 42 for examples): ");
        int seed = scanner.nextInt();

        switch (option) {
            case 1:
                Maze caveMaze = new MazeBuilder()
                        .setMazeColumns(25)
                        .setMazeRows(25)
                        .setSeed(seed)
                        .setMazeSolver(new DemoMazeSolver(option, size))
                        .buildCaveMaze();
                caveMaze.run();
                break;
            case 2:
                Maze dungeonMaze = new MazeBuilder()
                        .setMazeColumns(25)
                        .setMazeRows(25)
                        .setSeed(seed)
                        .setMazeSolver(new DemoMazeSolver(option, size))
                        .buildDungeonMaze();
                dungeonMaze.run();
                break;
        }
    }
}