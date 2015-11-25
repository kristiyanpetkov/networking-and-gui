package com.clouway.conversationclientserver;

import com.google.common.util.concurrent.Service;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.clouway.util.CalendarUtil.january;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class SingleThreadedServerTest {
    private SingleThreadedServer server;
    Synchroniser synchroniser=new Synchroniser();

    public class TestClient {
        private Socket socket;
        private final String hostName;
        private final int port;
        private String receivedMessage="";

        public TestClient(String hostName, int port) {
            this.hostName = hostName;
            this.port = port;
        }

        public void connect() {
            try {
                socket = new Socket(this.hostName, this.port);
                InputStream inputStream= socket.getInputStream();
                byte[] b=new byte[500];
                int read=inputStream.read(b);
                receivedMessage=new String(b,0,read);
            } catch (IOException e) {
                System.err.println("Couldn't get I/O for the connection to ");
            }
        }

        public void assertLastReceivedMessageIs(String message) {
            assertThat(receivedMessage, is(message));
        }
    }

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery() {{
        setThreadingPolicy(synchroniser);
    }};

    private Clock clock;

    private Service service;

    @Before
    public void setUp() {
        clock = context.mock(Clock.class);
        server = new SingleThreadedServer(7777, clock);
        service = server.startAsync();
        service.awaitRunning();
    }

    @Test
    public void serverSendingMessage() {
        final Date now = new Date();
        pretendThatServerTimeIs(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        TestClient testClient = new TestClient("localhost", 7777);
        testClient.connect();
        testClient.assertLastReceivedMessageIs("Hello! " + dateFormat.format(now));
    }

    @Test
    public void serverSendingMessageWhitDifferentDate() {
        final Date anyDate = january(2015,23);
        pretendThatServerTimeIs(anyDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        TestClient testClient = new TestClient("localhost", 7777);
        testClient.connect();
        testClient.assertLastReceivedMessageIs("Hello! " + dateFormat.format(anyDate));
    }

    @After
    public void stopServer() {
        service.stopAsync().awaitTerminated();
    }

    private void pretendThatServerTimeIs(final Date time) {
        context.checking(new Expectations() {{
            allowing(clock).currentDate();
            will(returnValue(time));
        }});
    }
}
