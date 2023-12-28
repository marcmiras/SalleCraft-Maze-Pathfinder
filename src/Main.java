import edu.salle.url.maze.Maze;
import edu.salle.url.maze.MazeBuilder;

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

        int option;
        System.out.println();

        do {
            System.out.print("Please, enter a number between 1 and 2: ");
            option = scanner.nextInt();
        } while (option < 1 || option > 2);

        System.out.print("Please, enter a size number (please, less than 100): ");
        int size = scanner.nextInt();

        System.out.print("Please, enter a seed (set 42 for examples): ");
        int seed = scanner.nextInt();

        switch (option) {
            case 1:
                Maze caveMaze = new MazeBuilder()
                        .setMazeColumns(size)
                        .setMazeRows(size)
                        .setSeed(seed)
                        .setMazeSolver(new DemoMazeSolver())
                        .buildCaveMaze();
                caveMaze.run();
                break;
            case 2:
                Maze dungeonMaze = new MazeBuilder()
                        .setMazeColumns(size)
                        .setMazeRows(size)
                        .setSeed(seed)
                        .setMazeSolver(new DemoMazeSolver())
                        .buildDungeonMaze();
                dungeonMaze.run();
                break;
        }
    }
}