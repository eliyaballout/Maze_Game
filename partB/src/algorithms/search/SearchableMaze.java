package algorithms.search;
import java.util.ArrayList;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;


/**
 * this class represents Object Adapter of the Maze problem.
 * the class Implements ISearchable interface.
 */

public class SearchableMaze implements ISearchable {

    private Maze maze;
    private MazeState startState;
    private MazeState goalState;
    private boolean[][] visited;


    /**
     * Constructor, it initializes the attributes.
     * @param maze: this is the Maze that we will search the solution on it.
     */
    public SearchableMaze(Maze maze) {
        if (maze != null) {
            this.maze = maze;
            String start = maze.getStartPosition().toString();
            String end = maze.getGoalPosition().toString();
            this.startState = new MazeState(null, start, maze.getStartPosition());
            this.goalState = new MazeState(null, end, maze.getGoalPosition());
            this.visited = new boolean[maze.getRowIndex()][maze.getColumnIndex()];

            for (int i = 0; i < maze.getRowIndex(); i++){
                for(int j = 0; j < maze.getColumnIndex(); j++){
                    if(maze.getCellValue(i, j) == 0)
                        this.visited[i][j] = false;
                    else
                        this.visited[i][j] = true;
                }
            }
        }

        else{
            this.maze = null;
            this.startState = null;
            this.goalState = null;
            System.out.println("Can't construct a null maze!");
        }
    }


    /**
     * @return the start state.
     */
    @Override
    public AState getStartState() {
        return this.startState;
    }


    /**
     * @return the goal state.
     */
    @Override
    public AState getGoalState() {
        return this.goalState;
    }


    /**
     * getting all the successors of a given state, by calculating and checking all the directions.
     * @param state: the given state.
     * @return array of all the successors.
     */
    @Override
    public ArrayList<AState> getAllPossibleState(AState state) {
        if (state.cost == -1) {

            state.setCost(0);
            for (int i =0 ; i < this.maze.getRowIndex(); i++) {
                for (int j = 0 ; j < this.maze.getColumnIndex(); j++) {
                    if(this.maze.getCellValue(i, j) == 0)
                        this.visited[i][j] = false;
                    else
                        this.visited[i][j] = true;
                }
            }
        }


        ArrayList<AState> possibleStates = new ArrayList<>();
        Position position;
        MazeState currState;
        if (state != null) {
            MazeState mazestate = (MazeState) state;
            int stateRow = mazestate.getRowIndex();
            int stateCol = mazestate.getColIndex();
            boolean up, right, down, left;
            boolean rightUp, rightDown, leftDown, leftUp;


            if (!this.visited[stateRow][stateCol]) {

                this.visited[stateRow][stateCol] = true;
                up = isValid(mazestate, stateRow - 1, stateCol);
                right = isValid(mazestate, stateRow, stateCol + 1);
                down = isValid(mazestate, stateRow + 1, stateCol);
                left = isValid(mazestate, stateRow, stateCol - 1);

                rightUp = isValid(mazestate, stateRow - 1, stateCol + 1);
                rightDown = isValid(mazestate, stateRow + 1, stateCol + 1);
                leftDown = isValid(mazestate, stateRow + 1, stateCol - 1);
                leftUp = isValid(mazestate, stateRow - 1, stateCol - 1);

                if (up && !this.visited[stateRow - 1][stateCol]) {
                    position = new Position(stateRow - 1, stateCol);
                    currState = new MazeState(state, position.toString(), position);
                    currState.setCost(state.getCost() + 10);
                    possibleStates.add(currState);
                }

                if (right && !this.visited[stateRow][stateCol + 1]) {
                    position = new Position(stateRow, stateCol + 1);
                    currState = new MazeState(state, position.toString(), position);
                    currState.setCost(state.getCost() + 10);
                    possibleStates.add(currState);
                }

                if (down && !this.visited[stateRow + 1][stateCol]) {
                    position = new Position(stateRow + 1, stateCol);
                    currState = new MazeState(state, position.toString(), position);
                    currState.setCost(state.getCost() + 10);
                    possibleStates.add(currState);
                }

                if (left && !this.visited[stateRow][stateCol - 1]) {
                    position = new Position(stateRow, stateCol - 1);
                    currState = new MazeState(state, position.toString(), position);
                    currState.setCost(state.getCost() + 10);
                    possibleStates.add(currState);
                }


                if (rightUp && (right || up) && !this.visited[stateRow - 1][stateCol + 1]) {
                    position = new Position(stateRow - 1, stateCol + 1);
                    currState = new MazeState(state, position.toString(), position);
                    currState.setCost(state.getCost() + 15);
                    possibleStates.add(currState);
                }

                if (rightDown && (right || down) && !this.visited[stateRow + 1][stateCol + 1]) {
                    position = new Position(stateRow + 1, stateCol + 1);
                    currState = new MazeState(state, position.toString(), position);
                    currState.setCost(state.getCost() + 15);
                    possibleStates.add(currState);
                }

                if (leftDown && (left || down) && !this.visited[stateRow + 1][stateCol - 1]) {
                    position = new Position(stateRow + 1, stateCol - 1);
                    currState = new MazeState(state, position.toString(), position);
                    currState.setCost(state.getCost() + 15);
                    possibleStates.add(currState);
                }

                if (leftUp && (left || up) && !this.visited[stateRow - 1][stateCol - 1]) {
                    position = new Position(stateRow - 1, stateCol - 1);
                    currState = new MazeState(state, position.toString(), position);
                    currState.setCost(state.getCost() + 15);
                    possibleStates.add(currState);
                }

            }
        }

        return possibleStates;
    }


    /**
     * private method for checking if a state in [row,col] is a valid successor to the given state.
     * @param state: state that we want to check its valid successors.
     * @param row: row of the successor.
     * @param col: col of the successor.
     * @return a boolean value if this successor is valid or not.
     */
    private boolean isValid(MazeState state, int row, int col){
        int rowSize = this.maze.getRowIndex();
        int colSize = this.maze.getColumnIndex();
        MazeState cameFrom = (MazeState) state.getCameFrom();
        int cameFromRow = -1;
        int cameFromCol = -1;
        int cellValue;

        if (cameFrom != null){
            cameFromRow = cameFrom.getRowIndex();
            cameFromCol = cameFrom.getColIndex();
        }

        if (cameFromRow == row && cameFromCol == col)
            return false;

        else if (row < rowSize && col < colSize && row >= 0 && col >= 0){
            cellValue = this.maze.getCellValue(row, col);
            if (cellValue == 0)
                return true;
        }

        return false;
    }

}
