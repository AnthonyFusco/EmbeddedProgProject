package com.project.unice.embeddedprogproject.fragments.views;


import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.project.unice.embeddedprogproject.fragments.AbstractFragment;
import com.project.unice.embeddedprogproject.R;
import com.project.unice.embeddedprogproject.pages.Contact;

import java.util.ArrayList;
import java.util.List;


public class ViewContacts extends AbstractFragment {


    public ViewContacts(String title) {
        super(title, R.layout.fragment_listcontacts);
    }

    @Override
    protected void onCreateFragment(ViewGroup rootView, Bundle savedInstanceState) {
        ListView listView = (ListView)rootView.findViewById(R.id.listView_contacts);

        //LayoutListAdapter<Contact> adapter = new LayoutListAdapter<>();
        //adapter.setInflater(getLayoutInflater(savedInstanceState));
        //adapter.setListElements(getContacts());
        //adapter.setLayout(R.layout.contact_list_row);
        //adapter.setRowListView(new ListContacts());

        //listView.setAdapter(adapter);

        listView.setAdapter(new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_list_item_1,
                getContacts()));
    }


    private List<String> getContacts(){
        ContentResolver cr = getActivity().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        List<String> l = new ArrayList<>();
        if (cur != null && cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
                Contact c = new Contact();
                c.name = name;
                l.add(c.name);
                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    if (pCur != null) {
                        while (pCur.moveToNext()) {
                            String phoneNo = pCur.getString(pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
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
