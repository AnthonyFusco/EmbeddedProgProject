package com.project.unice.embeddedprogproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.InputType;
import android.widget.EditText;

import com.google.gson.Gson;
import com.project.unice.embeddedprogproject.models.BusinessCard;
import com.project.unice.embeddedprogproject.models.ContactManager;

public class MySharedPreferences {
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String USER_PHONE = "userPhone";
    public static final String USER_BUSINESS_CARD = "businessCard";
    private Context context;
    private SharedPreferences settings;

    public MySharedPreferences(Context context) {
        this.context = context;
        this.settings = context.getSharedPreferences(PREFS_NAME, 0);
    }

    /**
     * Prompt the user to add it's phone number.
     */
    public void askForPhoneNumber() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        String textToSend = "Please add your phone number";
        builder.setMessage(textToSend)
                .setTitle("");
        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_PHONE);
        builder.setView(input);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (input.getText() != null) {
                    String phone = ContactManager.formatPhone(input.getText().toString());
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString(USER_PHONE, phone);
                    editor.apply();
                }
            }
        });
        builder.setNegativeButton("Later", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    /**
     *
     * If the user number isn't registered, prompt him to add it.
     */
    public void askForPhoneNumberIfNotSaved() {
        String userPhone = settings.getString(USER_PHONE, "-1");
        if (userPhone.equals("-1") || userPhone.length() <= 0) {
            askForPhoneNumber();
        }
    }

    public String getPhoneNumber() {
        String userPhone = settings.getString(USER_PHONE, "-1");
        if (userPhone.equals("-1") || userPhone.length() <= 0) {
            return null;
        }
        return userPhone;
    }

    public void saveBusinessCard(BusinessCard card) {
        Gson gson = new Gson();
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(USER_BUSINESS_CARD, gson.toJson(card));
        editor.apply();
    }

    public BusinessCard getBusinessCard() {
        String businessCardSerial = settings.getString(USER_BUSINESS_CARD, "-1");
        if (businessCardSerial.equals("-1") || businessCardSerial.length() <= 0) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(businessCardSerial, BusinessCard.class);
    }
}