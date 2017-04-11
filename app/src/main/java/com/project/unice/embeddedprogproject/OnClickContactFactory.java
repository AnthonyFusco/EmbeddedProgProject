package com.project.unice.embeddedprogproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;

import com.project.unice.embeddedprogproject.pages.Contact;

/**
 * Singleton Factory to choose which kind of onCLick to use after an itemCLick.
 */
public class OnClickContactFactory {
    private static final OnClickContactFactory ourInstance = new OnClickContactFactory();

    public static OnClickContactFactory getInstance() {
        return ourInstance;
    }

    private OnClickContactFactory() {
    }

    /**
     * Prompt the user if he want to send a sms to the selected contact and send it if yes.
     * @param context context of the app
     * @return the onClickListener
     */
    public AdapterView.OnItemClickListener getOnClickContactListener(final Context context){
        return new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Contact contact = (Contact) parent.getItemAtPosition(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Send Message ?")
                        .setTitle("");

                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(contact.phone, null, "catch", null, null);
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
    }
}
