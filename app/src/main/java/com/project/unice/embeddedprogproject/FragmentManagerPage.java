package com.project.unice.embeddedprogproject;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class FragmentManagerPage extends FragmentStatePagerAdapter {

    private List<AbstractFragment> list;
    public FragmentManagerPage(FragmentManager fm) {
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
