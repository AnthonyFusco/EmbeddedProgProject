package com.project.unice.embeddedprogproject;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.webkit.MimeTypeMap;

import com.project.unice.embeddedprogproject.models.Contact;
import com.project.unice.embeddedprogproject.sqlite.DataBaseManager;
import com.project.unice.embeddedprogproject.sqlite.DataBaseTableManager;
import com.project.unice.embeddedprogproject.sqlite.IDatabaseManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserContactDatabaseManager implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int DATA_QUERY_ID = 0;
    public static final int ADRESS_QUERY_ID = 1;
    private Activity activity;
    private Map<String, List<String>> results;

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
    private String phone;
    private int query;

    public UserContactDatabaseManager(Activity activity, String phone) {
        this.activity = activity;
        this.phone = phone;
    }

    public Map<String, List<String>> startQuery(int query) {
        this.query = query;
        // Initializes the loader framework
        activity.getLoaderManager().initLoader(query, null, this);
        return results;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        mSelectionArgs[0] = String.valueOf(getUserId(phone));

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
            case ADRESS_QUERY_ID:
                return addressLoader;
            default:
                return dataLoader;
        }
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        String phoneId = "Phone";
        String emailId = "Email";
        String organizationId = "Organization";
        String websiteId = "Website";
        String structuredNameId = "StructuredName";
        String structuredPostalId = "StructuredPostal";
        results = new HashMap<>();
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
        while (data.moveToNext()) {
            String type = data.getString(1);
            switch (type) {
                case ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE:
                    results.get(phoneId).add(data.getString(2));
                    break;
                case ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE:
                    results.get(emailId).add(data.getString(2));
                    break;
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
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

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
}
