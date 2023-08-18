package algorithms.mazeGenerators;
import java.util.ArrayList;
import java.util.Random;


/**
 * MyMazeGenerator class that extends AMazeGenerator abstract class.
 * this class responsible for generating a maze based on Randomized Prim's Algorithm
 */

public class MyMazeGenerator extends AMazeGenerator{

    /**
     * method for generating the maze, The cells value of the maze ('path and walls) chosen randomly and based on Prim's algorithm.
     * but the maze always has solution.
     * @param row: number of rows in the maze.
     * @param col: number of columns in the maze.
     * @return maze generated with Prim's algorithm, with given size of row and column.
     */
    @Override
    public Maze generate(int row, int col) {
        if (row >= 2 && col >= 2){

            Maze MyMaze = new Maze(row, col);
            int[][] maze = MyMaze.getMaze();
            Random rand = new Random();
            ArrayList<Position> wallsList = new ArrayList<>();
            Position startPoint = new Position(0, rand.nextInt(col));
            Position goalPoint = new Position(row - 1, rand.nextInt(col));
            MyMaze.setStartPosition(startPoint.getRowIndex(), startPoint.getColumnIndex());

            for (int i = 0; i < row; i++)
                for (int j = 0; j < col; j++)
                    MyMaze.changeValue(i, j, 1);

            MyMaze.changeValue(startPoint.getRowIndex(), startPoint.getColumnIndex(), 0);
            this.addWalls(maze, wallsList, startPoint.getRowIndex(), startPoint.getColumnIndex());

            while (!wallsList.isEmpty()){
                Position randomWall = wallsList.get(rand.nextInt(wallsList.size()));
                ArrayList<Position> neighbors = FindNeighbors(maze, randomWall.getRowIndex(), randomWall.getColumnIndex());
                if (neighbors.size() == 1){
                    MyMaze.changeValue(randomWall.getRowIndex(), randomWall.getColumnIndex(), 0);
                    this.addWalls(maze, wallsList, randomWall.getRowIndex(), randomWall.getColumnIndex());
                }

                wallsList.remove(randomWall);
            }

            MyMaze.setGoalPosition(goalPoint.getRowIndex(), goalPoint.getColumnIndex());
            MyMaze.changeValue(goalPoint.getRowIndex(), goalPoint.getColumnIndex(), 0);

            return MyMaze;
        }

        else{
            System.out.println("Cannot construct a 'my maze' with coordinates that less than 2*2 size");
            System.out.println("We will create a default 'my maze' in size of 10*10");
            return this.generate(10,10);
        }

    }


    /**
     * private method for adding walls that are in the maze to a walls list.
     * this method gets walls list ,maze and Position [row,col]
     * and add to the walls list all the walls the beside to the given position.
     * @param maze: 2D array of the maze.
     * @param wallsList: the walls list to add to.
     * @param row: row index.
     * @param col: col index.
     */
    private void addWalls(int[][] maze, ArrayList<Position> wallsList, int row, int col){
        if (col + 1 < maze[row].length){ //right
            if (maze[row][col + 1] == 1)
                wallsList.add(new Position(row, col + 1));
        }

        if(col - 1 >= 0){ //left
            if(maze[row][col - 1] == 1)
                wallsList.add(new Position(row, col - 1));
        }

        if(row - 1 >= 0){ //up
            if(maze[row - 1][col] == 1)
                wallsList.add(new Position(row - 1, col));
        }

        if(row + 1 < maze.length){ //down
            if(maze[row + 1][col] == 1)
                wallsList.add(new Position(row + 1, col));
        }

    }


    /**
     * private method that adds all the visited neighbors of a specific position to an array.
     * this method gets the maze and a specific position [row,col]
     * and return the number of visited neighbors of the given position.
     * @param maze: 2D array of the maze.
     * @param row: row index.
     * @param column: col index.
     * @return
     */
    private ArrayList<Position> FindNeighbors(int[][] maze, int row, int column) {
        ArrayList<Position> neighbors = new ArrayList<>();
        if(column + 1 < maze[row].length){ //right
            if(maze[row][column + 1] == 0)
                neighbors.add(new Position(row, column + 1));
        }

        if(column - 1 >= 0){ //left
            if(maze[row][column - 1] == 0)
                neighbors.add(new Position(row, column - 1));
        }

        if(row - 1 >= 0 ){ //up
            if(maze[row - 1][column] == 0)
                neighbors.add(new Position(row - 1, column ));
        }

        if(row + 1 < maze.length){ //down
            if(maze[row + 1][column] == 0)
                neighbors.add(new Position(row + 1, column));
        }

        return neighbors;
    }

}
