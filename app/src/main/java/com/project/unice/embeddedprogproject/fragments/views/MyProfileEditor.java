package com.project.unice.embeddedprogproject.fragments.views;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts.Data;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.project.unice.embeddedprogproject.MySharedPreferences;
import com.project.unice.embeddedprogproject.R;
import com.project.unice.embeddedprogproject.UserContactDatabaseManager;
import com.project.unice.embeddedprogproject.fragments.AbstractFragment;
import com.project.unice.embeddedprogproject.models.Contact;
import com.project.unice.embeddedprogproject.sqlite.DataBaseManager;
import com.project.unice.embeddedprogproject.sqlite.DataBaseTableManager;
import com.project.unice.embeddedprogproject.sqlite.IDatabaseManager;

import java.util.List;
import java.util.Map;

public class MyProfileEditor extends AbstractFragment {

    private MySharedPreferences mySharedPreferences;

    public MyProfileEditor() {
        super("MY PROFILE", R.layout.my_profile_editor);
    }

    @Override
    protected void onCreateFragment(ViewGroup rootView, Bundle savedInstanceState) {
        Button edit = (Button) rootView.findViewById(R.id.buttonEditMyProfile);

        mySharedPreferences = new MySharedPreferences(getActivity());
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = mySharedPreferences.getPhoneNumber();
                if (phone == null) {
                    showNoNumberWarning();
                    return;
                }
                Long myId = getUserId(phone);
                if (myId != -1) {
                    Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, myId);
                    Intent intent = new Intent(Intent.ACTION_EDIT);
                    intent.setData(contactUri);
                    intent.putExtra("finishActivityOnSaveCompleted", true);
                    startActivityForResult(intent, 42);
                } else {
                    Toast.makeText(getActivity(),
                            String.format("You need an existing contact with the number %s" +
                                    " to create a Business Card", phone), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Inform the user that he needs to add a phone number to continue.
     */
    private void showNoNumberWarning() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String textToSend = "Please add your phone number in Settings to create a Business Card";
        builder.setMessage(textToSend)
                .setTitle("");
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Fetch the android id in the database to open the correct contact in the contact app. <br />
     * Uses the user phone number.
     *
     * @return the user android id, -1 if not found
     */
    private Long getUserId(String phone) {
        IDatabaseManager manager = new DataBaseTableManager(getActivity(), DataBaseManager.DATABASE_NAME);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 42) {
            Long userId = getUserId(mySharedPreferences.getPhoneNumber());
            if (userId == -1) {
                return;
            }
            UserContactDatabaseManager userContactDatabaseManager = new UserContactDatabaseManager(getActivity(), userId);
            userContactDatabaseManager.startQuery(UserContactDatabaseManager.DATA_QUERY_ID);
        }
    }

}
