package com.project.unice.embeddedprogproject.pages;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.unice.embeddedprogproject.R;
import com.project.unice.embeddedprogproject.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ViewHolderBCChoice implements ViewHolder {

    private List<ChoiceElement> choices;
    private TextView choiceText;
    private CheckBox choiceCheck;
    private LinearLayout rowLayout;


    public ViewHolderBCChoice(List<String> choices) {
        this.choices = new ArrayList<>();
        for (String choice : choices) {
            this.choices.add(new ChoiceElement(choice, false));
        }
    }

    @Override
    public boolean initializeView(View view, final int position) {
        choiceCheck = (CheckBox) view.findViewById(R.id.checkboxChoiceBC);
        choiceCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                choices.get(position).checked = isChecked;
            }
        });
        choiceText = (TextView) view.findViewById(R.id.textViewChoiceBC);
        rowLayout = (LinearLayout) view.findViewById(R.id.contact_list_row_id);
        return true;
    }

    @Override
    public boolean fillView(int position) {
        if (position < choices.size()){
            choiceText.setText(choices.get(position).text);
            choiceCheck.setChecked(choices.get(position).checked);
            return true;
        }
        return false;
    }

    private class ChoiceElement {
        boolean checked;
        String text;

        public ChoiceElement(String choice, boolean b) {
            this.checked = b;
            this.text = choice;
        }
    }
}