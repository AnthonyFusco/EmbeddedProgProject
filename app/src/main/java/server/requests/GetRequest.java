package server.requests;

import server.ContentProvider;
import server.interfaces.IContentProvider;

public class GetRequest extends AbstractRequest {

    @Override
    public void execute() {
        //dunno if we should use a content provider to add something in the database
        IContentProvider contentProvider = ContentProvider.getInstance();
        contentProvider.execute(getDatabaseRequest());
    }
}
