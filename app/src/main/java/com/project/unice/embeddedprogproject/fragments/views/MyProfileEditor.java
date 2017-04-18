package com.project.unice.embeddedprogproject.fragments.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.project.unice.embeddedprogproject.R;
import com.project.unice.embeddedprogproject.ViewBusinessCardActivity;
import com.project.unice.embeddedprogproject.fragments.AbstractFragment;

public class MyProfileEditor extends AbstractFragment {

    public MyProfileEditor() {
        super("MY PROFILE", R.layout.my_profile_editor);
    }

    @Override
    protected void onCreateFragment(ViewGroup rootView, Bundle savedInstanceState) {
        Button visualize = (Button)rootView.findViewById(R.id.buttonViewMyProfile);
        visualize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewBusinessCardActivity.class);
                startActivity(intent);
            }
        });
    }
}
