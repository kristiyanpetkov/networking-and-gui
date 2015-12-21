package com.clouway.dateserverandclient;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by clouway on 15-12-21.
 */
public class DateServer {
    private final int port;
    private final Clock clock;

    public DateServer(int port, Clock clock) {
        this.port = port;
        this.clock = clock;
    }

    private ServerSocket serverSocket;

    public void startServer() {
        (new Thread() {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(port);
                }catch (IOException ioE) {
                    ioE.printStackTrace();
                }
                try{
                    while (!isInterrupted()) {
                       Socket socket = serverSocket.accept();
                        OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
                        Date date = clock.now();
                        out.write("Hello! Current date: " + date);
                        out.close();
                        interrupt();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void stopServer() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
