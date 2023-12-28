import edu.salle.url.maze.Maze;
import edu.salle.url.maze.MazeBuilder;
import edu.salle.url.maze.business.enums.Cell;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("|| Welcome to SalleCraft! ||");
        System.out.println();
        System.out.println("What map do you want to test?");
        System.out.println("1. Cave");
        System.out.println("2. Dungeon");

        int option;
        do {
            System.out.println();
            System.out.print("Please, enter a number between 1 and 2: ");
            option = scanner.nextInt();
        } while (option < 1 || option > 2);

        switch (option) {
            case 1:
                Maze caveMaze = new MazeBuilder()
                        .setMazeColumns(25)
                        .setMazeRows(25)
                        .setSeed(42)
                        .setMazeSolver(new DemoMazeSolver(option))
                        .buildCaveMaze();
                caveMaze.run();
                break;
            case 2:
                Maze dungeonMaze = new MazeBuilder()
                        .setMazeColumns(25)
                        .setMazeRows(25)
                        .setSeed(42)
                        .setMazeSolver(new DemoMazeSolver(option))
                        .buildDungeonMaze();
                dungeonMaze.run();
                break;
        }
    }
}