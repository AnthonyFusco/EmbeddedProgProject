package com.project.unice.embeddedprogproject.fragments.views;

import android.os.Bundle;
import android.view.ViewGroup;

import com.project.unice.embeddedprogproject.R;
import com.project.unice.embeddedprogproject.fragments.AbstractFragment;

/**
 * Fragment gathering the pending requests for a a business card operation.
 */
public class ListRequests extends AbstractFragment {

    public ListRequests() {
        super("DEMANDES", R.layout.page_list_demandes);
    }

    @Override
    protected void onCreateFragment(ViewGroup rootView, Bundle savedInstanceState) {

    }
}
