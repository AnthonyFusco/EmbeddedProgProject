package com.project.unice.embeddedprogproject.sms;

import android.telephony.SmsManager;

import com.google.gson.Gson;
import com.project.unice.embeddedprogproject.models.Contact;

public class Sender {
    public static final String HEADER = "{BUSINESS_CARD}";
    private static final Sender ourInstance = new Sender();

    public static Sender getInstance() {
        return ourInstance;
    }

    private Sender() {

    }

    public void send(Contact contact) {

        StringBuilder stringBuilder = new StringBuilder();
        Gson gson = new Gson();
        String contactSerialized = gson.toJson(contact);
        stringBuilder.append(HEADER).append(contactSerialized);

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(contact.phone, null, stringBuilder.toString(), null, null);
    }
}
