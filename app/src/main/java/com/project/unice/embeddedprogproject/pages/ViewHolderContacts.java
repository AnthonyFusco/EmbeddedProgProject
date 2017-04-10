package com.project.unice.embeddedprogproject.pages;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.unice.embeddedprogproject.R;
import com.project.unice.embeddedprogproject.ViewHolder;

import java.util.List;


public class ViewHolderContacts implements ViewHolder {

    private final List<Contact> contacts;

    private TextView nom;
    private TextView phone;

    public ViewHolderContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public boolean initialize(int position) {
        nom.setText(contacts.get(position).name);
        phone.setText(contacts.get(position).phone);
        return true;
    }

    @Override
    public boolean configure(View view) {
        nom = (TextView) view.findViewById(R.id.textnamecontact);
        phone = (TextView) view.findViewById(R.id.phonecontact);
        return true;
    }
}
