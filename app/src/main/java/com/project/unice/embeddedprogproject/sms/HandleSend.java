package com.project.unice.embeddedprogproject.sms;

import android.telephony.SmsManager;

import com.google.gson.Gson;
import com.project.unice.embeddedprogproject.sqlite.databaseModels.BusinessCard;

import java.util.ArrayList;

public class HandleSend {
    public HandleSend() {
    }

    void sendMyCard(BusinessCard businessCard, String contactNumber) {
        StringBuilder stringBuilder = new StringBuilder();
        Gson gson = new Gson();
        String contactSerialized = gson.toJson(businessCard);
        String s1 = formatEmulator(Sender.HEADER);
        String s2 = formatEmulator(contactSerialized);
        stringBuilder.append(s1).append(s2);

        SmsManager smsManager = SmsManager.getDefault();

        ArrayList<String> parts = smsManager.divideMessage(stringBuilder.toString());
        smsManager.sendMultipartTextMessage(contactNumber, null, parts, null, null);
    }

    private String formatEmulator(String sms) {
        String s =  sms.replaceAll("\\{", "<");
        s = s.replaceAll("\\}", ">");
        s = s.replaceAll("\\[", "(");
        s = s.replaceAll("\\]", ")");
        s = s.replaceAll("\\\\", "/");
        return s;
    }
}