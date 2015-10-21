package com.clouway.conversationclientserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class Server {
    private final int port;
    private final ServerDisplay display;
    private ServerSocket serverSocket;

    public Server(int port, ServerDisplay display) {
        this.port = port;
        this.display = display;
    }

    public void sendSystemData() {
        try {
            startServer();
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            String messageToSend = "Hello" + new Date();
            display.setMessage(messageToSend);
            out.println(messageToSend);
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

    private void startServer() {
        try {
            serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
