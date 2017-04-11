package com.project.unice.embeddedprogproject.fragments;

import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Contains a list of Fragments and allow multiple utils functions.
 */
public class FragmentManager extends FragmentStatePagerAdapter {

    private List<AbstractFragment> list;
    public FragmentManager(android.support.v4.app.FragmentManager fm) {
        super(fm);
        list = new ArrayList<>();
    }

    @Override
    public AbstractFragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getTitle();
    }

    public void addFragment(AbstractFragment fragment){
        list.add(fragment);
    }
}
