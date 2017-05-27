package com.project.unice.embeddedprogproject.sms;

import android.content.BroadcastReceiver;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.project.unice.embeddedprogproject.models.BusinessCard;
import com.project.unice.embeddedprogproject.models.Contact;
import com.project.unice.embeddedprogproject.models.ContactManager;
import com.project.unice.embeddedprogproject.sqlite.DataBaseManager;
import com.project.unice.embeddedprogproject.sqlite.DataBaseTableManager;
import com.project.unice.embeddedprogproject.sqlite.IDatabaseManager;
import com.project.unice.embeddedprogproject.sqlite.IModel;

import android.provider.ContactsContract.Data;

import java.util.ArrayList;
import java.util.List;

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
        String phoneFormatted = ContactManager.formatPhone(businessCard.phone);
        List<IModel> models = manager.selectWhere(Contact.class, "Phone", phoneFormatted);
        for (IModel model : models) {
            System.out.println("received : " + phoneFormatted);
            Contact contact = (Contact) model;
            contact.idBusinessCard = businessCard.id;
            manager.update(contact);

            Intent in = new Intent();
            in.putExtra("sms", true);
            in.setAction("NOW");
            LocalBroadcastManager.getInstance(context).sendBroadcast(in);
        }

        if (models.isEmpty()) { //tentative d'ajout de contact
            Contact newContact = new Contact();
            newContact.phone = phoneFormatted;
            newContact.name = businessCard.name;
            newContact.idBusinessCard = businessCard.id;
            manager.add(newContact);

            Intent in = new Intent();
            in.putExtra("sms", true);
            in.setAction("NOW");
            LocalBroadcastManager.getInstance(context).sendBroadcast(in);

        }
    }

}
