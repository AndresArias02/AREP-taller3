package edu.eci.arep.servers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * HttpServer Class
 * @author Andrés Arias
 */
public class HttpServer {

    private static HttpServer instance;
    private static final int PORT = 8080;
    public static String WEB_ROOT;
    private boolean running = false;

    /**
     * Method to get the unique instance of HttpServer
     * @return the instance
     */
    public static HttpServer getInstance(){
        if(instance == null){
            instance = new HttpServer();
        }
        return instance;
    }

    /**
     * method to start the server
     * @throws IOException If an IA error occurs
     */
    public void start() throws IOException {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        ServerSocket serverSocket = new ServerSocket(PORT);

        System.out.println(" ");
        System.out.println("Ready to recive requests.... ");

        running = true;
        while (running) {
            Socket clientSocket = serverSocket.accept();
            threadPool.submit(new ClientHandler(clientSocket));
        }
    }

    /**
     * method to verify if the httpServer is running
     * @return flag with the value true if so ot false if not
     */
    public boolean isRunning(){
        return running;
    }

    /**
     * metod to define the path of the statics files
     * @param webRoot path
     */
    public void setStaticFileLocation(String webRoot){
        WEB_ROOT = webRoot;
    }
}
