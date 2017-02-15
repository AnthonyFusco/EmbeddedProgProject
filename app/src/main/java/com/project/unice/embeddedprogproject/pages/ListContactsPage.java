package com.project.unice.embeddedprogproject.pages;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.project.unice.embeddedprogproject.AbstractFragment;
import com.project.unice.embeddedprogproject.FragmentManagerPage;
import com.project.unice.embeddedprogproject.R;


public class ListContactsPage extends AbstractFragment {
    private FragmentManagerPage fragmentManager;
    private ViewPager viewPager;

    public ListContactsPage() {
        super("CONTACTS", R.layout.page_list_contacts);
    }

    @Override
    protected void onCreateFragment(ViewGroup rootView, Bundle savedInstanceState) {
        fragmentManager = new FragmentManagerPage(getActivity().getSupportFragmentManager());

        viewPager = (ViewPager) rootView.findViewById(R.id.container2);
        viewPager.setAdapter(fragmentManager);

        fragmentManager.addFragment(new ViewContactsFragment("TOUS"));
        fragmentManager.addFragment(new ViewContactsFragment("AVEC"));
        fragmentManager.addFragment(new ViewContactsFragment("SANS"));
        fragmentManager.notifyDataSetChanged();

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
