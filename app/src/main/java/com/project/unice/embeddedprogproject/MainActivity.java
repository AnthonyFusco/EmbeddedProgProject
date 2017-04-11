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
import com.project.unice.embeddedprogproject.sqlite.DataBaseManager;
import com.project.unice.embeddedprogproject.sqlite.DataBaseTableManager;
import com.project.unice.embeddedprogproject.sqlite.IModel;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private SmsBroadcastReceiver smsBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragment();

        //Create the broadcast receiver for the SMS. The App will listen for a new SMS and parse it.
        smsBroadcastReceiver = new SmsBroadcastReceiver(getApplicationContext());
        smsBroadcastReceiver.enableBroadcastReceiver();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle(R.string.BUSINESS_CARD);
        setSupportActionBar(myToolbar);


        /*DataBaseTableManager manager = new DataBaseTableManager(this, DataBaseManager.DATABASE_NAME);
        BusinessCard card = new BusinessCard();
        card.name = "anthony";
        card.company = "UNS";

        manager.add(card);
        for (IModel model : manager.selectAll(BusinessCard.class)) {
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
            case R.id.action_favorites:
                change(0);
                return true;
            case R.id.action_schedules :
                change(1);
                return true;
            case R.id.action_myprofile:
                Intent intent = new Intent(MainActivity.this, MyProfileActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void change(int n){
        viewPager.setCurrentItem(n, false);
    }
}
