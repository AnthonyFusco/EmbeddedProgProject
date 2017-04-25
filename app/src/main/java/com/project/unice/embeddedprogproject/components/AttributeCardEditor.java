package com.project.unice.embeddedprogproject.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.unice.embeddedprogproject.R;


public class AttributeCardEditor extends LinearLayout {

    private EditText edition;
    private TextView label;

    public AttributeCardEditor(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AttributeCardEditor, 0, 0);
        String labelText = a.getString(R.styleable.AttributeCardEditor_titleText);
        a.recycle();
        init(context);

        label.setText(labelText);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.component_attr_editor, this);
        this.label = (TextView)findViewById(R.id.textViewAttrEditorLabel);
        this.edition = (EditText)findViewById(R.id.editTextAttrEditorInput);
    }
}
