package server.requests;

import server.interfaces.IRequest;

public abstract class AbstractRequest implements IRequest {

    private String databaseRequest;

    public AbstractRequest() {
        this("");
    }

    public AbstractRequest(String message) {
        this.databaseRequest = message;
    }

    @Override
    public String getDatabaseRequest() {
        return this.databaseRequest;
    }

    @Override
    public void setDatabaseRequest(String message) {
        this.databaseRequest = message;
    }
}
