package com.clouway.conversationclientserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class Client {

    private final String hostName;
    private final int port;
    private final ClientDisplay display;

    public Client(String hostName, int port, ClientDisplay display) {
        this.hostName = hostName;
        this.port = port;
        this.display = display;
    }

    public void connect() {
        try {
            Socket echoSocket = new Socket(this.hostName, this.port);
            BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            String receiveMessage = in.readLine();
            display.setMessage(receiveMessage);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to ");
        }
    }
}
