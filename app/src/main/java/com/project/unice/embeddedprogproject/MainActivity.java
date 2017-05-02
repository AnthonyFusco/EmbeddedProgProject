package com.project.unice.embeddedprogproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.project.unice.embeddedprogproject.fragments.FragmentManager;
import com.project.unice.embeddedprogproject.fragments.views.ListContacts;
import com.project.unice.embeddedprogproject.fragments.views.ListRequests;
import com.project.unice.embeddedprogproject.fragments.views.MyProfileEditor;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefsFile";


    private ViewPager viewPager;
    private SmsBroadcastReceiver smsBroadcastReceiver;
    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragment();

        //Create the broadcast receiver for the SMS. The App will listen for a new SMS and parse it.
        smsBroadcastReceiver = new SmsBroadcastReceiver(getApplicationContext());
        smsBroadcastReceiver.enableBroadcastReceiver();

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        change(1);
        myToolbar.setTitle(R.string.contact_list);
        setSupportActionBar(myToolbar);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String userPhone = settings.getString("userPhone", "-1");
        if (userPhone.equals("-1")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            String textToSend ="Please add your phone number";
            builder.setMessage(textToSend)
                    .setTitle("");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_PHONE);
            builder.setView(input);
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    String phone = input.getText().toString();
                    Toast.makeText(MainActivity.this, "number " + phone, Toast.LENGTH_LONG).show();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }

       /* DataBaseTableManager manager = new DataBaseTableManager(this, DataBaseManager.DATABASE_NAME);
        BusinessCard card = new BusinessCard();
        card.name = "toto";
        card.company = "UNS";

        manager.add(card);

        Contact contact = new Contact();
        contact.name = "toto";
        contact.idBusinessCard = 42;
        contact.phone = "42";

        manager.add(contact);*/

        /*for (IModel model : manager.selectAll(Contact.class)) {
            System.out.println(model);
        }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        smsBroadcastReceiver.disableBroadcastReceiver();
    }

    /**
     * Initialize the Fragment Manager and the  ViewPager and add multiple Fragment to it.
     */
    private void initFragment() {
        FragmentManager fragmentManager = new FragmentManager(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(fragmentManager);

        fragmentManager.addFragment(new ListRequests());
        fragmentManager.addFragment(new ListContacts());
        fragmentManager.addFragment(new MyProfileEditor());
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
            case R.id.action_requests:
                change(0);
                myToolbar.setTitle(getString(R.string.pending_request));
                return true;
            case R.id.action_list_contacts :
                change(1);
                myToolbar.setTitle(R.string.contact_list);
                return true;
            case R.id.action_myprofile:
                change(2);
                myToolbar.setTitle(R.string.contact_list);
                return true;
            default:
                myToolbar.setTitle(R.string.BUSINESS_CARD);
                return super.onOptionsItemSelected(item);
        }
    }

    private void change(int n){
        viewPager.setCurrentItem(n, false);
    }
}
