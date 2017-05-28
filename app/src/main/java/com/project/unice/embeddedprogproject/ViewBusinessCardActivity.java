package com.project.unice.embeddedprogproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.unice.embeddedprogproject.databaseModels.BusinessCard;
import com.project.unice.embeddedprogproject.databaseModels.Contact;
import com.project.unice.embeddedprogproject.sqlite.DataBaseManager;
import com.project.unice.embeddedprogproject.sqlite.DataBaseTableManager;
import com.project.unice.embeddedprogproject.sqlite.IDatabaseManager;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Pretty display of a Business Card.
 */
public class ViewBusinessCardActivity extends AppCompatActivity {
    public static final String CONTACT_INTENT_CODE = "contact";

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
            BusinessCard card = (BusinessCard) manager.findFirstValue(BusinessCard.class, "Phone", contact.phone);
            fillCard(card);
        }
    }

    /**
     * Fill the view with the data of the given contact.
     * @param card the BC to add to the view
     */
    private void fillCard(BusinessCard card) {
        Type type = new TypeToken<Map<String, List<String>>>(){}.getType();
        Map<String, List<String>> map = new Gson().fromJson(card.contains, type);

        String phoneId = "Phone";
        String emailId = "Email";
        String organizationId = "Organization";
        String websiteId = "Website";
        String structuredNameId = "StructuredName";
        String structuredPostalId = "StructuredPostal";



        TextView name = (TextView) findViewById(R.id.textViewName);
        TextView phone = (TextView) findViewById(R.id.textViewPhone);
        TextView email = (TextView) findViewById(R.id.textViewEmail);
        TextView address = (TextView) findViewById(R.id.textViewAddressHome);
        TextView society = (TextView) findViewById(R.id.textViewSociety);

        String org = formatedStringValues(map.get(organizationId));
        String na = formatedStringValues(map.get(structuredNameId));
        String post =formatedStringValues(map.get(structuredPostalId));
        String mail = formatedStringValues(map.get(emailId));
        String pho = formatedStringValues(map.get(phoneId));


        name.setText(na);
        phone.setText(pho);
        email.setText(mail);
        address.setText(post);
        society.setText(org);
    }

    private String formatedStringValues(List<String> list){
        if (list == null) return "";
        StringBuilder builder = new StringBuilder();
        for (String s : list) {
            builder.append(s).append('\n');
        }
        return builder.toString();
    }
}