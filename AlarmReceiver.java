package com.example.eoint.turnip;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;


/**
 * Created by eoint on 12/11/2016.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // Click Notification and Send To Activity
        Intent myIntent = new Intent(context,Alarm.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Show Intent To Notification
        PendingIntent pendingIntent = PendingIntent.getActivity(context,100,myIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentIntent(pendingIntent);
        builder.setContentTitle("Habit");
        builder.setContentText("Time To Habit!");
        builder.setTicker("Habit Time!");
        builder.setSmallIcon(R.drawable.turnip_icon);
        builder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
        builder.setAutoCancel(true);

        notificationManager.notify(100,builder.build());
    }
}
