package com.project.unice.embeddedprogproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.project.unice.embeddedprogproject.pages.ViewHolderBCChoice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChoicesBCActivity extends AppCompatActivity {

    private Map<String, List<String>> choices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choices);

        //TODO initialize the list with the data in the intent
        choices = new HashMap<>();

        ListView listView = (ListView)findViewById(R.id.listViewChoices);


        LayoutListAdapter<String> adapter = new LayoutListAdapter<>();
        adapter.setViewHolder(new ViewHolderBCChoice(choices));
        adapter.setInflater(getLayoutInflater());
        adapter.setListElements(choices);
        adapter.setLayout(R.layout.choice_list_item);
        listView.setAdapter(adapter);
    }
}