package com.project.unice.embeddedprogproject.sqlite.databaseModels;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.telephony.PhoneNumberUtils;
import android.util.Log;

import com.project.unice.embeddedprogproject.sqlite.DataBaseManager;
import com.project.unice.embeddedprogproject.sqlite.DataBaseTableManager;
import com.project.unice.embeddedprogproject.sqlite.IDatabaseManager;
import com.project.unice.embeddedprogproject.sqlite.IModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;


/**
 * Implementation of {@link IContactManager} which return the contact list with a possibility of SQL query.
 */
public class ContactManager implements IContactManager {

    private Context activity;
    private ArrayList<IModel> contacts;

    private static final String[] PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.Contacts.HAS_PHONE_NUMBER
    };

    public ContactManager(Context activity) {
        this.activity = activity;
    }

    @Override
    public List<IModel> getContacts() {
        checkAndroidContactList();
        Collections.sort(contacts, new Comparator<IModel>() {
            @Override
            public int compare(IModel lhs, IModel rhs) {
                if (((Contact)lhs).name == null || ((Contact)rhs).name == null) {
                    return 0;
                }
                return ((Contact)lhs).name.compareTo(((Contact)rhs).name);
            }
        });
        return new ArrayList<>(contacts);
    }

    /**
     * Call the ContentResolver of the Contact application and add the new contact in the internal database.
     *
     * @see Contact
     */
    private void checkAndroidContactList() {
        contacts = new ArrayList<>();
        IDatabaseManager manager = new DataBaseTableManager(activity, DataBaseManager.DATABASE_NAME);
        List<IModel> contactsRecorded = manager.selectAll(Contact.class);
        ContentResolver cr = activity.getContentResolver();
        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, null, null, null);
        for (IModel model : contactsRecorded) {
            Contact contact = ((Contact) model);
            contacts.add(contact);
        }
        if (cursor != null) {
            try {
                final int idIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID);
                final int displayNameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                final int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                final int hasPhoneIndex = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
                String phone;
                while (cursor.moveToNext()) {
                    if (cursor.getInt(hasPhoneIndex) > 0) {
                        phone = cursor.getString(phoneIndex);
                        Contact newContact = new Contact();
                        newContact.phone = formatPhone(phone);
                        newContact.setIdContactAndroid(cursor.getLong(idIndex));
                        if (!contactsRecorded.contains(newContact)){
                            //le contact n'est pas dans la bdd sqlite donc on l'ajoute
                            newContact.idBusinessCard = -1;
                            String nameSave = newContact.name;
                            newContact.name = null; //so ugly
                            manager.add(newContact);
                            newContact.name = nameSave;
                            contactsRecorded.add(newContact);
                            System.out.println("AJOUT DE ==> " + newContact);
                        }
                        else {
                            newContact = (Contact) contactsRecorded.get(contactsRecorded.indexOf(newContact));
                        }
                        newContact.name = cursor.getString(displayNameIndex);
                        if (!contacts.contains(newContact)) {
                            contacts.add(newContact);
                        }
                    }
                }

            }catch (Exception e) {
                Log.d("ContactManager", "checkAndroidContactList: " + e);
            } finally {
                cursor.close();
            }
        }
    }

    public static String formatPhone(String phone) {
        String formatted = "";
        if (!phone.isEmpty()) {
            formatted = PhoneNumberUtils.formatNumber(phone, Locale.getDefault().getCountry());
            if (formatted != null && formatted.length() > 0) {
                formatted = formatted.replaceAll("\\+[0-9]+\\s", "0").replaceAll("^00 33 ", "0");
            } else {
                formatted = phone;
            }
        }
        return formatted;
    }
}