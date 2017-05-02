package com.project.unice.embeddedprogproject.fragments.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.project.unice.embeddedprogproject.R;
import com.project.unice.embeddedprogproject.ViewBusinessCardActivity;
import com.project.unice.embeddedprogproject.fragments.AbstractFragment;
import com.project.unice.embeddedprogproject.models.Contact;
import com.project.unice.embeddedprogproject.sqlite.DataBaseManager;
import com.project.unice.embeddedprogproject.sqlite.DataBaseTableManager;
import com.project.unice.embeddedprogproject.sqlite.IDatabaseManager;

public class MyProfileEditor extends AbstractFragment {

    public MyProfileEditor() {
        super("MY PROFILE", R.layout.my_profile_editor);
    }

    @Override
    protected void onCreateFragment(ViewGroup rootView, Bundle savedInstanceState) {
        Button edit = (Button)rootView.findViewById(R.id.buttonEditMyProfile);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int myId = getUserId();
                Intent intent = new Intent(Intent.ACTION_EDIT, Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, String.valueOf(myId)));
                startActivityForResult(intent, 10011);
            }
        });
    }

    private int getUserId() {
        IDatabaseManager manager = new DataBaseTableManager(getActivity(), DataBaseManager.DATABASE_NAME);
        Contact c = (Contact)manager.findFirstValue(Contact.class, "Phone", "0668728382");
        return c.idContactAndroid;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
