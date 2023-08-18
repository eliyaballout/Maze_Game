package algorithms.search;
import algorithms.mazeGenerators.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;



class BestFirstSearchTest {

    private AMazeGenerator emptyMaze = new EmptyMazeGenerator();
    private AMazeGenerator simpleMaze = new SimpleMazeGenerator();
    private AMazeGenerator myMaze = new MyMazeGenerator();


    @Test
    void solve() {

        /* Empty Maze Test */
        assertTrue(checkSolution(emptyMaze, 0, 0));
        assertTrue(checkSolution(emptyMaze, -10, -89));
        assertTrue(checkSolution(emptyMaze, -5, 100));
        assertTrue(checkSolution(emptyMaze, 100, -95));
        assertTrue(checkSolution(emptyMaze, 35, 30));
        assertTrue(checkSolution(emptyMaze, 1000, 1000));
        assertTrue(checkSolution(emptyMaze, 1050, 1111));


        /* Simple Maze Test */
        assertTrue(checkSolution(simpleMaze, 0, 0));
        assertTrue(checkSolution(simpleMaze, -10, -89));
        assertTrue(checkSolution(simpleMaze, -5, 100));
        assertTrue(checkSolution(simpleMaze, 100, -95));
        assertTrue(checkSolution(simpleMaze, 35, 30));
        assertTrue(checkSolution(simpleMaze, 1000, 1000));
        assertTrue(checkSolution(simpleMaze, 1050, 1111));


        /* My Maze Test( Prim's Algorithm) */
        assertTrue(checkSolution(myMaze, 0, 0));
        assertTrue(checkSolution(myMaze, -10, -89));
        assertTrue(checkSolution(myMaze, -5, 100));
        assertTrue(checkSolution(myMaze, 100, -95));
        assertTrue(checkSolution(myMaze, 35, 30));
        assertTrue(checkSolution(myMaze, 1000, 1000));
        assertTrue(checkSolution(myMaze, 1050, 1111));

    }


    private boolean checkSolution(AMazeGenerator generator, int row, int col){

        Maze maze = generator.generate(row, col);
        SearchableMaze searchable = new SearchableMaze(maze);
        Solution solPath = new BestFirstSearch().solve(searchable);


        ArrayList<AState> path = solPath.getSolutionPath();
        for (int i = 0; i < path.size(); i++) {
            AState state = path.get(i);
            Position pos;

            if (state.getPosition() instanceof Position)
                pos = state.getPosition();

            else {
                System.out.println("Wrong Instance  Expected (Position) - Actual " + state.getPosition().getClass());
                return false;
            }


            if (i == 0 && !(pos.equals(maze.getStartPosition()))) {
                System.out.println("Wrong Start Point !  Expected : " + maze.getStartPosition() + " - Actual: " + pos);
                return false;
            }


            if (maze.getCellValue(pos.getRowIndex(), pos.getColumnIndex()) == 1) {
                System.out.println("CELL IS A WALL !!! Position : " + pos);
                return false;
            }
        }

        Position goalPos = path.get(path.size() - 1).getPosition();
        return goalPos.equals(maze.getGoalPosition());
    }

}