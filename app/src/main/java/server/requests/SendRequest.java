package server.requests;

import server.CardFormatHelper;
import server.ContentProvider;
import server.Sender;
import server.interfaces.IBusinessCard;
import server.interfaces.ICardFormatHelper;
import server.interfaces.IContentProvider;
import server.interfaces.ISender;

public class SendRequest extends AbstractRequest {

    private static final String GET_PERSONAL_CARD_REQUEST = "GET ME MY BUSINESS CARD RIGHT NOW OR I SWEAR TO GOD IM GONNA DPZINEvsdvPZIsdfvsENF";

    @Override
    public void execute() {
        IContentProvider contentProvider = ContentProvider.getInstance();
        ICardFormatHelper cardFormatHelper = CardFormatHelper.getInstance();

        IBusinessCard businessCard = (IBusinessCard) contentProvider.execute(GET_PERSONAL_CARD_REQUEST);

        ISender sender = new Sender();
        sender.send(cardFormatHelper.format(businessCard));
    }
}
