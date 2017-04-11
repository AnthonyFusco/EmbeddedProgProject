package com.project.unice.embeddedprogproject.pages;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.unice.embeddedprogproject.R;
import com.project.unice.embeddedprogproject.ViewHolder;
import com.project.unice.embeddedprogproject.models.Contact;

import java.util.List;


/**
 * Personalized the view of a contact.
 */
public class ViewHolderContacts implements ViewHolder {

    private final List<Contact> contacts;

    private TextView nom;
    private TextView phone;
    private LinearLayout rowLayout;

    public ViewHolderContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public boolean initializeView(View view) {
        nom = (TextView) view.findViewById(R.id.textnamecontact);
        phone = (TextView) view.findViewById(R.id.phonecontact);
        rowLayout = (LinearLayout) view.findViewById(R.id.contact_list_row_id);
        return true;
    }

    @Override
    public boolean fillView(int position) {
        nom.setText(contacts.get(position).name);
        phone.setText(contacts.get(position).phone);
        if (contacts.get(position).idBusinessCard == -1) {
            rowLayout.setBackgroundColor(Color.WHITE);
        } else {
            rowLayout.setBackgroundColor(Color.LTGRAY);
        }
        return true;
    }

}
