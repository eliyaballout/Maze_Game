package algorithms.mazeGenerators;


/**
 * Position class.
 * this class represents a cell coordinates in the maze.
 */

public class Position {

    private int row;
    private int col;


    /**
     * Constructor, initializing the attributes.
     * @param row: row index of the position.
     * @param col: column index of the position.
     */
    public Position(int row, int col){
        this.row = row;
        this.col = col;
    }


    /**
     * getter for the row index of the position.
     * @return the row index.
     */
    public int getRowIndex(){
        return this.row;
    }


    /**
     * getter for the column index of the position.
     * @return the column index.
     */
    public int getColumnIndex(){
        return this.col;
    }


    /**
     * Abstract method.
     * overriding the 'equals' method, and checks if the parameter p is the same position as the current one(this).
     * @param p: Position to compare.
     * @return true if they are the same position.
     */
    public boolean equals(Position p){
        if (p == null || getClass() != p.getClass())
            return false;

        if (this == p)
            return true;

        if (p.getRowIndex() == this.getRowIndex() && p.getColumnIndex() == this.getColumnIndex())
            return true;

        return false;
    }


    /**
     * overriding the 'toString' method.
     * @return string describing the position
     */
    @Override
    public String toString() {
        return "{" + this.row + "," + this.col + "}";
    }

}
