package com.project.unice.embeddedprogproject.fragments.views;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;

import com.project.unice.embeddedprogproject.R;
import com.project.unice.embeddedprogproject.fragments.AbstractFragment;

public class MyProfile extends AbstractFragment {

    public MyProfile() {
        super("MY_PROFILE", R.layout.my_profile);
    }

    @Override
    protected void onCreateFragment(ViewGroup rootView, Bundle savedInstanceState) {
        Toast.makeText(getActivity(), "MY_PROFILE_PAGE", Toast.LENGTH_SHORT).show();
    }

}