package com.project.unice.embeddedprogproject;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.project.unice.embeddedprogproject.fragments.FragmentManager;
import com.project.unice.embeddedprogproject.fragments.views.ListContacts;
import com.project.unice.embeddedprogproject.fragments.views.MyProfileEditor;
import com.project.unice.embeddedprogproject.databaseModels.BusinessCard;
import com.project.unice.embeddedprogproject.sms.Sender;

public class MainActivity extends AppCompatActivity {

    private MySharedPreferences mySharedPreferences;

    private ViewPager viewPager;
    private SmsBroadcastReceiver smsBroadcastReceiver;
    private Toolbar myToolbar;

    private MyProfileEditor myProfileEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragment();

        mySharedPreferences =  new MySharedPreferences(this);

        //Create the broadcast receiver for the SMS. The App will listen for a new SMS and parse it.
        smsBroadcastReceiver = new SmsBroadcastReceiver(getApplicationContext());
        smsBroadcastReceiver.enableBroadcastReceiver();

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle(R.string.contact_list);
        setSupportActionBar(myToolbar);

        mySharedPreferences.askForPhoneNumberIfNotSaved();

        /*BusinessCard bc = new BusinessCard();
        bc.phone = "4444";
        bc.name = "aaaa";
        bc.company = "toto";
        mySharedPreferences.saveBusinessCard(bc);*/

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        smsBroadcastReceiver.disableBroadcastReceiver();
    }

    /**
     * Initialize the Fragment Manager and the ViewPager and add multiple Fragment to it.
     */
    private void initFragment() {
        FragmentManager fragmentManager = new FragmentManager(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(fragmentManager);

        ListContacts listContacts = new ListContacts();
        myProfileEditor = new MyProfileEditor();
        fragmentManager.addFragment(listContacts);
        fragmentManager.addFragment(myProfileEditor);
        fragmentManager.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_send_sms:
                if (mySharedPreferences.getBusinessCard() == null) {
                    Toast.makeText(this, "You need to create your Business Card first !", Toast.LENGTH_LONG).show();
                } else {
                    sendCardToNumber();
                }
                return true;
            case R.id.action_list_contacts:
                change(0);
                myToolbar.setTitle(R.string.contact_list);
                return true;
            case R.id.action_myprofile:
                change(1);
                myToolbar.setTitle(R.string.contact_list);
                return true;
            case R.id.action_settings:
                mySharedPreferences.askForPhoneNumber();
                return true;
            default:
                myToolbar.setTitle(R.string.BUSINESS_CARD);
                return super.onOptionsItemSelected(item);
        }
    }

    private void sendCardToNumber() {
        AlertDialog.Builder builder = new AlertDialog.Builder(viewPager.getContext());
        String textToSend = "To what phone number do you want to send your card ?";
        builder.setMessage(textToSend)
                .setTitle("");
        final EditText input = new EditText(viewPager.getContext());
        input.setInputType(InputType.TYPE_CLASS_PHONE);
        builder.setView(input);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (input.getText() != null && input.getText().length() > 0) {
                    BusinessCard card = mySharedPreferences.getBusinessCard();
                    Sender.getInstance().send(card, input.getText().toString());
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void change(int n) {
        viewPager.setCurrentItem(n, false);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean type = intent.getBooleanExtra("sms", false);  //get the type of message from MyGcmListenerService 1 - lock or 0 -Unlock
            if (type) {
                initFragment();
            }
        }
    };

    @Override
    protected void onResume(){
        super.onResume();
        LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(broadcastReceiver, new IntentFilter("NOW"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        myProfileEditor.onActivityResult(requestCode, resultCode, data);
    }
}
