package com.example.android.turnip_habit_tracking_app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void onClickHabits(View view){
        Intent intent = new Intent(this, MainHabitsActivity.class);
        startActivity(intent);
    }

    public void onClickPoints(View view){
        Intent intent = new Intent(this, PointsActivity.class);
        startActivity(intent);
    }

}
