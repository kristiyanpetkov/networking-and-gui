package com.clouway.conversationclientserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class Client {
    private Socket echoSocket;
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
           echoSocket = new Socket(this.hostName, this.port);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to ");
        }
    }

    public String lastMessage(){
        BufferedReader in = null;
        String receiveMessage="";
        try {
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            receiveMessage = in.readLine();
            display.setMessage(receiveMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return receiveMessage;
    }
}
