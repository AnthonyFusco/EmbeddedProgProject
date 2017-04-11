package com.project.unice.embeddedprogproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.project.unice.embeddedprogproject.models.Contact;
import com.project.unice.embeddedprogproject.sms.Sender;


/**
 * Singleton Factory to choose which kind of onCLick to use after an itemCLick.
 */
public class OnClickContactFactory {
    private static final OnClickContactFactory ourInstance = new OnClickContactFactory();

    public static OnClickContactFactory getInstance() {
        return ourInstance;
    }

    /**
     * onClick action when the card is not known.
     * <p>Ask the user if he want to send a SMS to the selected Contact to get his Card</p>
     */
    private AdapterView.OnItemClickListener onClickNewMessage = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final Contact contact = (Contact) parent.getItemAtPosition(position);

            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setMessage("Send Message ?")
                    .setTitle("");

            builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Sender.getInstance().send(contact);
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
     * <p>Show the Card in a specific view</p>
     */
    private AdapterView.OnItemClickListener onClickShowCard = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final Contact contact = (Contact) parent.getItemAtPosition(position);

            Intent intent = new Intent(view.getContext(), MyProfileActivity.class);
            Gson gson = new Gson();
            String contactSerialized = gson.toJson(contact);
            intent.putExtra(MyProfileActivity.CONTACT_INTENT_CODE, contactSerialized);

            view.getContext().startActivity(intent);
        }
    };

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
        return onClickShowCard;
    }
}
