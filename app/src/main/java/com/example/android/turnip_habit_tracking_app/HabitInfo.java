package com.example.android.turnip_habit_tracking_app;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


public class HabitInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_test);

        Bundle bundle = getIntent().getExtras();
        //String Table_Name = "habits.db";
        String habitID = bundle.getString("ID");


        Toast.makeText(getApplicationContext(),habitID,Toast.LENGTH_SHORT).show();
    }
}
