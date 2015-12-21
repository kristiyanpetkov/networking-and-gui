package com.clouway.dateserverandclienttest;

import com.clouway.dateserverandclient.Client;
import com.clouway.dateserverandclient.Clock;
import com.clouway.dateserverandclient.DateServer;
import com.clouway.dateserverandclient.Display;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by clouway on 15-12-21.
 */
public class DateServerTest {
    public class DisplayDate implements Display {
        String message;

        @Override
        public void setMessage(String message) {
            this.message = message;
            System.out.println(message);
        }
    }


    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery() {{
        setThreadingPolicy(new Synchroniser());
    }};

    @Mock
    Clock clock;

    private void pretendThatCurrentDateIs(final Date date) {
        context.checking(new Expectations() {{
            oneOf(clock).now();
            will(returnValue(date));
        }});
    }

    private DateServer dateServer = null;

    @Before
    public void startServer() {
        dateServer = new DateServer(8000, clock);
        dateServer.startServer();
    }


    @Test
    public void happyPath() {
        final DisplayDate displayDate = new DisplayDate();
        final Date date = new Date();
        pretendThatCurrentDateIs(date);
        final Client clientServer = new Client("localhost", 8000, displayDate);
        assertEquals(clientServer.connectClient(), "Hello! Current date: " + date);
    }

    @After
    public void stopServer() {
        dateServer.stopServer();
    }
}
