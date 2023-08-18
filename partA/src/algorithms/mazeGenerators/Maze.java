package algorithms.mazeGenerators;


/**
 * Maze class.
 * this class represents Maze object.
 */

public class Maze {

    private int row;
    private int col;
    private Position startPosition;
    private Position goalPosition;
    private int[][] maze;


    /**
     * Constructor, initializing the attributes and building the Maze object.
     * @param row: number of the rows in the maze.
     * @param col number of the column in the maze.
     */
    public Maze(int row, int col) {
        if (row >= 2 && col >= 2){
            this.row = row;
            this.col = col;
            this.startPosition = new Position(0,0);
            this.goalPosition = new Position(row - 1, col - 1);
            maze = new int[row][col];
            maze[0][0] = 0;
            maze[row - 1][col - 1] = 0;
        }
    }


    /**
     * getter for the start point/position of the maze.
     * @return the start position.
     */
    public Position getStartPosition(){
        return this.startPosition;
    }


    /**
     * getter for the goal point/position of the maze.
     * @return the goal position.
     */
    public Position getGoalPosition() {
        return this.goalPosition;
    }


    /**
     * getter for the rows in the maze.
     * @return number of rows.
     */
    public int getRowIndex(){
        return this.row;
    }


    /**
     * getter for the columns in the maze.
     * @return number of columns.
     */
    public int getColumnIndex(){
        return this.col;
    }


    /**
     * getter for the maze in 2D array.
     * @return 2D array of the maze.
     */
    public int[][] getMaze(){
        return this.maze;
    }


    /**
     * getter for the value at cell [row, col].
     * @param row: number of the row.
     * @param col: number of the column.
     * @return the cell value in [row, col].
     */
    public int getCellValue(int row, int col){
        if (row < 0 || col < 0 || row > this.row - 1 || col > this.col - 1 || this.maze == null)
            return -1;
        return this.maze[row][col];
    }


    /**
     * setter for the start position, takes two coordinates and sets start point at [row, col].
     * @param row: number of the row.
     * @param col: number of the column.
     */
    public void setStartPosition(int row, int col){
        if (row < this.row && col < this.col)
            this.startPosition = new Position(row, col);
        else
            System.out.println("coordinates out of bounds of the maze's size!");
    }


    /**
     * setter for the goal position, takes two coordinates and sets goal point at [row, col].
     * @param row: number of the row.
     * @param col: number of the column.
     */
    public void setGoalPosition(int row, int col){
        if (row < this.row && col < this.col)
            this.goalPosition = new Position(row, col);
        else
            System.out.println("coordinates out of bounds of the maze's size!");
    }


    /**
     * changing or updating the value in [row, col] to 'val'
     * @param row: number of the row.
     * @param col: number of the column.
     * @param val: the new value to set.
     */
    public void changeValue(int row, int col, int val){
        this.maze[row][col] = val;
    }


    /**
     * this method responsible for printing the board maze, including start and goal position.
     * '0' represents a path, '1' represents a wall, 'S' and 'E' represents start and goal position respectively.
     */
    public void print(){
        for (int i = 0; i < this.row; i++){
            for (int j = 0; j < this.col; j++){
                if (i == startPosition.getRowIndex() && j == startPosition.getColumnIndex())
                    System.out.print(" \033[0;31m" + "S" + "\033[0m");

                else if (i == goalPosition.getRowIndex() && j == goalPosition.getColumnIndex())
                    System.out.print(" \033[0;32m" + "E" + "\033[0m");

                else
                    System.out.print(" " + this.maze[i][j]);

                if (j == this.col - 1)
                    System.out.println();
            }
        }
    }



    public void setmat(int[][] mat){
        this.maze = mat;
    }

}
