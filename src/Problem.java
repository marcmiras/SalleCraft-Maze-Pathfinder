import edu.salle.url.maze.business.enums.Cell;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Problem {
    public static Cell[][] CellReader() {
        String filenameCave = "lib/datasets/cave.txt";
        String filenameDungeon = "lib/datasets/dungeon.txt";
        int i = 0, j = 0;
        Cell[][] cells = new Cell[25][25];
        int start_row, start_col, exit_row, exit_col;

        try (BufferedReader reader = new BufferedReader(new FileReader(filenameCave))){
            for (i = 0; i < 2; i++) {
                reader.readLine();
            }
            i=0;
            String line;
            while ((line = reader.readLine()) != null) {
                for (j = 0; j < line.length(); j++) {
                    if (line.charAt(j) == '#') {
                        cells[i][j] = Cell.WALL;
                    }
                    if (line.charAt(j) == ' ') {
                        cells[i][j] = Cell.EMPTY;
                    }
                    if (line.charAt(j) == 'S') {
                        cells[i][j] = Cell.START;
                    }
                    if (line.charAt(j) == 'E') {
                        cells[i][j] = Cell.EXIT;
                    }
                }
                i++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return cells;
    }
}
