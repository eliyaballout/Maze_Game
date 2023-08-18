package algorithms.mazeGenerators;


/**
 * IMazeGenerator Interface.
 * this interface defines method for generating mazes.
 */

public interface IMazeGenerator {

    /**
     * abstract method for generating maze.
     * @param row: number of rows in the maze.
     * @param col: number of columns in the maze.
     * @return a maze.
     */
    public Maze generate(int row, int col);

    /**
     * method for measuring the time duration of generating the maze in milliseconds.
     * @param row: number of rows in the maze.
     * @param col: number of columns in the maze.
     * @return long which represents the time duration of generating the maze.
     */
    public long measureAlgorithmTimeMillis(int row, int col);

}
