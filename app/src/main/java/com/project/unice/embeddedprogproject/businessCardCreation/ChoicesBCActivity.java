package com.project.unice.embeddedprogproject.businessCardCreation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.unice.embeddedprogproject.LayoutListAdapter;
import com.project.unice.embeddedprogproject.MySharedPreferences;
import com.project.unice.embeddedprogproject.OnClickContactFactory;
import com.project.unice.embeddedprogproject.R;
import com.project.unice.embeddedprogproject.sqlite.databaseModels.BusinessCard;
import com.project.unice.embeddedprogproject.businessCardCreation.ChoiceElement;
import com.project.unice.embeddedprogproject.businessCardCreation.ViewHolderBCChoice;
import com.project.unice.embeddedprogproject.sqlite.databaseModels.Contact;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChoicesBCActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choices);

        String extra = getIntent().getStringExtra("Serial");
        Type type = new TypeToken<Map<String, List<String>>>(){}.getType();
        Map<String, List<String>> choices = new Gson().fromJson(extra, type);

        ListView listView = (ListView)findViewById(R.id.listViewChoices);

        LayoutListAdapter<ChoiceElement> adapter = new LayoutListAdapter<>();
        List<ChoiceElement> lst = new ArrayList<>();
        for (String key : choices.keySet()) {
            for (String value : choices.get(key)) {
                lst.add(new ChoiceElement(key, value, false));
            }
        }
        adapter.setViewHolder(new ViewHolderBCChoice(lst));
        adapter.setInflater(getLayoutInflater());

        adapter.setListElements(lst);
        adapter.setLayout(R.layout.choice_list_item);
        listView.setAdapter(adapter);
    }

    public void createCard(View view){
        ListView listView = (ListView)findViewById(R.id.listViewChoices);
        LayoutListAdapter<ChoiceElement> adapter = (LayoutListAdapter<ChoiceElement>) listView.getAdapter();

        List<ChoiceElement> selectors = new ArrayList<>();
        for (ChoiceElement choiceElement : adapter.getListElements()) {
            if (choiceElement.checked){
                selectors.add(choiceElement);
            }
        }

        Map<String, List<String>> map = new HashMap<>();
        for (ChoiceElement selector : selectors) {
            if (!map.containsKey(selector.property)) {
                map.put(selector.property, new ArrayList<String>());
            }
            map.get(selector.property).add(selector.value);
        }

        MySharedPreferences preferences = new MySharedPreferences(getApplicationContext());

        BusinessCard card = new BusinessCard();
        card.phone = preferences.getPhoneNumber();
        card.contains = new Gson().toJson(map);

        preferences.saveBusinessCard(card);

        Intent intent = new Intent(ChoicesBCActivity.this, ViewBusinessCardActivity.class);
        Gson gson = new Gson();
        Contact contact = new Contact();
        contact.phone = preferences.getPhoneNumber();
        String contactSerialized = gson.toJson(contact);
        intent.putExtra(ViewBusinessCardActivity.CONTACT_INTENT_CODE, contactSerialized);

        startActivity(intent);
        finish();
    }
}