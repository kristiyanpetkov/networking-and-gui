package com.clouway.multiclientserver;

/**
 * Created by clouway on 16-1-12.
 */
public class DemoClient {
    public static void main(String[] args) {
        Display display = new Display() {
            @Override
            public void setMessage(String message) {
                System.out.println(message);
            }
        };
        Client client = new Client("localhost",4007,display);
        client.connect();
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
