package algorithms.mazeGenerators;


/**
 * Abstract class AMazeGenerator that implements IMazeGenerator interface.
 * this abstract responsible on generating different kinds of mazes.
 */

public abstract class AMazeGenerator implements IMazeGenerator{


    /**
     * abstract method for generating maze
     * @param row: number of rows in the maze.
     * @param col: number of columns in the maze.
     * @return a Maze.
     */
    @Override
    public abstract Maze generate(int row, int col);


    /**
     * method for measuring the time duration of generating the maze in milliseconds.
     * @param row: number of rows in the maze.
     * @param col: number of columns in the maze.
     * @return long which represents the time duration of generating the maze.
     */
    @Override
    public long measureAlgorithmTimeMillis(int row, int col) {
        long startTime = System.currentTimeMillis();
        generate(row, col);
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

}