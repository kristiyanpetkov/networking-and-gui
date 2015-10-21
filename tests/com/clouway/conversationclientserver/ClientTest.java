package com.clouway.conversationclientserver;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class ClientTest {
    public class TestServer {
        private ServerSocket serverSocket;

        public TestServer(int port) {
            try {
                serverSocket=new ServerSocket(port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public boolean clientConnect(){
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                String messageToSend = "Hello!";
                out.println(messageToSend);
                out.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock
    Display display;

    @Test
    public void clientReceiveMessage() {
        Client client = new Client("localhost", 7777, display);
        TestServer testServer=new TestServer(7777);
        context.checking(new Expectations() {{
            oneOf(display).setMessage("Hello!");
        }});
        client.connect();
        testServer.clientConnect();
        assertThat(client.lastMessage(),is(equalTo("Hello!")));
    }
}
