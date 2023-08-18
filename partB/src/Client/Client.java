package Client;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;


/**
 * Client Class.
 * The Class represents a client.
 */

public class Client {

    private InetAddress serverIP;
    private int serverPort;
    private IClientStrategy clientStrategy;


    /**
     * Constructor for building Client Object.
     * @param serverIP: server IP address.
     * @param serverPort: server Port address.
     * @param clientStrategy: interface for client strategy according to needed action.
     */
    public Client(InetAddress serverIP, int serverPort, IClientStrategy clientStrategy) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.clientStrategy = clientStrategy;
    }


    /**
     * starting the client operation.
     * connect with server.
     * applying appropriate client strategy.
     */
    public void start(){
        try {
            Socket serverSocket = new Socket(this.serverIP, this.serverPort);
            System.out.println("Client is connected to server - IP = " + this.serverIP + ", Port = " + this.serverPort);
            this.clientStrategy.clientStrategy(serverSocket.getInputStream(), serverSocket.getOutputStream());
            serverSocket.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }


    /**
     * communicating with server.
     * applying start method.
     */
    public void communicateWithServer(){
        this.start();
    }

}
