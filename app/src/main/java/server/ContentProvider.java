package server;

import server.interfaces.IBusinessCard;
import server.interfaces.ICardFormatHelper;
import server.interfaces.IContentProvider;
import server.interfaces.IRequest;

public class ContentProvider implements IContentProvider {

    private static IContentProvider instance = new ContentProvider();

    private ContentProvider(){}

    public static IContentProvider getInstance() {
        return instance;
    }

    private ICardFormatHelper cardFormatHelper;

    @Override
    public Object execute(String databaseRequest) {
        //request.execute();
        //send or save it

        //EXECUTE THE DATABASE REQUEST
        //SOMETHING LIKE THAT
        //I DON'T KNOW

        //LEAVE ME ALONE

        cardFormatHelper.format(new IBusinessCard() {
            @Override
            public int getId() {
                return 0;
            }

            @Override
            public void setId(int id) {

            }

            @Override
            public int getName() {
                return 0;
            }

            @Override
            public void setName(int name) {

            }

            @Override
            public String getNumber() {
                return null;
            }

            @Override
            public void setNumber(String number) {

            }
        });//WHAT THE HELL WAS THAT FOR



        return new Object();
        //AAAAAH
    }
}


//IM TYPING WORDS WITH MY HANDS