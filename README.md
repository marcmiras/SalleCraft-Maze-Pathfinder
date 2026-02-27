# SalleCraft — A* Maze Pathfinder

A Java application that procedurally generates Cave and Dungeon mazes and solves them using the **A\* (A-star) search algorithm**. Built as an assignment for the *Advanced Programming and Data Structures* subject at La Salle URL (2024).

## What it does

The program:
1. Prompts the user to choose a maze type (Cave or Dungeon), a grid size, and a random seed.
2. Procedurally generates the maze using the provided `MazeHelper` library.
3. Finds the **shortest path** from the start cell to the exit using A\*.
4. Renders the solution path visually and reports the time taken to solve it.

## Algorithm: A* Search

`Solution.java` implements A\* from scratch. Key design decisions:

- **State representation**: each node in the search tree is a full `Solution` object, carrying its own copy of the maze grid, the path taken so far, and the current position. Visited cells are marked as `WALL` to prevent revisiting without needing a separate visited set.
- **Priority queue**: nodes are ordered by `f(n) = g(n) + h(n)`, where:
  - `g(n)` = steps taken from the start (actual cost).
  - `h(n)` = Manhattan distance to the exit (admissible heuristic).
- **Expansion**: at each step, up to four children are generated (UP, DOWN, LEFT, RIGHT), pruning any that would exceed the current best known solution cost.
- **Termination**: the search ends when the priority queue is empty, returning the shortest path found.

## Project structure

```
src/
├── Main.java            # Entry point: CLI menu, maze construction
├── DemoMazeSolver.java  # MazeSolver implementation: locates START/EXIT, times the solve, triggers render
└── Solution.java        # A* algorithm: state space search, heuristic, path reconstruction

lib/
└── MazeHelper.jar       # Course-provided library: maze generation, rendering, Cell/Direction enums
```

## How to run

Requires **Java 11+** and the `MazeHelper.jar` on the classpath.

**Compile:**
```bash
javac -cp lib/MazeHelper.jar -d out src/*.java
```

**Run:**
```bash
java -cp out:lib/MazeHelper.jar Main
```

Follow the prompts to select a maze type, size, and seed. Use seed `42` to reproduce the example mazes from the assignment.

> On Windows, replace `:` with `;` in the classpath separator.

## Technologies

- **Java** (standard library only: `PriorityQueue`, `ArrayList`, `Scanner`)
- **MazeHelper** (course-provided JAR): maze generation and ASCII rendering
- **IntelliJ IDEA** (development environment)
