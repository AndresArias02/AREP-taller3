package edu.eci.arep;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

    private static HttpServer instance;
    private static final int PORT = 35000;
    public static String WEB_ROOT;
    private boolean running = false;

    public static HttpServer getInstance(){
        if(instance == null){
            instance = new HttpServer();
        }
        return instance;
    }

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

    public boolean isRunning(){
        return running;
    }

    public void setStaticFileLocation(String webRoot){
        WEB_ROOT = webRoot;
    }
}
