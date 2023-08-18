package test;
import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import IO.SimpleCompressorOutputStream;
import IO.SimpleDecompressorInputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import java.io.*;
import java.util.Arrays;



public class RunCompressDecompressMaze {

    public static void main(String[] args) {
        String mazeFileName = "savedMaze.maze";
        AMazeGenerator mazeGenerator = new MyMazeGenerator();
        Maze maze = mazeGenerator.generate(100, 100); //Generate new maze

        //##############################################################################################################
        try {
            // save maze to a file
            OutputStream out = new SimpleCompressorOutputStream(new FileOutputStream(mazeFileName));
            out.write(maze.toByteArray());
            out.flush();
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        byte savedMazeBytes1[] = new byte[0];
        try {
            //read maze from file
            InputStream in = new SimpleDecompressorInputStream(new FileInputStream(mazeFileName));
            savedMazeBytes1 = new byte[maze.toByteArray().length];
            in.read(savedMazeBytes1);
            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        Maze loadedMaze1 = new Maze(savedMazeBytes1);
        boolean areMazesEquals1 = Arrays.equals(loadedMaze1.toByteArray(),maze.toByteArray());
        System.out.println("Compress and Decompress the maze in 'SimpleCompressor/Decompressor': ");
        System.out.println(String.format("Mazes equal: %s",areMazesEquals1));
        //maze should be equal to loadedMaze


        //##############################################################################################################


        try {
            // save maze to a file
            OutputStream out = new MyCompressorOutputStream(new FileOutputStream(mazeFileName));
            out.write(maze.toByteArray());
            out.flush();
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        byte savedMazeBytes[] = new byte[0];
        try {
            //read maze from file
            InputStream in = new MyDecompressorInputStream(new FileInputStream(mazeFileName));
            savedMazeBytes = new byte[maze.toByteArray().length];
            in.read(savedMazeBytes);
            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        Maze loadedMaze = new Maze(savedMazeBytes);
        boolean areMazesEquals = Arrays.equals(loadedMaze.toByteArray(),maze.toByteArray());
        System.out.println("Compress and Decompress the maze in 'MyCompressor/Decompressor': ");
        System.out.println(String.format("Mazes equal: %s",areMazesEquals));
        //maze should be equal to loadedMaze
    }

}