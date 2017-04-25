package com.project.unice.embeddedprogproject.sms;

import android.telephony.SmsManager;

import com.google.gson.Gson;
import com.project.unice.embeddedprogproject.models.BusinessCard;
import com.project.unice.embeddedprogproject.sqlite.IModel;

/**
 * Encapsulation for the action of sending a business card.
 */
public class Sender implements ISender{
    public static final String HEADER = "{BUSINESS_CARD}";
    private static final Sender ourInstance = new Sender();

    public static Sender getInstance() {
        return ourInstance;
    }

    private Sender() {

    }

    public void send(IModel iModel) {
        sendMyCard((BusinessCard) iModel);
    }

    private void sendMyCard(BusinessCard businessCard) {
        StringBuilder stringBuilder = new StringBuilder();
        Gson gson = new Gson();
        String contactSerialized = gson.toJson(businessCard);
        stringBuilder.append(HEADER).append(contactSerialized);

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(businessCard.phone, null, stringBuilder.toString(), null, null);
    }

    private void askForCard(IModel iModel) {

    }
}
