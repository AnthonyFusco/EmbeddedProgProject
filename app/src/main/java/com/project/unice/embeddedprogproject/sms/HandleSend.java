package com.project.unice.embeddedprogproject.sms;

import android.telephony.SmsManager;

import com.google.gson.Gson;
import com.project.unice.embeddedprogproject.models.BusinessCard;

public class HandleSend {
    public HandleSend() {
    }

    void sendMyCard(BusinessCard businessCard, String contactNumber) {
        StringBuilder stringBuilder = new StringBuilder();
        Gson gson = new Gson();
        String contactSerialized = gson.toJson(businessCard);
        stringBuilder.append(Sender.HEADER).append(contactSerialized);

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(contactNumber, null, stringBuilder.toString(), null, null);
    }
}