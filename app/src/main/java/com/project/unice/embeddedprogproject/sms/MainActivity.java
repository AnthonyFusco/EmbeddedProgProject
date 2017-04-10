package com.project.unice.embeddedprogproject.sms;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button startbtn, stopbtn;
    private int NOTIFICATION = 81237;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startbtn = (Button) findViewById(R.id.buttonRefresh);
        stopbtn = (Button) findViewById(R.id.button2);

        SmsListener myReceiver = new SmsListener();
        IntentFilter intentFilter = new IntentFilter("SmsListener");
        registerReceiver( myReceiver , intentFilter);

    }


    public void activer(View view){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("0668728382", null, "sms message", null, null);
    }

    public void connect(final View v) {
        startbtn.setVisibility(View.INVISIBLE);
        enableBroadcastReceiver(v);
        stopbtn.setVisibility(View.VISIBLE);
    }
    public void disconnect(final View v) {
        stopbtn.setVisibility(View.INVISIBLE);
        disableBroadcastReceiver(v);
        startbtn.setVisibility(View.VISIBLE);
    }

    public void enableBroadcastReceiver(View view) {

        ComponentName receiver = new ComponentName(this, SmsListener.class); //created SMSLog class above!
        PackageManager pm = this.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
        Toast.makeText(this, "Enabled logging", Toast.LENGTH_SHORT).show();

        //Let us also show a notification
        Notification notification = new Notification.Builder(getApplicationContext())
                .setContentTitle("SMS Logger Running")
                .setContentText("Status: Logging..")
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();
        notification.flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;
        NotificationManager notifier = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notifier.notify(NOTIFICATION, notification);
    }

    /**
     * This method disables the Broadcast receiver registered in the AndroidManifest file.
     *
     * @param view
     */
    public void disableBroadcastReceiver(View view) {
        ComponentName receiver = new ComponentName(this, SmsListener.class);
        PackageManager pm = this.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
        Toast.makeText(this, "Disabled logging", Toast.LENGTH_SHORT).show();
        ((NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE)).cancel(NOTIFICATION);
    }
}
