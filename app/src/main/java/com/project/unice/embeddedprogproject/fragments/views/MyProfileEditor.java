package com.project.unice.embeddedprogproject.fragments.views;

import android.app.AlertDialog;
import android.content.ContentResolver;
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
import com.project.unice.embeddedprogproject.models.ContactManager;
import com.project.unice.embeddedprogproject.sqlite.DataBaseManager;
import com.project.unice.embeddedprogproject.sqlite.DataBaseTableManager;
import com.project.unice.embeddedprogproject.sqlite.IDatabaseManager;
import com.project.unice.embeddedprogproject.sqlite.IModel;

import java.util.ArrayList;
import java.util.List;

public class MyProfileEditor extends AbstractFragment {

    private MySharedPreferences mySharedPreferences;

    private static final String[] PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.Contacts.HAS_PHONE_NUMBER
    };

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
                //fetchConcactData();

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
                    Intent intent = new Intent(Intent.ACTION_INSERT,
                            ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(intent, 42);
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


    void fetchConcactData() {
        Long myId = getUserId(mySharedPreferences.getPhoneNumber());
        List<Contact> contacts = new ArrayList<>();
        //IDatabaseManager manager = new DataBaseTableManager(getActivity(), DataBaseManager.DATABASE_NAME);
        //List<IModel> contactsRecorded = manager.selectAll(Contact.class);
        ContentResolver cr = getActivity().getContentResolver();
        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, null, null, null);
        if (cursor != null) {
            try {
                final int id = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                final int idIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID);
                final int displayNameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                final int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                final int hasPhoneIndex = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
                //final int test = cursor.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_NAME);
                String phone;
                while (cursor.moveToNext()) {
                    if (cursor.getInt(hasPhoneIndex) > 0) {
                        phone = cursor.getString(phoneIndex);
                        Contact newContact = new Contact();
                        newContact.phone = phone;
                        newContact.setIdContactAndroid(cursor.getLong(idIndex));
                        System.out.println("AJOUT DE ==> " + newContact);
                        contacts.add(newContact);
                        Cursor c = getActivity().getContentResolver().query(
                                ContactsContract.RawContacts.CONTENT_URI,
                                new String[] { ContactsContract.RawContacts.CONTACT_ID, ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY, ContactsContract.RawContacts.ACCOUNT_TYPE },
                                ContactsContract.RawContacts.CONTACT_ID + "= ?",
                                new String[] { ""+idIndex },
                                null);

                        ArrayList<String> contactList = new ArrayList<>();
                        int contactNameColumn = c.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_TYPE);
                        while (c.moveToNext())
                        {
                            // You can also read RawContacts.CONTACT_ID to read the
                            // ContactsContract.Contacts table or any of the other related ones.
                            contactList.add(c.getString(contactNameColumn));
                        }
                        c.close();
                        for (String s : contactList) {
                            System.out.println(s);
                        }
                    }
                }
            } finally {
                cursor.close();
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fetchConcactData();
        //Long myId = getUserId(mySharedPreferences.getPhoneNumber());

        //Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, myId);

        //String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.Contacts.HAS_PHONE_NUMBER};

        //Cursor cursor = getActivity().getContentResolver().query(contactUri, projection, null, null, null);

        /*Cursor cursor = this.getMyActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, null, null, null);

        //assert cursor != null;
        cursor.moveToFirst();

        final int idIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID);
        final int displayNameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        final int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        final int hasPhoneIndex = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);

        // if (cursor.getInt(hasPhoneIndex) > 0) {
        while (cursor.moveToNext()) {
            Long number = cursor.getLong(idIndex);
            System.out.println(number);
        }
        // }


        cursor.close();*/


    }

}
