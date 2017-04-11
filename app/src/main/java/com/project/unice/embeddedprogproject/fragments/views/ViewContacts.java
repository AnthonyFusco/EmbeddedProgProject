package com.project.unice.embeddedprogproject.fragments.views;


import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.project.unice.embeddedprogproject.LayoutListAdapter;
import com.project.unice.embeddedprogproject.OnClickContactFactory;
import com.project.unice.embeddedprogproject.R;
import com.project.unice.embeddedprogproject.fragments.AbstractFragment;
import com.project.unice.embeddedprogproject.models.Contact;
import com.project.unice.embeddedprogproject.pages.ViewHolderContacts;

import java.util.Collection;
import java.util.List;


/**
 * Fragment displaying all the contacts.
 * Add a listener to the items.
 */
public class ViewContacts extends AbstractFragment {

    private final List<Contact> contacts;

    public ViewContacts(String title, List<Contact> contacts) {
        super(title, R.layout.fragment_listcontacts);
        this.contacts = contacts;
    }

    @Override
    protected void onCreateFragment(ViewGroup rootView, Bundle savedInstanceState) {
        ListView listView = (ListView)rootView.findViewById(R.id.listView_contacts);

        initListView(savedInstanceState, listView);

        //listener on the list items. Use a factory class to decide which on click used according to the database
        // //The OnClick can be send a card request, visualize an existing one, ...
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OnClickContactFactory.getInstance().getOnClickContactListener(getActivity()).onItemClick(parent, view, position, id);
            }
        });
    }

    /**
     * Initialize the view.
     * Inflate the contacts elements.
     * @param savedInstanceState
     * @param listView
     */
    private void initListView(Bundle savedInstanceState, ListView listView) {
        LayoutListAdapter<Contact> adapter = new LayoutListAdapter<>();
        adapter.setViewHolder(new ViewHolderContacts(contacts));
        adapter.setInflater(getLayoutInflater(savedInstanceState));
        adapter.setListElements(contacts);
        adapter.setLayout(R.layout.contact_list_row);
        listView.setAdapter(adapter);
    }
}
