package com.example.android.turnip_habit_tracking_app;

<<<<<<< HEAD
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
=======
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.Toast;
>>>>>>> 868c07b0cd8bdb70975378bcf46913cf84d78e9d

public class AlarmTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_test);

        Bundle bundle = getIntent().getExtras();
        double habitID = bundle.getDouble("ID");

        Toast.makeText(getApplicationContext(),String.valueOf(habitID),Toast.LENGTH_SHORT).show();
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 868c07b0cd8bdb70975378bcf46913cf84d78e9d
