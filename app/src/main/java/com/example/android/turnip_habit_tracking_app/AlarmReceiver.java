package com.example.android.turnip_habit_tracking_app;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;

import java.util.Random;
/**
 * <h1>AlarmReceiver</h1>
 * This class is used to generate the notification, using the start alarm
 * of a habit session.
 * The notification tells the user that a habit session is about the begin.
 * <p>
 *
 * @author  Eoin Thompson
 * @version 1.0
 * @since   12-12-2016
 */
public class AlarmReceiver extends BroadcastReceiver {
    Random random = new Random();                       // Each notification given random id
    int uniqueID = random.nextInt(9999 - 1000) + 1000;  // Duplicate id's overwrite notification

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        String habitID = bundle.getString("ID");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // User brought to Alarm on clicking notification
        Intent showHabit = new Intent(context,HabitInfo.class);
        Bundle sendBack = new Bundle();
        sendBack.putString("ID",habitID);
        showHabit.putExtras(sendBack);
        // Set Intent Flag
        showHabit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Pending Intent for the Notification
        PendingIntent pendingIntent = PendingIntent.getActivity(context,uniqueID,showHabit,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        // Add Pending Intent to Notification
        builder.setContentIntent(pendingIntent);
        // Set Message Content for Notification
        builder.setContentTitle("Habit");
        builder.setContentText("Habit Session Beginning!");
        builder.setTicker("Session Beginning");
        // Set Icon for Notification
        builder.setSmallIcon(R.drawable.ic_turnip);
        // Enable Vibrate
        builder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
        // Intent Cancelled after click
        builder.setAutoCancel(true);
        // Create Notification
        notificationManager.notify(uniqueID,builder.build());
    }
}

