package server;

import server.interfaces.IRequest;
import server.interfaces.IRequestParser;
import server.requests.RequestType;

public class RequestParser implements IRequestParser{
    private static RequestParser ourInstance = new RequestParser();

    public static RequestParser getInstance() {
        return ourInstance;
    }

    //private IContentProvider contentProvider;

    private RequestParser() {
       // contentProvider = new ContentProvider();
    }

    @Override
    public void parse(String request) {
        IRequest requestToExecute;
        if (request.startsWith(RequestType.SEND.getType())) {
            requestToExecute = RequestType.SEND.getRequest();
        } else if (request.startsWith(RequestType.GET.getType()))  {
            requestToExecute = RequestType.GET.getRequest();
        } else {
            System.out.println("NOT RECOGNIZED MESSAGE TYPE");
            return;
        }
        requestToExecute.setDatabaseRequest(getDatabaseRequest(request));
        requestToExecute.execute();
        //contentProvider.execute(requestToExecute);
    }

    @Override
    public String getDatabaseRequest(String message) {
        return null;
    }

   /* @Override
    public IRequest createRequest(String type) {
        if (type.length() > 0) {
            return () -> System.out.println("System.out.println(\"\");");
        } else {
            return () -> System.out.println("public void execute()");
        }
    }*/
}
