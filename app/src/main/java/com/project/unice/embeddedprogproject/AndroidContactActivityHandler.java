package com.project.unice.embeddedprogproject;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.project.unice.embeddedprogproject.sqlite.DataBaseManager;
import com.project.unice.embeddedprogproject.sqlite.DataBaseTableManager;
import com.project.unice.embeddedprogproject.sqlite.IDatabaseManager;
import com.project.unice.embeddedprogproject.sqlite.UserContactDatabaseManager;
import com.project.unice.embeddedprogproject.sqlite.databaseModels.Contact;

public class AndroidContactActivityHandler {

    private MySharedPreferences mySharedPreferences;
    private Activity activity;

    public AndroidContactActivityHandler(Activity activity) {
        this.mySharedPreferences = new MySharedPreferences(activity);
        this.activity = activity;
    }

    /**
     * Fetch the android id in the database to open the correct contact in the contact app. <br />
     * Uses the user phone number.
     *
     * @return the user android id, -1 if not found
     */
    private Long getUserId(String phone) {
        IDatabaseManager manager = new DataBaseTableManager(activity, DataBaseManager.DATABASE_NAME);

        Contact c = new Contact();
        c.setIdContactAndroid(-1);
        if (phone != null) {
            c = (Contact) manager.findFirstValue(Contact.class, "Phone", phone);
        }

        if (c != null && c.idContactAndroid != null) {
            return c.idContactAndroid;
        } else {
            return (long) -1;
        }

    }

    public void checkStateForBusinessCardCreation() {
        if (mySharedPreferences.getPhoneNumber() == null) {
            Toast.makeText(activity, "Update your phone number in settings first !",
                    Toast.LENGTH_LONG).show();
        } else {
            startContactActivity();
        }
    }

    private void startContactActivity() {
        Long myId = getUserId(mySharedPreferences.getPhoneNumber());
        if (myId != -1) {
            Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, myId);
            Intent intent = new Intent(Intent.ACTION_EDIT);
            intent.setData(contactUri);
            intent.putExtra("finishActivityOnSaveCompleted", true);
            activity.startActivityForResult(intent, 42);
        } else {
            Toast.makeText(activity,
                    String.format("You need an existing contact with the number %s" +
                            " to create a Business Card", mySharedPreferences.getPhoneNumber()),
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Intent.ACTION_INSERT);
            intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

            intent.putExtra(ContactsContract.Intents.Insert.PHONE, mySharedPreferences.getPhoneNumber());

            intent.putExtra("finishActivityOnSaveCompleted", true);
            activity.startActivityForResult(intent, 43);

        }
    }

    public void androidContactActivityResult(int requestCode) {
        if(requestCode == 42) {
            Long userId = getUserId(mySharedPreferences.getPhoneNumber());
            if (userId == -1) {
                return;
            }
            UserContactDatabaseManager userContactDatabaseManager =
                    new UserContactDatabaseManager(activity, userId);
            userContactDatabaseManager.startQuery(UserContactDatabaseManager.DATA_QUERY_ID);
        }

        if (requestCode == 43) {
            Intent in = new Intent();
            in.putExtra("sms", true);
            in.setAction("NOW");
            LocalBroadcastManager.getInstance(activity).sendBroadcast(in);

            Toast.makeText(activity,
                    "You can now create your business card",
                    Toast.LENGTH_LONG).show();
        }
    }
}
