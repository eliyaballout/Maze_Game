package IO;
import java.io.IOException;
import java.io.InputStream;


/**
 * MyDecompressorInputStream Class that extends the abstract class InputStream.
 * this class is responsible for reading and decompressing data.
 */
public class MyDecompressorInputStream extends InputStream {

    private InputStream in;


    /**
     * Constructor, initializing the attribute.
     * @param in: source for reading bytes stream.
     */
    public MyDecompressorInputStream(InputStream in) {
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
        for(int i = 0; i < 12; i++) {
            b[i] = ((Integer) in.read()).byteValue();
        }

        int cols = (int)b[2] * 127 + (int)b[3];
        int binaryLength;
        int colNum = cols;

        for(int i = 12; i < b.length; i++) {
            Integer num = in.read();
            String numAsBinary = Integer.toBinaryString(num);
            binaryLength = numAsBinary.length();

            if (binaryLength < 8) {

                for (int j = 0; j < 8 - binaryLength && colNum > numAsBinary.length(); j++) {
                    numAsBinary = "0" + numAsBinary;
                }

                colNum = colNum - numAsBinary.length();

            }

            else {
                colNum -= 8;
            }

            for (int k = 0; k < numAsBinary.length() && i < b.length; k++) {
                b[i++] = Byte.parseByte(String.valueOf(numAsBinary.charAt(k)));
            }

            i--;
            if (colNum <= 0) {
                colNum = cols;
            }
        }

        return 0;
    }

}
