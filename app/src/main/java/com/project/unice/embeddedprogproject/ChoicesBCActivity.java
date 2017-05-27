package com.project.unice.embeddedprogproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.unice.embeddedprogproject.databaseModels.BusinessCard;
import com.project.unice.embeddedprogproject.pages.ChoiceElement;
import com.project.unice.embeddedprogproject.pages.ViewHolderBCChoice;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChoicesBCActivity extends AppCompatActivity {

    private Map<String, List<String>> choices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choices);

        String extra = getIntent().getStringExtra("Serial");
        Type type = new TypeToken<Map<String, List<String>>>(){}.getType();
        choices = new Gson().fromJson(extra, type);

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

        BusinessCard card = new BusinessCard();
        card.contains = new Gson().toJson(map);

        MySharedPreferences preferences = new MySharedPreferences(getApplicationContext());
        preferences.saveBusinessCard(card);
    }
}