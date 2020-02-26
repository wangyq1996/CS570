package assignments;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Class that solves maze problems with backtracking.
 *
 * @author Koffman and Wolfgang
 **/
public class Maze implements GridColors {

    /** The maze */
    private TwoDimGrid maze;
    private int []dx = {0,1,0,-1};
    private int []dy = {1,0,-1,0};

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
        if(inBound(x,y)){
            if(isRed(x,y)){
                if(x == maze.getNRows()-1 && y == maze.getNCols()-1){
                    maze.recolor(maze.getNRows()-1,maze.getNCols()-1,PATH);
                    return true;
                }
                maze.recolor(x,y,PATH);
                if(findMazePath(x+1,y) || findMazePath(x,y+1) || findMazePath(x-1,y) || findMazePath(x,y-1)){
                    return true;
                }
                else{
                    maze.recolor(x,y,TEMPORARY);
                    return false;
                }

            }
        }
        return false;
    }

    private boolean inBound(int x, int y){
        if(x > maze.getNRows()-1 || y > maze.getNCols()-1 || x < 0 || y < 0) return false;
        return true;
    }

    private boolean isRed(int x, int y){
        if(maze.getColor(x,y) == NON_BACKGROUND) return true;
        return false;
    }
    // PROBLEM 2
    public ArrayList<ArrayList<PairInt>> findAllMazePaths(int x, int y) {
        ArrayList<ArrayList<PairInt>> result = new ArrayList<>();
        Stack<PairInt> trace = new Stack<>();
        findMazePathStackBased(x,y,result,trace);
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
        if(!inBound(x,y))return;
        if(!isRed(x,y))return;
        if(x == maze.getNRows()-1 && y == maze.getNCols()-1){
            maze.recolor(x,y,PATH);
            trace.push(new PairInt(x,y));
            result.add(new ArrayList<>(trace));
            maze.recolor(x,y,NON_BACKGROUND);
            return;
        }
        maze.recolor(x,y,PATH);
        trace.push(new PairInt(x,y));
        for(int i=0;i<4;i++){
            int newX = x + dx[i];
            int newY = y + dy[i];
            findMazePathStackBased(newX,newY,result,trace);
        }
        trace.pop();
        maze.recolor(x,y,NON_BACKGROUND);
    }

    // PROBLEM 3
    public ArrayList<PairInt> findMazePathMin(int x, int y) {
        ArrayList<PairInt> result = new ArrayList<>();
        Stack<PairInt> trace = new Stack<>();
        findMazePathMinHelper(x,y,result,trace);
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
        ArrayList<ArrayList<PairInt>> output = findAllMazePaths(x,y);
        ArrayList<PairInt> temp = output.get(0);
        for(int i=0;i<output.size();i++) {
            if(output.get(i).size() < temp .size()){
                temp = output.get(i);
            }
        }
        for(PairInt pair: temp ){
            trace.add(pair);
        }
        while(!trace.isEmpty()){
            result.add(trace.pop());
        }
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
