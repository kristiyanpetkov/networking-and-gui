package com.clouway.multiclientserver;

import com.google.common.util.concurrent.AbstractExecutionThreadService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by clouway on 16-1-5.
 */
public class Server extends AbstractExecutionThreadService implements ConnectionStateChangeListener {
    private int port;
    private BlockingQueue<ClientConnection> clientsList = new ArrayBlockingQueue<ClientConnection>(6);
    private ServerSocket serverSocket;

    public Server(int port) {
        this.port = port;
    }

    @Override
    protected void startUp() throws Exception {
        serverSocket = new ServerSocket(port);
    }

    @Override
    protected void run() {
        try {
            Socket client = null;
            while (isRunning()) {
                final ClientConnection clientConnection = new ClientConnection(serverSocket.accept(), this);
                int currentConnectionCount = clientsList.size() + 1;
                clientConnection.sendMessage(String.format("Hello! You are client number %d \n", currentConnectionCount));
                synchronized (clientsList) {
                    clientsList.add(clientConnection);
                }
                sendMessageToAllClients();
            }
        } catch (IOException ioe) {
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

    @Override
    public void onClose(ClientConnection clientConnection) {
        synchronized (clientsList) {
            clientsList.remove(clientConnection);
            ;
        }
    }

    private void sendMessageToAllClients() {
        Iterator<ClientConnection> clientConnectionIterator = clientsList.iterator();
        while (clientConnectionIterator.hasNext()) {
            ClientConnection clientConection1 = clientConnectionIterator.next();
            int clientNumber = clientsList.size();
            clientConection1.sendMessage("Client number" + clientConection1 + " has connected");
        }
    }
}
