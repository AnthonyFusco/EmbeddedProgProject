package com.project.unice.embeddedprogproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.project.unice.embeddedprogproject.businessCardCreation.ViewBusinessCardActivity;
import com.project.unice.embeddedprogproject.sqlite.databaseModels.BusinessCard;
import com.project.unice.embeddedprogproject.sqlite.databaseModels.Contact;
import com.project.unice.embeddedprogproject.sms.Sender;


/**
 * Singleton Factory to choose which kind of onCLick to use after an itemCLick.
 */
public class OnClickContactFactory {
    private static final OnClickContactFactory ourInstance = new OnClickContactFactory();

    public static OnClickContactFactory getInstance() {
        return ourInstance;
    }

    private void sendCardAction(Contact contact, Context context) {
        boolean isSent = sendMyCard(contact, context);
        if (!isSent) {
            Toast.makeText(context, "Go to settings to create your Business Card first !", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * onClick action when the card is not known.
     * <p>Ask the user if he want to send a SMS to the selected Contact to get his Card</p>
     */
    private AdapterView.OnItemClickListener onClickNewMessage = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
            final Contact contact = (Contact) parent.getItemAtPosition(position);

            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            String textToSend;
            if (contact.name.isEmpty()) {
                textToSend = String.format("Send My Business Card to %s ?", contact.phone);
            } else {
                textToSend = String.format("Send My Business Card to %s ?", contact.name);
            }
            builder.setMessage(textToSend)
                    .setTitle("");

            builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    sendCardAction(contact, view.getContext());
                }
            });
            builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    };

    /**
     * onClick action when the card is known.
     * <p>Show the Card in a specific view or send your card</p>
     */
    private AdapterView.OnItemClickListener onClickWhenHaveCard = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(final AdapterView<?> parent, final View view, final int position, long id) {
            final Contact contact = (Contact) parent.getItemAtPosition(position);
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

            builder.setMessage("Choose an option")
                    .setTitle("");

            builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    sendCardAction(contact, view.getContext());
                }
            });
            builder.setNegativeButton("View", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    final Contact contact = (Contact) parent.getItemAtPosition(position);

                    Intent intent = new Intent(view.getContext(), ViewBusinessCardActivity.class);
                    Gson gson = new Gson();
                    String contactSerialized = gson.toJson(contact);
                    intent.putExtra(ViewBusinessCardActivity.CONTACT_INTENT_CODE, contactSerialized);

                    view.getContext().startActivity(intent);
                }
            });

            builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // User cancelled the dialog
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    };

    private boolean sendMyCard(Contact contact, Context context) {
        MySharedPreferences sharedPreferences = new MySharedPreferences(context);
        BusinessCard card = sharedPreferences.getBusinessCard();
        if (card == null) {
            return false;
        }
        Sender.getInstance().send(card, contact.phone);
        return true;
    }

    private OnClickContactFactory() {
    }

    /**
     * Prompt the user if he want to send a sms to the selected contact and send it if yes,
     * if the card is already set, show it.
     * @return the onClickListener
     */
    public AdapterView.OnItemClickListener getOnClickContactListener(AdapterView<?> parent, int position){
        Contact contact = (Contact) parent.getItemAtPosition(position);
        if (contact.idBusinessCard == -1) {
            return onClickNewMessage;
        }
        return onClickWhenHaveCard;
    }
}
