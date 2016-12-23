package com.example.android.turnip_habit_tracking_app;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
/**
 * <h1>ConfirmSession</h1>
 * When a session is over the user confirms
 * whether or not they have completed it, they
 * are rewarded if it is completed.
 * <p>
 *
 * @author  Eoin Thompson
 * @version 1.0
 * @since   23-12-2016
 */
public class ConfirmSession extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_session);

        Bundle bundle = getIntent().getExtras();
        final String habitID = bundle.getString("ID");
        final String habitFilter = DBOpenHelper.HABIT_ID +  "=" + habitID;

        // Simple Dialog - Increment Reward if Yes is clicked??
        new AlertDialog.Builder(this)
                .setTitle("Session Over")
                .setMessage("Have you completed the session?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Cursor cursor = getContentResolver().query(HabitsProvider.CONTENT_URI, DBOpenHelper.ALL_COLUMNS, habitFilter, null, null);
                        cursor.moveToFirst();
                        int points = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.HABIT_POINTS));
                        points +=5;
                        int streak = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.HABIT_STREAK));
                        streak +=1;
                        ContentValues values = new ContentValues();
                        values.put(DBOpenHelper.HABIT_POINTS, points);
                        values.put(DBOpenHelper.HABIT_STREAK, streak);
                        getContentResolver().update(HabitsProvider.CONTENT_URI, values, habitFilter, null);
                    }
                })

                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplication(),"Clicked - Yes",Toast.LENGTH_SHORT).show();
                        ContentValues values = new ContentValues();
                        values.put(DBOpenHelper.HABIT_STREAK, 0);
                        getContentResolver().update(HabitsProvider.CONTENT_URI, values, habitFilter, null);

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
