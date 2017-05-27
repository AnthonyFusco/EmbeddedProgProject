package com.project.unice.embeddedprogproject.fragments.views;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.project.unice.embeddedprogproject.R;
import com.project.unice.embeddedprogproject.fragments.AbstractFragment;
import com.project.unice.embeddedprogproject.fragments.FragmentManager;
import com.project.unice.embeddedprogproject.databaseModels.Contact;
import com.project.unice.embeddedprogproject.databaseModels.ContactManager;
import com.project.unice.embeddedprogproject.databaseModels.IContactManager;
import com.project.unice.embeddedprogproject.sqlite.IModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Fragment displaying a list on Contact.
 *
 * @see Contact
 * @see AbstractFragment
 */
public class ListContacts extends AbstractFragment {

    //titles of the fragments
    public static final String All_CONTACT_TITLE = "All Contacts";
    public static final String WITH_BUSINESS_CARD_TITLE = "With Card";
    public static final String WITHOUT_BUSINESS_CARD_TITLE = "Without Card";
    private ContactManager contactManager;
    private ViewContacts v1;
    private ViewContacts v2;
    private ViewContacts v3;

    public ListContacts() {
        super("CONTACTS", R.layout.page_list_contacts);
    }

    @Override
    protected void onCreateFragment(ViewGroup rootView, Bundle savedInstanceState) {
        initFragment(rootView);
    }

    /**
     * Init multiple Fragment to be displayed in the view.
     *
     * @param rootView the container view
     */
    private void initFragment(ViewGroup rootView) {
        FragmentManager fragmentManager = new FragmentManager(getActivity().getSupportFragmentManager());

        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.container2);
        viewPager.setAdapter(fragmentManager);

        contactManager = new ContactManager(getActivity());
        List<IModel> models = contactsFactory();
        List<Contact> contactWithCardList = new ArrayList<>();
        List<Contact> contactWithoutCardList = new ArrayList<>();

        List<Contact> contacts = new ArrayList<>();
        for (IModel model : models) {
            Contact contact = ((Contact) model);
            contacts.add(contact);
            if (contact.idBusinessCard == -1) {
                contactWithoutCardList.add(contact);
            } else {
                contactWithCardList.add(contact);
            }
        }

        v1 = new ViewContacts(All_CONTACT_TITLE, contacts);
        v2 = new ViewContacts(WITH_BUSINESS_CARD_TITLE, contactWithCardList);
        v3 = new ViewContacts(WITHOUT_BUSINESS_CARD_TITLE, contactWithoutCardList);

        fragmentManager.addFragment(v1);
        fragmentManager.addFragment(v2);
        fragmentManager.addFragment(v3);
        fragmentManager.notifyDataSetChanged();

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * Factory method used to decide which type of {@link IContactManager} to be used.
     *
     * @return the list of Contact
     */
    private List<IModel> contactsFactory() {
        return contactManager.getContacts();
    }
}
