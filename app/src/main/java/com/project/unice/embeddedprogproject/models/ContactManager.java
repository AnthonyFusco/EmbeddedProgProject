package com.project.unice.embeddedprogproject.models;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.project.unice.embeddedprogproject.sqlite.DataBaseManager;
import com.project.unice.embeddedprogproject.sqlite.DataBaseTableManager;
import com.project.unice.embeddedprogproject.sqlite.IModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link IContactManager} which return the contact list with a possibility of SQL query.
 */
public class ContactManager implements IContactManager {

    private Context activity;
    private ArrayList<IModel> contacts;

    public ContactManager(Context activity) {
        this.activity = activity;
    }


    @Override
    public List<IModel> getContacts() {
        checkAndroidContactList();
        //DataBaseTableManager manager = new DataBaseTableManager(activity, DataBaseManager.DATABASE_NAME);
        //return manager.selectAll(Contact.class);
        return new ArrayList<>(contacts);
    }

    /**
     * Call the ContentResolver of the Contact application and add the new contact in the internal database.
     *
     * @see Contact
     */
    private void checkAndroidContactList() {
        contacts = new ArrayList<>();
        //get the contact resolver
        ContentResolver cr = activity.getContentResolver();
        //Query the contact application for a cursor to the contact lists
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        //create the contact list
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
                            //add the information of the contact the contact list
                            DataBaseTableManager manager = new DataBaseTableManager(activity, DataBaseManager.DATABASE_NAME);
                            Contact newContact = (Contact) manager.findFirstValue(Contact.class, "IdContactAndroid", id);
                            //if (newContact == null){
                            //le contact n'est pas dans la bdd sqlite donc on l'ajoute
                            newContact = new Contact();
                            newContact.name = name;
                            newContact.phone = pCur.getString(pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
                            newContact.idContactAndroid = Integer.valueOf(id);
                            newContact.idBusinessCard = -1;
                            manager.add(newContact);
                            contacts.add(newContact);
                            System.out.println("AJOUT DE ==> " + newContact);
                            // }
                        }
                        pCur.close();
                    }
                }
            }
            cur.close();
        }
    }
}