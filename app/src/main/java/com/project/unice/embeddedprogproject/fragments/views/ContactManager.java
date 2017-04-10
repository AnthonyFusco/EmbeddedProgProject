package com.project.unice.embeddedprogproject.fragments.views;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.project.unice.embeddedprogproject.pages.Contact;

import java.util.ArrayList;
import java.util.List;

class ContactManager implements IContactManager {

    private Context activity;

    ContactManager(Context activity) {
        this.activity = activity;
    }

    @Override
    public List<Contact> getContacts(String title) {
        //TODO en fonction de title pour filter les contacts
        ContentResolver cr = activity.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        List<Contact> l = new ArrayList<Contact>();
        if (cur != null && cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
                Contact c = new Contact();
                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    if (pCur != null) {
                        while (pCur.moveToNext()) {
                            c.name = name;
                            c.phone = pCur.getString(pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
                            l.add(c);
                        }
                        pCur.close();
                    }
                }
            }
            cur.close();
        }
        return l;
    }
}