package com.project.unice.embeddedprogproject.sms;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SmsListener extends BroadcastReceiver {

    private SharedPreferences preferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent smsRecvIntent = new Intent("android.provider.Telephony.SMS_RECEIVED");
        List<ResolveInfo> infos = context.getPackageManager().queryBroadcastReceivers(smsRecvIntent, 0);
        System.out.println("informations : " + infos.get(0).resolvePackageName);
        //context.sendOrderedBroadcast();
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;
            String msg_from;
            String msg_body;
            if (bundle != null) {
                try {
                    Uri uriSms = Uri.parse("content://sms/inbox");
                    Cursor c = context.getContentResolver().query(
                            uriSms,
                            new String[]{"_id", "thread_id", "address", "person",
                                    "date", "body"}, "read=0", null, null);

                    if (c != null && c.moveToFirst()) {
                        do {
                            long id = c.getLong(0);
                            long threadId = c.getLong(1);
                            String address = c.getString(2);
                            String body = c.getString(5);
                            String date = c.getString(3);
                            /*Log.e("log>>>",
                                    "0--->" + c.getString(0) + "1---->" + c.getString(1)
                                            + "2---->" + c.getString(2) + "3--->"
                                            + c.getString(3) + "4----->" + c.getString(4)
                                            + "5---->" + c.getString(5));
                            Log.e("log>>>", "date" + c.getString(0));
*/
                            ContentValues values = new ContentValues();
                            values.put("read", true);
                            context.getContentResolver().update(Uri.parse("content://sms/"),
                                    values, "_id=" + id, null);

                            if (body.contains("catch")) {
                                // mLogger.logInfo("Deleting SMS with id: " + threadId);
                                context.getContentResolver().delete(
                                        Uri.parse("content://sms/" + id), "date=?",
                                        new String[]{c.getString(4)});
                                Log.e("log>>>", "Delete success.........");
                                Toast.makeText(context, body, Toast.LENGTH_SHORT).show();
                                abortBroadcast();
                            }
                        } while (c.moveToNext());
                    }
                } catch (Exception e) {
                    Log.e("log>>>", e.toString());
                }
            }
        }
    }

    /**
     * Method to convert current long type timestamp to human readable format - Easter egg ;)
     */
    public String getreadabledate() {
        long milles = System.currentTimeMillis();
        SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy HH:mm", Locale.getDefault());
        Date resultdate = new Date(milles);
        return df.format(resultdate);
    }
}
