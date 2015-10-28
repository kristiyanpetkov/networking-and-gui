package com.clouway.conversationclientserver;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.lib.concurrent.Synchroniser;
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
        private String receiveMessage;

        public TestClient(String hostName, int port) {
            this.hostName = hostName;
            this.port = port;
        }

        public void connect() {
            try {
                echoSocket = new Socket(this.hostName, this.port);
                BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
                receiveMessage = in.readLine();
            } catch (IOException e) {
                System.err.println("Couldn't get I/O for the connection to ");
            }
        }

        public void assertLastReceivedMessageIs(String message) {
            assertThat(receiveMessage, is(message));
        }
    }

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery() {{
        setThreadingPolicy(new Synchroniser());
    }};

    @Mock
    Clock clock;

    @Test
    public void serverSendingMessage() {
        final Server server = new Server(7777, clock);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final Date now = new Date();
        TestClient testClient = new TestClient("localhost", 7777);
        pretendThatServerTimeIs(now);
        server.start();
        testClient.connect();
        testClient.assertLastReceivedMessageIs("Hello! " + dateFormat.format(now));
    }

    private void pretendThatServerTimeIs(final Date time) {
        context.checking(new Expectations() {{
            oneOf(clock).currentDate();
            will(returnValue(time));
        }});
    }
}
