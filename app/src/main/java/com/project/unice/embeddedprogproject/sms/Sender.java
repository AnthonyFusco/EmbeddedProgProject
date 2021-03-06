package com.project.unice.embeddedprogproject.sms;

import com.project.unice.embeddedprogproject.sqlite.databaseModels.BusinessCard;
import com.project.unice.embeddedprogproject.sqlite.databaseModels.IModel;

/**
 * Encapsulation for the action of sending a business card.
 */
public class Sender implements ISender{
    public static final String HEADER = "{BUSINESS_CARD}";
    public static final String HEADER_EMULATOR = "<BUSINESS_CARD>";
    private static final Sender ourInstance = new Sender();
    private final HandleSend handleSend = new HandleSend();

    public static Sender getInstance() {
        return ourInstance;
    }

    private Sender() {

    }

    public void send(IModel iModel, String contactNumber) {
        sendMyCard((BusinessCard) iModel, contactNumber);
    }

    private void sendMyCard(BusinessCard businessCard, String contactNumber) {
        handleSend.sendMyCard(businessCard, contactNumber);
    }

}
