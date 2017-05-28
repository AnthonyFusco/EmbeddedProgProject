package com.project.unice.embeddedprogproject.businessCardCreation;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.project.unice.embeddedprogproject.R;
import com.project.unice.embeddedprogproject.ViewHolder;

import java.util.List;

public class ViewHolderBCChoice implements ViewHolder {

    private List<ChoiceElement> choices;
    private TextView choiceTextKey;
    private TextView choiceTextValue;
    private CheckBox choiceCheck;


    public ViewHolderBCChoice(List<ChoiceElement> choices) {
        this.choices = choices;
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
        choiceTextKey = (TextView) view.findViewById(R.id.textViewChoiceBC);
        choiceTextValue = (TextView) view.findViewById(R.id.textViewChoiceBCVal);
        return true;
    }

    @Override
    public boolean fillView(int position) {
        if (position < choices.size()){
            choiceTextKey.setText(choices.get(position).property);
            choiceTextValue.setText(choices.get(position).value);
            choiceCheck.setChecked(choices.get(position).checked);
            return true;
        }
        return false;
    }
}