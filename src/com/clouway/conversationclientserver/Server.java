package com.clouway.conversationclientserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class Server {
    private final int port;
    private final Clock clock;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private  ServerSocket serverSocket;
    public Server(int port, Clock clock) {
        this.port = port;
        this.clock = clock;
    }

    public void start() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                     serverSocket = new ServerSocket(port);
                    Socket clientSocket = null;
                    while (true){
                        clientSocket = serverSocket.accept();
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        String messageToSend = "Hello! " + dateFormat.format(clock.currentDate());
                        out.println(messageToSend);
                        out.close();
                    }
                } catch (IOException e) {
                    System.out.println(serverSocket.isClosed());
                }
            }
        });
        thread.start();
    }

    public void stop(){
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
