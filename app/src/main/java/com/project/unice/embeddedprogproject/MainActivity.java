package com.project.unice.embeddedprogproject;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.project.unice.embeddedprogproject.pages.ListContactsPage;
import com.project.unice.embeddedprogproject.pages.ListDemandesPage;

public class MainActivity extends AppCompatActivity {

    private FragmentManagerPage fragmentManager;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = new FragmentManagerPage(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(fragmentManager);

        fragmentManager.addFragment(new ListDemandesPage());
        fragmentManager.addFragment(new ListContactsPage());
        fragmentManager.notifyDataSetChanged();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("");

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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void change(int n){
        viewPager.setCurrentItem(n, false);
    }
}
