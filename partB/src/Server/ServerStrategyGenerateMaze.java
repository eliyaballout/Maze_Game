package Server;
import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import java.io.*;


/**
 * ServerStrategyGenerateMaze Class which implements IServerStrategy Interface.
 * The Class acting as strategy of mazes creator.
 */

public class ServerStrategyGenerateMaze implements IServerStrategy {

    /**
     * generating maze strategy implementation.
     * getting from client the maze size and sending to client the generated maze.
     * @param inFromClient: input getting from client.
     * @param outToClient: output sending to client.
     */
    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();
            AMazeGenerator generator = Configurations.getMazeGenerator();

            Maze maze;
            byte[] byteMaze;
            int[] mazeSize = (int[])fromClient.readObject();

            if (mazeSize instanceof int[] && mazeSize.length == 2)
                maze = generator.generate(mazeSize[0], mazeSize[1]);

            else // Client Sent us Wrong Format - Default Maze Size
                maze = (generator.generate(10, 10));

            byteMaze = maze.toByteArray();
            ByteArrayOutputStream toClientBytes = new ByteArrayOutputStream();
            MyCompressorOutputStream compressedToClient = new MyCompressorOutputStream(toClientBytes);
            compressedToClient.write(byteMaze);
            toClient.writeObject(toClientBytes.toByteArray());
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

}