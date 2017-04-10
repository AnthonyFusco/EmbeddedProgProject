package server;

import server.interfaces.IRequestListener;
import server.interfaces.IRequestParser;

public class RequestListener implements IRequestListener {

    private IRequestParser requestParser;

    public RequestListener(IRequestParser requestParser) {
        this.requestParser = requestParser;
    }

    @Override
    public void onReceive(String received) {
        if (received.length() > 0) {
            requestParser.parse(received);
            System.out.println("received " + received);
        }
    }
}
