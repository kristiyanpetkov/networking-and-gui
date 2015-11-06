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
            byte[] b=new byte[500];
            int read=inputStream.read(b);
            String message=new String(b,0,read-1);
            display.setMessage(message);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to ");
        }
    }
}
