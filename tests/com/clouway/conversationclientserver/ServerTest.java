package com.clouway.conversationclientserver;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.io.*;
import java.net.Socket;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class ServerTest {
    public class TestClient {
        private Socket echoSocket;
        private final String hostName;
        private final int port;

        public TestClient(String hostName, int port) {
            this.hostName = hostName;
            this.port = port;
        }

        public void connect() {
            try {
                echoSocket = new Socket(this.hostName, this.port);
            } catch (IOException e) {
                System.err.println("Couldn't get I/O for the connection to ");
            }
        }

        public String lastMessage(){
            BufferedReader in = null;
            String receiveMessage="";
            try {
                in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
                receiveMessage = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return receiveMessage;
        }
    }

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock
    Clock clock;

    @Test
    public void serverSendingMessage() {
        Server server = new Server(7777, clock);
        TestClient testClient=new TestClient("localhost",7777);
        context.checking(new Expectations() {{
            oneOf(clock).currentDate();
            will(returnValue("2015-12-12"));
        }});
        testClient.connect();
        server.clientConnect();
        assertThat(testClient.lastMessage(), is("Hello! 2015-12-12"));
    }
}
