package com.clouway.conversationclientserver;

import java.io.*;
import java.net.Socket;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class Client {
    private final String hostName;
    private final int port;
    private final Display display;

    public Client(String hostName, int port, Display display) {
        this.hostName = hostName;
        this.port = port;
        this.display = display;
    }

    public void connect() {
        try {
            Socket socket = new Socket(hostName, port);
            InputStream inputStream= socket.getInputStream();
            int i;
            String receivedMessage="";
            while ((i=inputStream.read())!=-1){
                receivedMessage+=(char)i;
            }
            display.setMessage(receivedMessage);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to ");
        }
    }
}
