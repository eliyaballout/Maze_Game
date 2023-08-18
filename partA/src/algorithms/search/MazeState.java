package algorithms.search;
import algorithms.mazeGenerators.Position;


/**
 * MazeState class that extends the Abstract AState class.
 * this class represents a state which adjusted to the maze problem.
 */

public class MazeState extends AState{

    private Position position;


    /**
     * Constructor, it gets the previous state, the name and a position and initializes the attributes.
     * @param cameFrom: the previous state of the current state.
     * @param name: the name of the state.
     * @param p: the position of the state.
     */
    public MazeState(AState cameFrom, String name, Position p){
        super(cameFrom, name);
        if (p != null)
            this.position = new Position(p.getRowIndex(), p.getColumnIndex());
        else
            this.position = null;
    }


    /**
     * @param obj: object to compare.
     * @return true if they are the same object.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MazeState)
            return (this.getRowIndex() == ((MazeState) obj).getRowIndex() && this.getColIndex() == ((MazeState) obj).getColIndex());

        return false;
    }


    /**
     * @param obj the object to be compared.
     * @return a number that represents who is bigger.
     */
    @Override
    public int compareTo(Object obj) {
        if (obj instanceof MazeState)
            if (this.getRowIndex() == ((MazeState) obj).getRowIndex() && this.getColIndex() == ((MazeState) obj).getColIndex())
                return 0;

        return -1;
    }


    /**
     * @return the position.
     */
    public Position getPosition(){
        return this.position;
    }


    /**
     * getter for the row of the position.
     * @return the row position.
     */
    public int getRowIndex(){
        if (this.position == null)
            return -1;

        return this.position.getRowIndex();
    }


    /**
     * getter for the column of the position.
     * @return the column position
     */
    public int getColIndex(){
        if (this.position == null)
            return -1;

        return this.position.getColumnIndex();
    }

}
