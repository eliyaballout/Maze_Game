package algorithms.mazeGenerators;
import java.util.Random;


/**
 * SimpleMazeGenerator class that extends AMazeGenerator abstract class.
 * responsible for generating a simple random maze.
 */

public class SimpleMazeGenerator extends AMazeGenerator{

    /**
     * method for generating simple maze, with the use of randomised values in the cell with '1' or '0'
     * @param row: number of rows in the maze.
     * @param col: number of columns in the maze.
     * @return simple random maze with given size of row and column.
     */
    @Override
    public Maze generate(int row, int col) {
        if (row >= 2 && col >= 2){

            Maze simpleMaze = new Maze(row, col);
            int[][] maze = simpleMaze.getMaze();
            Random rand = new Random();

            for (int i = 0; i < row; i++)
                for (int j = 0; j < col; j++)
                    simpleMaze.changeValue(i, j, rand.nextInt(2));

            int startPoint = rand.nextInt(col);
            int goalPoint = rand.nextInt(col);
            maze[0][startPoint] = 0;
            simpleMaze.setStartPosition(0, startPoint);
            simpleMaze.setGoalPosition(row - 1, goalPoint);
            this.createSolution(maze, 0, startPoint, goalPoint);
            return simpleMaze;
        }

        else{
            System.out.println("Cannot construct a simple maze with coordinates that less than 2*2 size");
            System.out.println("We will create a default simple maze in size of 10*10");
            return this.generate(10,10);
        }

    }


    /**
     * this method is responsible for making a path for the maze by marking it with '0'
     * @param maze: 2D array maze board.
     * @param row: row index.
     * @param col: column index.
     * @param finalPoint: the goal position of the maze.
     */
    public void createSolution(int[][] maze, int row, int col, int finalPoint){
        Random rand = new Random();
        maze[maze.length - 1][finalPoint] = 1;
        while (maze[maze.length - 1][finalPoint] == 1){
            switch (rand.nextInt(3)){
                case 0://down
                    if (row + 1 < maze.length){
                        maze[row + 1][col] = 0;
                        row++;
                    }
                    break;

                case 1://right
                    if (col + 1 < maze[row].length){
                        maze[row][col + 1] = 0;
                        col++;
                    }
                    break;

                case 2://left
                    if (col - 1 >= 0){
                        maze[row][col - 1] = 0;
                        col--;
                    }
                    break;
            }
        }
    }

}






