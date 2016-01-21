package com.clouway.multiclientserver;

import com.clouway.dateserverandclient.Clock;
import com.clouway.dateserverandclient.DateServer;

import java.util.Date;

/**
 * Created by clouway on 16-1-12.
 */
public class Demo {
    public static void main(String[] args) {
        Display display = new Display() {
            @Override
            public void setMessage(String message) {
                System.out.println(message);
            }
        };
        final Server server = new Server(4007);
        server.startAsync().awaitRunning();
//        Clock clock = new Clock() {
//            @Override
//            public Date now() {
//                return new Date();
//            }
//        };
//
//        DateServer dateServer = new DateServer(4007,clock);
//        dateServer.startServer();
    }
}
