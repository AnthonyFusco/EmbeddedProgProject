package com.project.unice.embeddedprogproject.models;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.project.unice.embeddedprogproject.sqlite.DataBaseManager;
import com.project.unice.embeddedprogproject.sqlite.DataBaseTableManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link IContactManager} which return the contact list with a possibility of SQL query.
 */
public class ContactManager implements IContactManager {

    private Context activity;

    public ContactManager(Context activity) {
        this.activity = activity;
    }

    /**
     * Call the ContentResolver of the Contact application and return the list of Contact.
     * @see Contact
     * @return the list of Contacts
     */
    @Override
    public List<Contact> getContacts() {
        //TODO en fonction de title pour filter les contacts
        //get the contact resolver
        ContentResolver cr = activity.getContentResolver();
        //Query the contact application for a cursor to the contact lists
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        //create the contact list
        List<Contact> l = new ArrayList<Contact>();
        if (cur != null && cur.getCount() > 0) {
            //for all the contacts
            while (cur.moveToNext()) {
                //get its id
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                //get the name of the contact
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
                //create the contact object

                //An other query is required for the phone number
                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    //if the number is not empty
                    if (pCur != null) {
                        while (pCur.moveToNext()) {
                            //add the information of the contact the the contact list
                            DataBaseTableManager manager = new DataBaseTableManager(activity, DataBaseManager.DATABASE_NAME);
                            Contact c = (Contact) manager.first(Contact.class, "IdContactAndroid", id);
                            if (c == null){
                                //le contact n'est pas dans la bdd sqlite donc on l'ajoute
                                c = new Contact();
                                c.name = name;
                                c.phone = pCur.getString(pCur.getColumnIndex(
                                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                                c.idContactAndroid = Integer.valueOf(id);
                                c.idBusinessCard = -1;
                                manager.add(c);
                                System.out.println("AJOUT DE ==> " + c);
                            }
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