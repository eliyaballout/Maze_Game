package IO;
import java.io.IOException;
import java.io.OutputStream;


/**
 * SimpleCompressorOutputStream class that extends the abstract class OutputStream.
 * this class is responsible for writing compressed data.
 */

public class SimpleCompressorOutputStream extends OutputStream{

    private OutputStream out;


    /**
     * Constructor, initializing the attribute.
     * @param o: destination to write bytes stream.
     */
    public SimpleCompressorOutputStream(OutputStream o) {
        this.out = o;
    }


    /**
     * override method from OutputStream Class.
     * must implement this method.
     * @param b: the {@code byte}.
     * @throws IOException .
     */
    @Override
    public void write(int b) throws IOException { }


    /**
     * method which responsible on compressing and writing bytes into out OutputStream.
     * @param b: the data.
     * @throws IOException .
     */
    @Override
    public void write(byte[] b) throws IOException {
        int counter = 0;
        int currVal = 0;
        boolean flag = false;
        int i = 0;

        for (i = 0; i < 12; i++){ //write basic data about the maze.
            out.write(b[i]);
        }

        for (; i < b.length; i++){
            while (i < b.length && b[i] == currVal && counter < 255){
                counter++;
                i++;
                flag = true;
            }

            out.write((byte)counter);
            if (i >= b.length)
                break;

            currVal = b[i];
            if (flag || counter == 0)
                i--;

            flag = false;
            counter = 0;
        }
    }

}
