package com.project.unice.embeddedprogproject;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.project.unice.embeddedprogproject.sms.SmsListener;

/**
 * BroadcastReceiver listening to the reception of SMS.
 *
 */
class SmsBroadcastReceiver {

    private static final int NOTIFICATION = 81237;
    private Context activity;

    SmsBroadcastReceiver(Context activity) {
        this.activity = activity;
    }

    /**
     * Enable the SMS listener and notify the app when a sms is received.
     */
    void enableBroadcastReceiver() {
        ComponentName receiver = new ComponentName(activity, SmsListener.class); //created SMSLog class above!
        PackageManager packageManager = activity.getPackageManager();
        packageManager.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
        Toast.makeText(activity, "Enabled logging", Toast.LENGTH_SHORT).show();

        Notification notification = new Notification.Builder(activity)
                .setContentTitle("SMS Logger Running")
                .setContentText("Status: Logging..")
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();
        notification.flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;
        NotificationManager notifier = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        notifier.notify(NOTIFICATION, notification);
    }

    /**
     * Clean the listener.
     */
    void disableBroadcastReceiver() {
        ComponentName receiver = new ComponentName(activity, SmsListener.class);
        PackageManager pm = activity.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
        Toast.makeText(activity, "Disabled logging", Toast.LENGTH_SHORT).show();
        ((NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(NOTIFICATION);
    }
}
