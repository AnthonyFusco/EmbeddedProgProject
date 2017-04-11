package com.project.unice.embeddedprogproject.fragments.views;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.project.unice.embeddedprogproject.R;
import com.project.unice.embeddedprogproject.fragments.AbstractFragment;
import com.project.unice.embeddedprogproject.fragments.FragmentManager;
import com.project.unice.embeddedprogproject.models.ContactManager;
import com.project.unice.embeddedprogproject.models.IContactManager;
import com.project.unice.embeddedprogproject.pages.Contact;

import java.util.List;


/**
 * Fragment displaying a list on Contact.
 * @see Contact
 * @see AbstractFragment
 */
public class ListContacts extends AbstractFragment {

    //titles of the fragments
    public static final String TOUS_TITLE = "TOUS";
    public static final String AVEC_TITLE = "AVEC";
    public static final String SANS_TITLE = "SANS";
    private ContactManager contactManager;

    public ListContacts() {
        super("CONTACTS", R.layout.page_list_contacts);
    }

    @Override
    protected void onCreateFragment(ViewGroup rootView, Bundle savedInstanceState) {
        initFragment(rootView);
    }

    /**
     * Init multiple Fragment to be displayed in the view.
     * @param rootView the container view
     */
    private void initFragment(ViewGroup rootView) {
        FragmentManager fragmentManager = new FragmentManager(getActivity().getSupportFragmentManager());

        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.container2);
        viewPager.setAdapter(fragmentManager);

        contactManager = new ContactManager(getActivity());
        List<Contact> contacts = contactsFactory("");

        fragmentManager.addFragment(new ViewContacts(TOUS_TITLE, contacts));
        fragmentManager.addFragment(new ViewContacts(AVEC_TITLE, contacts));
        fragmentManager.addFragment(new ViewContacts(SANS_TITLE, contacts));
        fragmentManager.notifyDataSetChanged();

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * Factory method used to decide which type of {@link IContactManager} to be used.
     * @param title filter for the SQL Query
     * @return the list of Contact
     */
    private List<Contact> contactsFactory(String title){
        //TODO en fonction de title pour filter les contacts
        return contactManager.getContacts(title);
    }
}
