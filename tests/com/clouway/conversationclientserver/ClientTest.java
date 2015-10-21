package com.clouway.conversationclientserver;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class ClientTest {
    private final String message = "Hello! " + new Date();

    @Before
    public void setUp() {
        Thread myThread = new Thread() {
            public void run() {
                PrintWriter out = null;
                try {
                    ServerSocket myServer = new ServerSocket(7777);
                    Socket clien = myServer.accept();
                    out = new PrintWriter(clien.getOutputStream(), true);
                    out.println(message);
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }

    @Test
    public void clientReceiveMessage() {
        ClientDisplay display = new ClientDisplay();
        Client client = new Client("localhost", 7777, display);
        client.start();
        assertThat(message, is(equalTo(display.getMessage())));
    }
}
