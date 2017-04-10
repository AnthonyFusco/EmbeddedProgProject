package server.interfaces;

public interface IRequest {
    void execute();

    String getDatabaseRequest();

    void setDatabaseRequest(String message);
}
