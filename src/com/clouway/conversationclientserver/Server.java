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
    private ServerSocket serverSocket;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public Server(int port, Clock clock) {
        this.port = port;
        this.clock = clock;
    }

    public boolean listen() {
        Socket clientSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            String messageToSend = "Hello! " + dateFormat.format(clock.currentDate());
            out.println(messageToSend);
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
