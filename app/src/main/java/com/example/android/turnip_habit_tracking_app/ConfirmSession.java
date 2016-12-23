package com.example.android.turnip_habit_tracking_app;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ConfirmSession extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_session);

        Bundle bundle = getIntent().getExtras();
        final String habitID = bundle.getString("ID");

        // Simple Dialog - Increment Reward if Yes is clicked??
        new AlertDialog.Builder(this)
                .setTitle("Session Over")
                .setMessage("Have you completed the session?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //execute database query to increment points for given id
                        String habitFilter = DBOpenHelper.HABIT_ID +  "=" + habitID;
                        Cursor cursor = getContentResolver().query(HabitsProvider.CONTENT_URI, DBOpenHelper.ALL_COLUMNS, habitFilter, null, null);
                        cursor.moveToFirst();
                        int points = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.HABIT_POINTS));
                        points +=1;
                        ContentValues values = new ContentValues();
                        values.put(DBOpenHelper.HABIT_POINTS, points);
                        getContentResolver().update(HabitsProvider.CONTENT_URI, values, habitFilter, null);
                    }
                })

                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplication(),"Clicked - Yes",Toast.LENGTH_SHORT).show();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
