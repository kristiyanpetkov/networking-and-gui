package com.clouway.multiclientserver;

import com.clouway.dateserverandclient.Clock;
import com.clouway.dateserverandclient.DateServer;

import java.util.Date;

/**
 * Created by clouway on 16-1-12.
 */
public class DemoServer {
    public static void main(String[] args) {
        Display display = new Display() {
            @Override
            public void setMessage(String message) {
                System.out.println(message);
            }
        };
        final Server server = new Server(4007);
        server.startAsync().awaitRunning();
    }
}
