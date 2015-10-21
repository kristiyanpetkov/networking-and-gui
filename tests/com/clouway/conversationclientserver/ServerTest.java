package com.clouway.conversationclientserver;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.Socket;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class ServerTest {
    private String message;

    @Before
    public void setUp() {
            Thread myThread = new Thread() {
                public void run() {
                    BufferedReader in = null;
                    try {
                        Socket client = new Socket("localhost", 7777);
                        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        message = in.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            myThread.start();
    }

    @Test
    public void serverSendingMessage() {
        ServerDisplay display = new ServerDisplay();
        Server server = new Server(7777, display);
        server.sendSystemData();
        setUp();
        assertThat(message, is(equalTo(display.getMessage())));
    }
}
