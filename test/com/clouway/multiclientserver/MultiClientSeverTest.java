package com.clouway.multiclientserver;

import javafx.concurrent.Service;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by clouway on 16-1-12.
 */
public class MultiClientSeverTest {

    public class ClientsDisplay implements Display {
        private String message;

        @Override
        public void setMessage(String message) {
            System.out.println(message);
            this.message = message;
        }
    }

    private Server server;

    @Before
    public void startServer() {
        server = new Server(4008);
        server.startAsync().awaitRunning();
    }

    @Test
    public void twoClientsConnected() {
        ClientsDisplay clientsDisplay = new ClientsDisplay();
        Client client = new Client("localhost", 4008, clientsDisplay);
        client.connect();
        assertThat(clientsDisplay.message, is("Hello! You are client number 1 "));
        client.connect();
        assertThat(clientsDisplay.message, is("Hello! You are client number 2 "));
    }

    @Test
    public void clientsConnectAndDisconnect() {
        ClientsDisplay clientsDisplay = new ClientsDisplay();
        Client client = new Client("localhost", 4008, clientsDisplay);
        client.connect();
        assertThat(clientsDisplay.message, is("Hello! You are client number 1 "));
        client.connect();
        assertThat(clientsDisplay.message, is("Hello! You are client number 2 "));
        client.disconnect();
        client.connect();
        assertThat(clientsDisplay.message, is("Hello! You are client number 2 "));
        client.connect();
        assertThat(clientsDisplay.message, is("Hello! You are client number 3 "));
        client.connect();
        assertThat(clientsDisplay.message, is("Hello! You are client number 4 "));
        client.disconnect();
        client.connect();
        assertThat(clientsDisplay.message, is("Hello! You are client number 4 "));
    }

    @After
    public void stopServer() {
        server.stopAsync().awaitTerminated();
    }
}
