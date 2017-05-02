package com.project.unice.embeddedprogproject.fragments.views;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
                Long myId = getUserId();
                if (myId != null) {
                    /*Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                    Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
                    long idContact = cursor.getLong(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));*/
                    Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, myId);
                    Intent intent = new Intent(Intent.ACTION_EDIT);
                    intent.setData(contactUri);
                    startActivityForResult(intent, 42   );
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
    private Long getUserId() {
        IDatabaseManager manager = new DataBaseTableManager(getActivity(), DataBaseManager.DATABASE_NAME);
        MySharedPreferences mySharedPreferences = new MySharedPreferences(getActivity());
        String phone = mySharedPreferences.getPhoneNumber();
        Contact c;
        if (phone != null) {
            c = (Contact)manager.findFirstValue(Contact.class, "Phone", phone);
            return c.idContactAndroid;
        } else {
            c = (Contact)manager.findFirstValue(Contact.class, "Phone", "06 22 75 04 66");
        }
        //return null;

        return c.idContactAndroid;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
