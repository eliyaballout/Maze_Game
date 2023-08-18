package IO;
import java.io.IOException;
import java.io.InputStream;


/**
 * SimpleDecompressorInputStream Class that extends the abstract class InputStream.
 * this class is responsible for reading and decompressing data.
 */

public class SimpleDecompressorInputStream extends InputStream {

    private InputStream in;


    /**
     * Constructor, initializing the attribute.
     * @param in: source for reading bytes stream.
     */
    public SimpleDecompressorInputStream(InputStream in) {
        this.in = in;
    }


    /**
     * override method from InputStream Class.
     * must implement this method.
     * @return the next byte of data, or -1 if the end of the stream is reached.
     * @throws IOException .
     */
    @Override
    public int read() throws IOException {
        return 0;
    }


    /**
     * method which responsible on decompressing and reading bytes from in InputStream.
     * @param b: the buffer into which the data is read.
     * @return the next byte of data, or -1 if the end of the stream is reached.
     * @throws IOException .
     */
    @Override
    public int read(byte[] b) throws IOException {
        byte[] inputData = in.readAllBytes();

        for (int i = 0; i < 12; i++)
            b[i] = inputData[i];

        int counter;
        int index = 12;
        byte currVal = 0;

        for (int i = 12; i < inputData.length; i++) {
            if (i % 2 == 0)
                currVal = 0;
            else
                currVal = 1;

            counter = inputData[i];

            while (counter != 0) {
                b[index++] = currVal;
                counter--;
            }
        }

        return 0;
    }

}
