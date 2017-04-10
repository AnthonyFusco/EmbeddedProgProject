package com.project.unice.embeddedprogproject;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.project.unice.embeddedprogproject.fragments.FragmentManager;
import com.project.unice.embeddedprogproject.fragments.views.ListContacts;
import com.project.unice.embeddedprogproject.fragments.views.ListRequests;
import com.project.unice.embeddedprogproject.sms.SmsListener;

public class MainActivity extends AppCompatActivity {

    private static final int NOTIFICATION = 81237;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = new FragmentManager(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(fragmentManager);

        fragmentManager.addFragment(new ListRequests());
        fragmentManager.addFragment(new ListContacts());
        //fragmentManager.addFragment(new SmsPage());
        fragmentManager.notifyDataSetChanged();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("");
        enableBroadcastReceiver();
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
            case R.id.action_sms :
                change(2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void change(int n){
        viewPager.setCurrentItem(n, false);
    }

    public void enableBroadcastReceiver() {
        ComponentName receiver = new ComponentName(this, SmsListener.class); //created SMSLog class above!
        PackageManager pm = this.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
        Toast.makeText(this, "Enabled logging", Toast.LENGTH_SHORT).show();

        //Let us also show a notification
        Notification notification = new Notification.Builder(this)
                .setContentTitle("SMS Logger Running")
                .setContentText("Status: Logging..")
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();
        notification.flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;
        NotificationManager notifier = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notifier.notify(NOTIFICATION, notification);
    }


    public void disableBroadcastReceiver() {
        ComponentName receiver = new ComponentName(this, SmsListener.class);
        PackageManager pm = this.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
        Toast.makeText(this, "Disabled logging", Toast.LENGTH_SHORT).show();
        ((NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(NOTIFICATION);
    }
}
