package IO;
import java.io.IOException;
import java.io.OutputStream;


/**
 * MyCompressorOutputStream class that extends the abstract class OutputStream.
 * this class is responsible for writing compressed data.
 */

public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;


    /**
     * Constructor, initializing the attribute.
     * @param o: destination to write bytes stream.
     */
    public MyCompressorOutputStream(OutputStream o) {
        this.out = o;
    }


    /**
     * override method from OutputStream Class.
     * must implement this method.
     * @param b: the {@code byte}.
     * @throws IOException
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
        String res = "";
        for(int i = 0; i < 12; i++) {
            out.write(b[i]);
        }

        int cols = (int)b[2] * 127 + (int)b[3];
        int tmpCol = cols;
        int num;
        int i = 12;

        while(i < b.length) {
            if (res.length() < 8 && res.length() < tmpCol) {
                res += ((Byte) b[i]).toString();}

            if ((i == b.length - 1 || res.length() == 8 || res.length() == tmpCol)) {
                num = Integer.parseInt(res, 2);
                tmpCol = tmpCol - res.length();
                out.write(num);
                res = "";
            }

            if (tmpCol <= 0)
                tmpCol = cols;

            i++;
        }
    }

}
