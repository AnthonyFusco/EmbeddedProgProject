package com.project.unice.embeddedprogproject.fragments.views;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.project.unice.embeddedprogproject.R;
import com.project.unice.embeddedprogproject.fragments.AbstractFragment;
import com.project.unice.embeddedprogproject.sms.SmsListener;

public class SmsPage extends AbstractFragment {

    private static final int NOTIFICATION = 81237;

    public SmsPage() {
        super("SMS", R.layout.sms_page);
    }

    @Override
    protected void onCreateFragment(ViewGroup rootView, Bundle savedInstanceState) {
        SmsListener myReceiver = new SmsListener();
        IntentFilter intentFilter = new IntentFilter("SmsListener");
        getActivity().registerReceiver(myReceiver, intentFilter);
        enableBroadcastReceiver();
    }

    @Override
    public void onStop() {
        disableBroadcastReceiver();
        super.onStop();
    }

    /*public void activer(View view){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("0668728382", null, "sms message", null, null);
    }*/

    public void enableBroadcastReceiver() {
        ComponentName receiver = new ComponentName(getActivity(), SmsListener.class); //created SMSLog class above!
        PackageManager pm = getActivity().getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
        Toast.makeText(getActivity(), "Enabled logging", Toast.LENGTH_SHORT).show();

        //Let us also show a notification
        Notification notification = new Notification.Builder(getActivity())
                .setContentTitle("SMS Logger Running")
                .setContentText("Status: Logging..")
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();
        notification.flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;
        NotificationManager notifier = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notifier.notify(NOTIFICATION, notification);
    }


    public void disableBroadcastReceiver() {
        ComponentName receiver = new ComponentName(getActivity(), SmsListener.class);
        PackageManager pm = getActivity().getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
        Toast.makeText(getActivity(), "Disabled logging", Toast.LENGTH_SHORT).show();
        ((NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE)).cancel(NOTIFICATION);
    }
}