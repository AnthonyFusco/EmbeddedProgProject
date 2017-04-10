package server.requests;

import server.interfaces.IRequest;

public enum RequestType {
    SEND("[SENDBUSINESSCARD]", new SendRequest()),
    GET("[GETBUSINESSCARD]", new GetRequest());
    //SENDANDGET("[SENDANDGETBUSINESSCARD]");

    private String type;
    private IRequest request;

    RequestType(String s, IRequest request) {
        this.type = s;
        this.request = request;
    }

    public String getType() {
        return type;
    }

    public IRequest getRequest() {
        return request;
    }
}
