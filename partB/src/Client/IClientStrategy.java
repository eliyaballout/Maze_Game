package Client;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * IClientStrategy interface.
 * this interface is responsible for applying different client strategies.
 */

public interface IClientStrategy {

    /**
     * client strategy according to needed action.
     * @param inFromServer: receiving input from server.
     * @param outToServer: sending output to server.
     */
    void clientStrategy(InputStream inFromServer, OutputStream outToServer);

}
