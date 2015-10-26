package com.clouway.conversationclientserver;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

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

        public String lastReceiveMessage(){
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
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        final Date date=new Date();
        TestClient testClient=new TestClient("localhost",7777);
        context.checking(new Expectations() {{
            oneOf(clock).currentDate();
            will(returnValue(date));
        }});
        server.start();
        testClient.connect();
        server.listen();
        assertThat(testClient.lastReceiveMessage(), is("Hello! "+dateFormat.format(date)));
    }
}
