package com.project.unice.embeddedprogproject.fragments.views;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.project.unice.embeddedprogproject.ViewHolder;
import com.project.unice.embeddedprogproject.fragments.AbstractFragment;
import com.project.unice.embeddedprogproject.LayoutListAdapter;
import com.project.unice.embeddedprogproject.R;
import com.project.unice.embeddedprogproject.pages.Contact;
import com.project.unice.embeddedprogproject.pages.ViewHolderContacts;

import java.util.ArrayList;
import java.util.List;


public class ViewContacts extends AbstractFragment {


    private final List<Contact> contacts;

    public ViewContacts(String title, List<Contact> contacts) {
        super(title, R.layout.fragment_listcontacts);
        this.contacts = contacts;
    }

    @Override
    protected void onCreateFragment(ViewGroup rootView, Bundle savedInstanceState) {
        ListView listView = (ListView)rootView.findViewById(R.id.listView_contacts);

        LayoutListAdapter<Contact> adapter = new LayoutListAdapter<>();
        adapter.setViewHolder(new ViewHolderContacts(contacts));
        adapter.setInflater(getLayoutInflater(savedInstanceState));
        adapter.setListElements(contacts);
        adapter.setLayout(R.layout.contact_list_row);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Contact contact = (Contact) parent.getItemAtPosition(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setMessage("Send Message ?")
                        .setTitle("");

                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(contact.phone, null, "sms message", null, null);
                    }
                });
                builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}
