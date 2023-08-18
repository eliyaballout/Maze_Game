package Server;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;
import algorithms.search.ASearchingAlgorithm;
import algorithms.search.BestFirstSearch;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.DepthFirstSearch;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * Configuration static Class which define 3 different definitions:
 * the number of Threads on ThreadPool.
 * which way of generating new maze.
 * the type of searching algorithm for solving the maze.
 */
public class Configurations {

    private static Properties prop;


    /**
     * static method for initializing the properties of this class.
     */
    public static void start(){
        try (InputStream input = new FileInputStream("resources/config.properties")) {
            prop = new Properties();
            prop.load(input);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * getting the number of Threads on ThreadPool.
     * @return number of the threads.
     */
    public static int getNumOfThreads(){
        int threads = 10;
        try {
            threads = Integer.valueOf(prop.getProperty("threadPoolSize"));
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return threads;
    }


    /**
     * getting the way of generating a new maze.
     * @return Maze Generator.
     */
    public static AMazeGenerator getMazeGenerator(){
        AMazeGenerator MazeGenerator = new MyMazeGenerator();
        switch (prop.getProperty("mazeGeneratingAlgorithm")){
            case "MyMazeGenerator":
                MazeGenerator = new MyMazeGenerator();
                break;

            case "SimpleMazeGenerator":
                MazeGenerator = new SimpleMazeGenerator();
                break;
        }

        return MazeGenerator;
    }


    /**
     * getting the type of searching algorithm for solving the maze.
     * @return the searching algorithm.
     */
    public static ASearchingAlgorithm getSearchingAlgorithm(){
        ASearchingAlgorithm SearchingAlgorithm = new BreadthFirstSearch();
        switch (prop.getProperty("mazeSearchingAlgorithm")){

            case "BreadthFirstSearch":
                SearchingAlgorithm = new BreadthFirstSearch();
                break;

            case "DepthFirstSearch":
                SearchingAlgorithm = new DepthFirstSearch();
                break;

            case "BestFirstSearch":
                SearchingAlgorithm = new BestFirstSearch();
                break;
        }

        return SearchingAlgorithm;
    }

}
