package com.project.unice.embeddedprogproject.fragments.views;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.project.unice.embeddedprogproject.R;
import com.project.unice.embeddedprogproject.fragments.AbstractFragment;
import com.project.unice.embeddedprogproject.fragments.FragmentManager;


public class ListContacts extends AbstractFragment {

    public ListContacts() {
        super("CONTACTS", R.layout.page_list_contacts);
    }

    @Override
    protected void onCreateFragment(ViewGroup rootView, Bundle savedInstanceState) {
        FragmentManager fragmentManager = new FragmentManager(getActivity().getSupportFragmentManager());

        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.container2);
        viewPager.setAdapter(fragmentManager);

        fragmentManager.addFragment(new TestContacts("TEST"));
        fragmentManager.addFragment(new ViewContacts("TOUS"));
        fragmentManager.addFragment(new ViewContacts("AVEC"));
        fragmentManager.addFragment(new ViewContacts("SANS"));
        fragmentManager.notifyDataSetChanged();

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
