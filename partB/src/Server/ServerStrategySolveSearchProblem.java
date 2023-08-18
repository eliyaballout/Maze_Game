package Server;
import algorithms.mazeGenerators.Maze;
import algorithms.search.ASearchingAlgorithm;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;
import java.io.*;
import java.util.HashMap;


/**
 * ServerStrategySolveSearchProblem Class which implements IServerStrategy Interface.
 * The Class acting as strategy of mazes searching problem solver.
 */

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    static HashMap<Integer, String> solvedMazes;


    /**
     * solving maze searching problem, strategy implementation.
     * getting from client the maze object and sending to client the maze solution.
     * @param inFromClient: input getting from client.
     * @param outToClient: output sending to client.
     */
    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        solvedMazes = new HashMap<Integer, String>();
        try {
            String tempDirectoryPath = System.getProperty("java.io.tmpdir");
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();
            Maze maze = (Maze)fromClient.readObject();
            int key = maze.hashCode();
            String path = tempDirectoryPath + "/" + key;

            if (solvedMazes.containsKey(key)) {
                FileInputStream fileIn = new FileInputStream(path);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                Solution solution1 = (Solution) objectIn.readObject();
                toClient.writeObject(solution1);
            }

            else {
                ASearchingAlgorithm searchingAlgorithm = Configurations.getSearchingAlgorithm();
                SearchableMaze test = new SearchableMaze(maze);
                Solution solution = searchingAlgorithm.solve(test);
                FileOutputStream outFile = new FileOutputStream(path);
                ObjectOutputStream outObject = new ObjectOutputStream(outFile);
                outObject.writeObject(solution);
                solvedMazes.put(key, path);
                outObject.close();
                outFile.close();
                FileInputStream fileIn = new FileInputStream(path);
                ObjectInputStream inObject = new ObjectInputStream(fileIn);
                inObject.close();
                fileIn.close();
                toClient.writeObject(solution);
            }

            fromClient.close();
            toClient.close();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
