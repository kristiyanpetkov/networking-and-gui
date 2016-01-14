package com.clouway.multiclientserver;

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
        server.run();
    }
}
