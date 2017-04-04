package com.project.unice.embeddedprogproject.pages;


import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.ViewGroup;
import android.widget.ListView;

import com.project.unice.embeddedprogproject.AbstractFragment;
import com.project.unice.embeddedprogproject.LayoutListAdapter;
import com.project.unice.embeddedprogproject.R;

import java.util.ArrayList;
import java.util.List;


public class ViewContactsFragment extends AbstractFragment {


    public ViewContactsFragment(String title) {
        super(title, R.layout.fragment_listcontacts);
    }

    @Override
    protected void onCreateFragment(ViewGroup rootView, Bundle savedInstanceState) {
        ListView listView = (ListView)rootView.findViewById(R.id.listView_contacts);

        LayoutListAdapter<Contact> adapter = new LayoutListAdapter<>();
        adapter.setInflater(getLayoutInflater(savedInstanceState));
        adapter.setListElements(getContacts());
        adapter.setLayout(R.layout.contact_list_row);
        adapter.setRowListView(new ListContactView());
        listView.setAdapter(adapter);
    }


    private List<Contact> getContacts(){
        ContentResolver cr = getActivity().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        List<Contact> l = new ArrayList<>();
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
                Contact c = new Contact();
                c.name = name;
                l.add(c);
                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }
                    pCur.close();
                }
            }
        }
        return l;
    }
}
