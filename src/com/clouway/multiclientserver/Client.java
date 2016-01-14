package com.clouway.multiclientserver;

import org.omg.CORBA.portable.OutputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by clouway on 16-1-5.
 */
public class Client {
    private String ip;
    private int port;
    private Display display;
    private Socket socket;

    public Client(String ip, int port, Display display) {
        this.ip = ip;
        this.port = port;
        this.display = display;
    }

    public void connect() {
        try {
            socket = new Socket(ip, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String recievedMessage = in.readLine();
            display.setMessage(recievedMessage);
            Thread.sleep(220);
        } catch (IOException e) {
            e.printStackTrace();
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }
    }

    public  void disconnect() {
        try {
            socket.close();
            try {
                Thread.sleep(220);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
        }
    }
}
