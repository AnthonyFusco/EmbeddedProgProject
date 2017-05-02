package com.project.unice.embeddedprogproject.sms;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Telephony;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.project.unice.embeddedprogproject.fragments.views.ViewContacts;
import com.project.unice.embeddedprogproject.models.BusinessCard;
import com.project.unice.embeddedprogproject.models.Contact;
import com.project.unice.embeddedprogproject.models.ContactManager;
import com.project.unice.embeddedprogproject.sqlite.DataBaseManager;
import com.project.unice.embeddedprogproject.sqlite.DataBaseTableManager;
import com.project.unice.embeddedprogproject.sqlite.IDatabaseManager;
import com.project.unice.embeddedprogproject.sqlite.IModel;

import java.util.Locale;

/**
 * Parse a received SMS.
 */
public class SmsListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                String messageBody = smsMessage.getMessageBody();
                if (messageBody.contains(Sender.HEADER)) {
                    Log.e("log>>>", "received business card");
                    //int id = smsMessage.getIndexOnIcc();
                    handleReceive(context, messageBody);
                }
            }
        }
    }

    private void handleReceive(Context context, String body) {
        String businessCardSerialized = body.substring(body.indexOf(Sender.HEADER) + Sender.HEADER.length());
        Toast.makeText(context, businessCardSerialized, Toast.LENGTH_SHORT).show();
        Gson gson = new Gson();
        BusinessCard businessCard = gson.fromJson(businessCardSerialized, BusinessCard.class);
        IDatabaseManager manager = new DataBaseTableManager(context, DataBaseManager.DATABASE_NAME);
        manager.add(businessCard);
        for (IModel model : manager.selectWhere(Contact.class, "Phone", ContactManager.formatPhone(businessCard.phone))) {
            System.out.println("received : " + ContactManager.formatPhone(businessCard.phone));
            Contact contact = (Contact)model;
            contact.idBusinessCard = businessCard.id;
            manager.update(contact);
        }
    }

}
