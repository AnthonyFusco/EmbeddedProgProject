package com.project.unice.embeddedprogproject.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.google.gson.Gson;
import com.project.unice.embeddedprogproject.sqlite.databaseModels.BusinessCard;
import com.project.unice.embeddedprogproject.sqlite.databaseModels.Contact;
import com.project.unice.embeddedprogproject.sqlite.databaseModels.ContactManager;
import com.project.unice.embeddedprogproject.sqlite.DataBaseManager;
import com.project.unice.embeddedprogproject.sqlite.DataBaseTableManager;
import com.project.unice.embeddedprogproject.sqlite.IDatabaseManager;
import com.project.unice.embeddedprogproject.sqlite.databaseModels.IModel;

import java.util.List;

/**
 * Parse a received SMS.
 */
public class SmsListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] pdus = (Object[])bundle.get("pdus");
            final SmsMessage[] messages = new SmsMessage[pdus != null ? pdus.length : 0];
            for (int i = 0; i < (pdus != null ? pdus.length : 0); i++) {
                messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
            }
            StringBuilder content = new StringBuilder();
            if (messages.length > 0) {
                for (SmsMessage message : messages) {
                    content.append(message.getMessageBody());
                }
            }
            String mySmsText = content.toString();
            if (mySmsText.contains(Sender.HEADER_EMULATOR)){
                String format = formatReceiveEmulator(mySmsText);
                handleReceive(context, format);
            }
        }
    }

    private String formatReceiveEmulator(String sms) {
        return sms.replaceAll("<", "{").replaceAll(">", "}")
                .replaceAll("\\(", "[").replaceAll("\\)", "]").replaceAll("/", "\\\\");
    }

    private void handleReceive(Context context, String body) {
        String businessCardSerialized = body.substring(body.indexOf(Sender.HEADER) + Sender.HEADER.length());
        Toast.makeText(context, businessCardSerialized, Toast.LENGTH_SHORT).show();
        Gson gson = new Gson();
        BusinessCard businessCard = gson.fromJson(businessCardSerialized, BusinessCard.class);
        IDatabaseManager manager = new DataBaseTableManager(context, DataBaseManager.DATABASE_NAME);



        String phoneFormatted = ContactManager.formatPhone(businessCard.phone);

        BusinessCard card = (BusinessCard) manager.findFirstValue(BusinessCard.class, "Phone", phoneFormatted);
        if (card != null) {
            manager.remove(card);
        }
        manager.add(businessCard);
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
