package com.project.unice.embeddedprogproject.fragments.views;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.project.unice.embeddedprogproject.R;
import com.project.unice.embeddedprogproject.fragments.AbstractFragment;

import java.util.ArrayList;
import java.util.List;

class TestContacts extends AbstractFragment {

    private List<String> itemList;

    public TestContacts(String test) {
        super(test, R.layout.test_layout);
        itemList = new ArrayList<>();
    }

    @Override
    protected void onCreateFragment(ViewGroup rootView, Bundle savedInstanceState) {
        ListView listView = (ListView)rootView.findViewById(R.id.listView_contacts);

        listView.setAdapter(new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_list_item_1,
                itemList));
    }
}
