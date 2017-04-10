package server;

import server.interfaces.ISender;

public class Sender implements ISender {
    @Override
    public void send(String toSend) {
        System.out.println(toSend);
    }
}
