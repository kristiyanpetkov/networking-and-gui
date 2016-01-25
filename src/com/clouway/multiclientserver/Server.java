package com.clouway.multiclientserver;

import com.google.common.util.concurrent.AbstractExecutionThreadService;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by clouway on 16-1-5.
 */
public class Server extends AbstractExecutionThreadService implements ConnectionStateChangeListener {
    private int port;
    public final List<ClientConnection> clientsList = new ArrayList<>();
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
            while (isRunning()) {
                final ClientConnection clientConnection = new ClientConnection(serverSocket.accept(), this);
                int currentConnectionCount;
                synchronized (clientsList) {
                    clientsList.add(clientConnection);
                    currentConnectionCount = clientsList.size();
                }
                clientConnection.sendMessage(String.format("Hello! You are client number %d \n", currentConnectionCount));
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
        }
    }

    private void sendMessageToAllClients() {
        synchronized (clientsList) {
            Iterator<ClientConnection> clientConnectionIterator = clientsList.iterator();
            while (clientConnectionIterator.hasNext()) {
                ClientConnection clientConnection1 = clientConnectionIterator.next();
                clientConnection1.sendMessage("Client number" + clientConnection1 + " has connected");
            }
        }
    }
}
