package com.example.android.turnip_habit_tracking_app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Alarm extends AppCompatActivity implements View.OnClickListener {
    double habitID;
    Button setTime;                // Button used to set time of alarm
    Calendar calendar;               // Calendar

    CheckBox Monday;                 // Monday checkbox
    CheckBox Tuesday;                // Tuesday checkbox
    CheckBox Wednesday;              // Wednesday checkbox
    CheckBox Thursday;               // Thursday checkbox
    CheckBox Friday;                 // Friday checkbox
    CheckBox Saturday;               // Saturday checkbox
    CheckBox Sunday;                 // Sunday checkbox

    boolean mon   = false;           // Switch for Monday checkbox
    boolean tues  = false;           // Switch for Tuesday checkbox
    boolean wed   = false;           // Switch for Wednesday checkbox
    boolean thurs = false;           // Switch for Thursday checkbox
    boolean fri   = false;           // Switch for Friday checkbox
    boolean sat   = false;           // Switch for Saturday checkbox
    boolean sun   = false;           // Switch for Sunday checkbox

    Intent alarmRx;                  // Alarm Receiver Intent
    AlarmManager alarmMan;           // Instance of Alarm Manager Class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        setTitle("Create Habit Alarm");
        Bundle bundle1 = getIntent().getExtras();
        habitID = bundle1.getDouble("ID");
        init();
        initClickListen();
        Toast.makeText(getApplicationContext(),String.valueOf(habitID),Toast.LENGTH_SHORT).show();
    }
    // Initialise Variables
    private void init() {
        setTime = (Button) findViewById(R.id.setTime);
        calendar = Calendar.getInstance();
        alarmRx = new Intent(Alarm.this, AlarmReceiver.class);
        Bundle bundle2 = new Bundle();
        bundle2.putDouble("ID",habitID);
        alarmRx.putExtras(bundle2);
        alarmMan = (AlarmManager) getSystemService(ALARM_SERVICE);
        Monday = (CheckBox) findViewById(R.id.Monday);
        Tuesday = (CheckBox) findViewById(R.id.Tuesday);
        Wednesday = (CheckBox) findViewById(R.id.Wednesday);
        Thursday = (CheckBox) findViewById(R.id.Thursday);
        Friday = (CheckBox) findViewById(R.id.Friday);
        Saturday = (CheckBox) findViewById(R.id.Saturday);
        Sunday = (CheckBox) findViewById(R.id.Sunday);
    }
    // Initialise Listeners for each Checkbox
    private void initClickListen(){
        Monday   .setOnClickListener(this);
        Tuesday  .setOnClickListener(this);
        Wednesday.setOnClickListener(this);
        Thursday .setOnClickListener(this);
        Friday   .setOnClickListener(this);
        Saturday .setOnClickListener(this);
        Sunday   .setOnClickListener(this);
        // Set alarm time on button click
        setTime.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        setAlarmTime();
                    }
                }
        );
    }
    // Use Timer Dialog to input alarm time from user
    private void setAlarmTime(){
        int hour   = calendar.get(Calendar.HOUR_OF_DAY);        // Initial dialog hour set to current hour
        int minute = calendar.get(Calendar.MINUTE);             // Initial dialog minute set to current minute
        TimePickerDialog mTimerPicker;
        mTimerPicker = new TimePickerDialog(Alarm.this, new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker, int alarmHour, int alarmMinute) {
                calendar.set(Calendar.MINUTE,alarmMinute);      // Input alarm hour from user
                calendar.set(Calendar.HOUR_OF_DAY,alarmHour);   // Input alarm minute from user
            }
        }, hour,minute,true);
        mTimerPicker.show();
    }
    // Ensure alarm is not set for past date
    private void checkDAY(Calendar calendar){
        if(calendar.getTimeInMillis() < System.currentTimeMillis()){
            calendar.add(Calendar.DAY_OF_YEAR, 7);
        }
    }
    // Create alarm for given week day
    private void alarmManager(Calendar calendar, int day, int uniqueId, boolean start){
        PendingIntent alarmIntent = PendingIntent.getBroadcast(Alarm.this, uniqueId, alarmRx, 0);
        if(start){ // Checkbox is checked
            calendar.set(Calendar.DAY_OF_WEEK,day);
            checkDAY(calendar);
            alarmMan.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY *7 , alarmIntent);
        }
        else{ // Un-checking a box cancels the alarm
            alarmIntent.cancel();
            alarmMan.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
        }
    }

    // Switch Used to Create Alarm Instance for Each Day of the Week, when clicked.
    @Override
    public void onClick(View day) {
        int id = day.getId();
        switch (id) {
            case R.id.Monday:
                mon = !mon;                        // Track state of checkbox
                alarmManager(calendar,2,1,mon);    // Creates Monday alarm
                break;
            case R.id.Tuesday:
                tues = !tues;                      // Track state of checkbox
                alarmManager(calendar,3,2,tues);   // Creates Tuesday alarm
                break;
            case R.id.Wednesday:
                wed = !wed;                        // Track state of checkbox
                alarmManager(calendar,4,3,wed);    // Creates Wednesday alarm
                break;
            case R.id.Thursday:
                thurs = !thurs;                    // Track state of checkbox
                alarmManager(calendar,5,4,thurs);  // Creates Thursday alarm
                break;
            case R.id.Friday:
                fri = !fri;                        // Track state of checkbox
                alarmManager(calendar,6,5,fri);    // Creates Friday alarm
                break;
            case R.id.Saturday:
                sat = !sat;                        // Track state of checkbox
                alarmManager(calendar,7,6,sat);    // Creates Saturday alarm
                break;
            case R.id.Sunday:
                sun = !sun;                        // Track state of checkbox
                alarmManager(calendar,1,7,sun);    // Creates Sunday alarm
                break;
            default:
                Toast.makeText(getApplicationContext(),"Default",Toast.LENGTH_SHORT).show();
        }
    }
}
