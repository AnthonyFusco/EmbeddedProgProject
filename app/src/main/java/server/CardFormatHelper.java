package server;

import server.interfaces.IBusinessCard;
import server.interfaces.ICardFormatHelper;
import server.interfaces.ISender;

public class CardFormatHelper implements ICardFormatHelper {

    private static CardFormatHelper instance = new CardFormatHelper();

    private CardFormatHelper(){}

    public static CardFormatHelper getInstance(){
        return instance;
    }

    @Override
    public String format(IBusinessCard card) {
        return card.toString(); //actually not that but something like an actual formatted string
    }
}
