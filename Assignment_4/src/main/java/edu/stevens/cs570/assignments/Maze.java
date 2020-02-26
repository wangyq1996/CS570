package edu.stevens.cs570.assignments;


import java.util.ArrayList;
import java.util.Stack;

/**
 * Class that solves maze problems with backtracking.
 *
 * @author Koffman and Wolfgang
 **/
public class Maze implements GridColors {

    /** The maze */
    private TwoDimGrid maze;

    public Maze(TwoDimGrid m) {
        maze = m;
    }

    /** Wrapper method. */
    public boolean findMazePath() {
        return findMazePath(0, 0); // (0, 0) is the start point.
    }

    /**
     * PROBLEM 1
     * Attempts to find a path through point (x, y).
     *
     * @pre Possible path cells are in BACKGROUND color; barrier cells are in
     *      ABNORMAL color.
     * @post If a path is found, all cells on it are set to the PATH color; all
     *       cells that were visited but are not on the path are in the TEMPORARY
     *       color.
     * @param x
     *            The x-coordinate of current point
     * @param y
     *            The y-coordinate of current point
     * @return If a path through (x, y) is found, true; otherwise, false
     */
    public boolean findMazePath(int x, int y) {
        boolean found = false;
        /**
         * TO BE COMPLETED
         */
        return found;
    }

    // PROBLEM 2
    public ArrayList<ArrayList<PairInt>> findAllMazePaths(int x, int y) {
        ArrayList<ArrayList<PairInt>> result = new ArrayList<>();
        /**
         * TO BE COMPLETED
         */
        return result;
    }

    /**
     * Helper method for PROBLEM 2
     * @pre Possible cells are in ABNORMAL color. Barriers are in BACKGROUND color.
     * @post If a path is found, then the ArrayList result will be modified to
     *       include all possible paths to complete the maze successfully.
     * @param x
     *            x-coordinate of the current point
     * @param y
     *            y-coordinate of the current point
     * @param result
     *            The 2-D ArrayList that contains ArrayLists of PairInts
     *            corresponding to successful paths to complete the maze.
     * @param trace
     *            A stack to keep track of the current path being explored to
     *            determine a successful path.
     *
     */
    private void findMazePathStackBased(int x, int y, ArrayList<ArrayList<PairInt>> result, Stack<PairInt> trace) {
    }

    // PROBLEM 3
    public ArrayList<PairInt> findMazePathMin(int x, int y) {
        ArrayList<PairInt> result = new ArrayList<>();
        /**
         * TO BE COMPLETED
         */
        return result;
    }

    /**
     * Helper method for PROBLEM 3
     * All possible paths are explored, and the one with the shortest length is
     * added to the ArrayList.
     *
     * @param x
     *            current x-coordinate
     * @param y
     *            current y-coordinate
     * @return An ArrayList of PairInts that correspond to the shortest possible
     *         path through the maze.
     */
    private void findMazePathMinHelper(int x, int y, ArrayList<PairInt> result, Stack<PairInt> trace) {
    }

    public void resetTemp() {
        maze.recolor(TEMPORARY, BACKGROUND);
    }

    public void restore() {
        resetTemp();
        maze.recolor(PATH, BACKGROUND);
        maze.recolor(NON_BACKGROUND, BACKGROUND);
    }
}
