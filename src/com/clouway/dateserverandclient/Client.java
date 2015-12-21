package com.clouway.dateserverandclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by clouway on 15-12-21.
 */
public class Client {
    private Display display;
    public BufferedReader in;
    public String dateAndTime;
    private final String serverIP;
    private final int serverPort;

    public Client(String serverIP, int serverPort, Display display) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.display=display;
    }

    public String connectClient() {
        dateAndTime = null;
        try {
            Socket socket = new Socket(serverIP, serverPort);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            dateAndTime = in.readLine();
            display.setMessage(dateAndTime);
            socket.close();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
        return dateAndTime;
    }
}
