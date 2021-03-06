package com.project.unice.embeddedprogproject.sqlite;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;

import com.google.gson.Gson;
import com.project.unice.embeddedprogproject.businessCardCreation.ChoicesBCActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserContactDatabaseManager implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int DATA_QUERY_ID = 0;
    public static final int ADDRESS_QUERY_ID = 1;
    private Activity activity;
    private boolean block;

    private static final String[] PROJECTION_DATA =
            new String[]{
                    ContactsContract.Data._ID,
                    ContactsContract.Data.MIMETYPE,
                    ContactsContract.Data.DATA1,
                    ContactsContract.Data.DATA2,
                    ContactsContract.Data.DATA3,
                    ContactsContract.Data.DATA4,
                    ContactsContract.Data.DATA5,
                    ContactsContract.Data.DATA6,
                    ContactsContract.Data.DATA7,
                    ContactsContract.Data.DATA8,
                    ContactsContract.Data.DATA9,
                    ContactsContract.Data.DATA10,
                    ContactsContract.Data.DATA11,
                    ContactsContract.Data.DATA12,
                    ContactsContract.Data.DATA13,
                    ContactsContract.Data.DATA14,
                    ContactsContract.Data.DATA15
            };

    private static final String[] PROJECTION_ADDRESS =
            new String[]{
                    ContactsContract.CommonDataKinds.StructuredPostal.STREET,
                    ContactsContract.CommonDataKinds.StructuredPostal.CITY,
                    ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY
            };
    private static final String SORT_ORDER = ContactsContract.Data.MIMETYPE;
    private static final String SELECTION = ContactsContract.Data.CONTACT_ID + " = ?";
    private String[] mSelectionArgs = {""};
    private long myId;
    private int query;

    public UserContactDatabaseManager(Activity activity, long myId) {
        this.activity = activity;
        this.myId = myId;
    }

    public void startQuery(int query) {
        this.query = query;
        // Initializes the loader framework
        if (activity.getLoaderManager() == null) {
            activity.getLoaderManager().initLoader(query, null, this);
        } else {
            activity.getLoaderManager().restartLoader(query, null, this);
        }
        block = false;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        mSelectionArgs[0] = String.valueOf(myId);

        CursorLoader dataLoader = new CursorLoader(
                activity,
                ContactsContract.Data.CONTENT_URI,
                PROJECTION_DATA,
                SELECTION,
                mSelectionArgs,
                SORT_ORDER
        );

        CursorLoader addressLoader = new CursorLoader(
                activity,
                ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
                PROJECTION_ADDRESS,
                SELECTION,
                mSelectionArgs,
                null
        );

        switch (query) {
            case DATA_QUERY_ID:
                return dataLoader;
            case ADDRESS_QUERY_ID:
                return addressLoader;
            default:
                return dataLoader;
        }
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (!block) {
            String phoneId = "Phone Number";
            String emailId = "Email";
            String organizationId = "Organization";
            String websiteId = "Website";
            String structuredNameId = "Name";
            String structuredPostalId = "Address";
            Map<String, List<String>> results = new HashMap<>();
            List<String> phones = new ArrayList<>();
            List<String> emails = new ArrayList<>();
            List<String> organizations = new ArrayList<>();
            List<String> websites = new ArrayList<>();
            List<String> structuredNames = new ArrayList<>();
            List<String> structuredPostals = new ArrayList<>();
            results.put(phoneId, phones);
            results.put(emailId, emails);
            results.put(organizationId, organizations);
            results.put(websiteId, websites);
            results.put(structuredNameId, structuredNames);
            results.put(structuredPostalId, structuredPostals);
            data.moveToFirst();
            while (data.moveToNext()) {
                String type = data.getString(1);
                switch (type) {
                    case ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE:
                        results.get(phoneId).add(data.getString(2));
                        break;
                    //can't seem to get it that way
                    /*case ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE:
                        results.get(emailId).add(data.getString(2));
                        break;*/
                    case ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE:
                        results.get(organizationId).add(data.getString(2));
                        break;
                    case ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE:
                        results.get(websiteId).add(data.getString(2));
                        break;
                    case ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE:
                        results.get(structuredNameId).add(data.getString(2));
                        break;
                    case ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE:
                        results.get(structuredPostalId).add(data.getString(2));
                        break;
                    default:
                        break;
                }
            }

            getEmail(emailId, results);
            if (results.get(structuredNameId).isEmpty()) {
                getName(structuredNameId, results);
            }

            block = true;

            Gson gson = new Gson();
            String mapSerial = gson.toJson(results);
            Intent intent = new Intent(activity, ChoicesBCActivity.class);
            intent.putExtra("Serial", mapSerial);
            activity.startActivity(intent);
        }
    }

    /**
     * Query the email database and add them to the map.<br />
     * Strangely if there was only one email in the contact, the previous query would not see it.
     * @param emailId the key of the map
     * @param results the map itself
     */
    private void getEmail(String emailId, Map<String, List<String>> results) {
        Cursor emailCur = activity.getContentResolver().query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                new String[]{String.valueOf(myId)}, null);
        assert emailCur != null;
        while (emailCur.moveToNext()) {
            String email = emailCur.getString(
                    emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

            results.get(emailId).add(email);
        }
        emailCur.close();
    }

    /**
     * Query the email database and add them to the map.<br />
     * Strangely if there was only one email in the contact, the previous query would not see it.
     * @param nameId the key of the map
     * @param results the map itself
     */
    private void getName(String nameId, Map<String, List<String>> results) {
        Cursor nameCur = activity.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                new String[]{String.valueOf(myId)}, null);
        assert nameCur != null;
        while (nameCur.moveToNext()) {
            String name = nameCur.getString(
                    nameCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            results.get(nameId).add(name);
        }
        nameCur.close();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
