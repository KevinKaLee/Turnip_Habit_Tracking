package com.example.android.turnip_habit_tracking_app;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.Toast;

public class AlarmTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_test);

        Bundle bundle = getIntent().getExtras();
        double habitID = bundle.getDouble("ID");

        Toast.makeText(getApplicationContext(),String.valueOf(habitID),Toast.LENGTH_SHORT).show();
    }
}