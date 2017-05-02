package com.project.unice.embeddedprogproject.fragments.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.project.unice.embeddedprogproject.MySharedPreferences;
import com.project.unice.embeddedprogproject.R;
import com.project.unice.embeddedprogproject.fragments.AbstractFragment;
import com.project.unice.embeddedprogproject.models.Contact;
import com.project.unice.embeddedprogproject.sqlite.DataBaseManager;
import com.project.unice.embeddedprogproject.sqlite.DataBaseTableManager;
import com.project.unice.embeddedprogproject.sqlite.IDatabaseManager;

public class MyProfileEditor extends AbstractFragment {

    public MyProfileEditor() {
        super("MY PROFILE", R.layout.my_profile_editor);
    }

    @Override
    protected void onCreateFragment(ViewGroup rootView, Bundle savedInstanceState) {
        Button edit = (Button)rootView.findViewById(R.id.buttonEditMyProfile);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer myId = getUserId();
                if (myId != null) {
                    Intent intent = new Intent(Intent.ACTION_EDIT, Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, String.valueOf(myId)));
                    startActivityForResult(intent, 10011);
                } else {
                    showNoNumberWarning();
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
     * @return the user android id
     */
    private Integer getUserId() {
        IDatabaseManager manager = new DataBaseTableManager(getActivity(), DataBaseManager.DATABASE_NAME);
        MySharedPreferences mySharedPreferences = new MySharedPreferences(getActivity());
        String phone = mySharedPreferences.getPhoneNumber();
        if (phone != null) {
            Contact c = (Contact)manager.findFirstValue(Contact.class, "Phone", phone);
            return c.idContactAndroid;
        }
        return null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
