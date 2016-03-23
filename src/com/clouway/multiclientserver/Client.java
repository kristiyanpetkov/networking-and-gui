package com.clouway.multiclientserver;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by clouway on 16-1-5.
 */
public class Client {
    private String ip;
    private int port;
    private Display display;
    private Socket socket;
    private boolean connected=false;

    public Client(String ip, int port, Display display) {
        this.ip = ip;
        this.port = port;
        this.display = display;
    }

    public void connect() {
        try {
            socket = new Socket(ip, port);
            connected=true;
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String recievedMessage = in.readLine();
            display.setMessage(recievedMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendDisconnect() {
        if (connected) {
            try {
                OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
                out.write("Closed\n");
                out.flush();
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String l = reader.readLine();
            } catch (IOException e) {
            }
        }
    }
}
