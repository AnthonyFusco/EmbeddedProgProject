package server.interfaces;

public interface IRequestParser {
    void parse(String request);

    String getDatabaseRequest(String message);

   // IRequest createRequest(String type);

}
