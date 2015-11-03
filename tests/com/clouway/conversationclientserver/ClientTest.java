package com.clouway.conversationclientserver;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class ClientTest {
    public class TestServer {
        private final int port;
        private ServerSocket serverSocket;

        public TestServer(int port) {

            this.port = port;
        }

        public void start() {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Socket clientSocket = null;
                    try {
                        serverSocket = new ServerSocket(port);
                        clientSocket = serverSocket.accept();
                        OutputStream out = clientSocket.getOutputStream();
                        String messageToSend = "Hello!";
                        byte[] bytesToSend=messageToSend.getBytes();
                        for (int i = 0; i < bytesToSend.length; i++) {
                            byte b = bytesToSend[i];
                            out.write(b);
                        }
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
    }

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock
    Display display;

    @Test
    public void clientReceiveMessage() {
        final TestServer testServer = new TestServer(7777);
        Client client = new Client("localhost", 7777, display);
        context.checking(new Expectations() {{
            oneOf(display).setMessage("Hello!");
        }});
        testServer.start();
        client.connect();
    }
}