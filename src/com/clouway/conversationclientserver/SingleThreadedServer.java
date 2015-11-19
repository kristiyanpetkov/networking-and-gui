package com.clouway.conversationclientserver;

import com.google.common.util.concurrent.AbstractExecutionThreadService;

import java.io.*;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class SingleThreadedServer extends AbstractExecutionThreadService {
    private final int port;
    private final Clock clock;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private ServerSocket serverSocket;
    private PrintWriter out;
    public SingleThreadedServer(int port, Clock clock) {
        this.port = port;
        this.clock = clock;
    }

    @Override
    protected void run() {
        try {
            out = new PrintWriter(serverSocket.accept().getOutputStream());
            while (isRunning()) {
                String messageToSend = "Hello! " + dateFormat.format(clock.currentDate());
                out.write(messageToSend);
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void startUp() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void triggerShutdown() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
