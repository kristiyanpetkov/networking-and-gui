package com.clouway.multiclientserver;

import java.io.*;
import java.net.Socket;

/**
 * Created by clouway on 16-1-11.
 */
public class ClientConnection {
    private Socket client;
    private ConnectionStateChangeListener connectionStateChangeListener;

    public ClientConnection(Socket client, ConnectionStateChangeListener connectionStateChangeListener) {
        this.client = client;
        this.connectionStateChangeListener = connectionStateChangeListener;
    }

    public void sendMessage(String message) {
        try {
            OutputStreamWriter out = new OutputStreamWriter(client.getOutputStream());
            out.write(message);
            out.flush();
            Thread thr;
            (thr = new Thread() {
                @Override
                public void run() {
                    readMessage();
                }
            }).start();
        } catch (IOException ioe) {
            connectionStateChangeListener.onClose(this);
        }
    }

    public void readMessage() {
        try {
            InputStream in = new BufferedInputStream(client.getInputStream());
            int bytes = in.read();
            in.close();
        } catch (IOException e) {
            connectionStateChangeListener.onClose(this);
        }
    }
}
