package Server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Server class.
 * this class is the server of the program.
 * ths server responsible on handling and responding on client requests.
 */

public class Server {

    private int port;
    private int listeningInterval;
    private IServerStrategy serverStrategy;
    private volatile boolean stop;
    private ExecutorService threadPool;


    /**
     * constructor, initializing the attributes.
     * @param port: the port of the server.
     * @param listeningInterval: maximum time to wait to a connection to arrive.
     * @param serverStrategy: the specific strategy / role of the server.
     */
    public Server(int port, int listeningInterval, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningInterval = listeningInterval;
        this.serverStrategy = serverStrategy;
    }


    /**
     * allocating new thead as a running server.
     */
    public void start(){
        new Thread(() -> runServer()).start();
    }


    /**
     * running the server.
     * the server is waiting for handling assignments from clients.
     */
    public void runServer(){
        Configurations.start();
        this.threadPool = Executors.newFixedThreadPool(Configurations.getNumOfThreads());
        try {
            ServerSocket serverSocket = new ServerSocket(this.port);
            serverSocket.setSoTimeout(this.listeningInterval);
            while(!stop){
                try {
                    Socket clientSocket = serverSocket.accept();
                    this.threadPool.execute(() -> handleClient(clientSocket));
                }
                catch (SocketTimeoutException e){
                    System.out.println("Socket Timeout - no clients pending!");
                }
            }

            this.threadPool.shutdown();
            serverSocket.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }


    /**
     * handling the client job accordingly to the appropriate strategy.
     * @param clientSocket: the client.
     */
    public void handleClient(Socket clientSocket){
        try {
            serverStrategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    /**
     * stopping/shutting down the server.
     */
    public void stop(){
        stop = true;
    }

}
