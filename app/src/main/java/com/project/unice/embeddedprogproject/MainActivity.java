package com.project.unice.embeddedprogproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.project.unice.embeddedprogproject.fragments.FragmentManager;
import com.project.unice.embeddedprogproject.fragments.views.ListContacts;
import com.project.unice.embeddedprogproject.fragments.views.ListRequests;
import com.project.unice.embeddedprogproject.models.BusinessCard;
import com.project.unice.embeddedprogproject.models.Contact;
import com.project.unice.embeddedprogproject.sqlite.DataBaseManager;
import com.project.unice.embeddedprogproject.sqlite.DataBaseTableManager;
import com.project.unice.embeddedprogproject.sqlite.IModel;

public class MainActivity extends AppCompatActivity {

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
                Intent intent = new Intent(MainActivity.this, MyProfileActivity.class);
                startActivity(intent);
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
