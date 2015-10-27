package com.clouway.conversationclientserver;

import java.io.*;
import java.net.Socket;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class Client {
    private final String hostName;
    private final int port;
    private Display display;

    public Client(String hostName, int port, Display display) {
        this.hostName = hostName;
        this.port = port;
        this.display = display;
    }

    public void connect() {
        try {
            Socket socket = new Socket(hostName, port);
            String receiveMessage = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            receiveMessage = in.readLine();
            display.setMessage(receiveMessage);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to ");
        }
    }
}
