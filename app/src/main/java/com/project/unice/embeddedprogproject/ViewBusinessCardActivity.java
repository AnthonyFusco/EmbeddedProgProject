package com.project.unice.embeddedprogproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.Gson;
import com.project.unice.embeddedprogproject.databaseModels.BusinessCard;
import com.project.unice.embeddedprogproject.databaseModels.Contact;
import com.project.unice.embeddedprogproject.sqlite.DataBaseManager;
import com.project.unice.embeddedprogproject.sqlite.DataBaseTableManager;
import com.project.unice.embeddedprogproject.sqlite.IDatabaseManager;

/**
 * Pretty display of a Business Card.
 */
public class ViewBusinessCardActivity extends AppCompatActivity {
    public static final String CONTACT_INTENT_CODE = "contact";
    private BusinessCard card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_businesscard_activity);

        Intent intent = getIntent();
        String contactSerialized = intent.getStringExtra(CONTACT_INTENT_CODE);
        if (contactSerialized != null) {
            Gson gson = new Gson();
            Contact contact = gson.fromJson(contactSerialized, Contact.class);
            IDatabaseManager manager = new DataBaseTableManager(getApplicationContext(), DataBaseManager.DATABASE_NAME);
            card = (BusinessCard) manager.findFirstValue(BusinessCard.class, "id", contact.idBusinessCard);
            fillCard(contact);
        }
    }

    /**
     * Fill the view with the data of the given contact.
     * @param contact the contact to add to the view
     */
    private void fillCard(Contact contact) {
        TextView society = (TextView) findViewById(R.id.textViewSociety);
        TextView fistName = (TextView) findViewById(R.id.textViewFirstName);
        TextView name = (TextView) findViewById(R.id.textViewName);
        TextView adress = (TextView) findViewById(R.id.textViewAddressSociety);
        TextView email = (TextView) findViewById(R.id.textViewEmail);
        TextView phone = (TextView) findViewById(R.id.textViewPhone);

        society.setText("");
        fistName.setText("");
        name.setText(contact.name);
        adress.setText("");
        email.setText("");
        phone.setText(contact.phone);
    }
}