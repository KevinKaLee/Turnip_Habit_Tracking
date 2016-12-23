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

    private String habitID;
    private Button startTime;                // Button used to set time of alarm
    private Button finishTime;
    private Button saveButton;               // Save Alarm button

    Calendar startCal;               // Calendar
    Calendar finishCal;

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

    Intent startAlarm;                  // Alarm Receiver Intent
    Intent endAlarm;
    AlarmManager alarmMan;           // Instance of Alarm Manager Class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        setTitle("Create Habit Alarm");

        Bundle bundle1 = getIntent().getExtras();
        habitID = bundle1.getString("ID");
        init();
        initClickListen();
        init();
        initClickListen();
    }
    // Initialise Variables
    private void init() {
        startTime = (Button) findViewById(R.id.StartButton);
        finishTime = (Button) findViewById(R.id.FinishButton);
        startCal = Calendar.getInstance();
        finishCal = Calendar.getInstance();
        startAlarm = new Intent(Alarm.this, AlarmReceiver.class);
        endAlarm = new Intent(Alarm.this,SessionOverRx.class);
        Bundle bundle2 = new Bundle();
        bundle2.putString("ID",habitID);
        startAlarm.putExtras(bundle2);
        endAlarm.putExtras(bundle2);
        saveButton = (Button) findViewById(R.id.SaveAlarm);
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
        startTime.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        setAlarmTime(startCal);
                    }
                }
        );
        finishTime.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        setAlarmTime(finishCal);
                    }
                }
        );
        saveButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        createAlarms();
                        Toast.makeText(getApplicationContext(),"Alarm Saved",Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }

    private void createAlarms() {
        alarmManager(startCal,2,1,mon,startAlarm);    // Creates Monday alarm
        alarmManager(finishCal,2,1,mon,endAlarm);    // Creates Monday alarm
        alarmManager(startCal,3,2,tues,startAlarm);   // Creates Tuesday alarm
        alarmManager(finishCal,3,2,tues,endAlarm);    // Creates Monday alarm
        alarmManager(startCal,4,3,wed,startAlarm);   // Creates Tuesday alarm
        alarmManager(finishCal,4,3,wed,endAlarm);    // Creates Monday alarm
        alarmManager(startCal,5,4,thurs,startAlarm);   // Creates Tuesday alar
        alarmManager(finishCal,5,4,thurs,endAlarm);    // Creates Monday alarm// m
        alarmManager(startCal,6,5,fri,startAlarm);   // Creates Tuesday alarm
        alarmManager(finishCal,6,5,fri,endAlarm);    // Creates Monday alarm
        alarmManager(startCal,7,6,sat,startAlarm);   // Creates Tuesday alarm
        alarmManager(finishCal,7,6,sat,endAlarm);    // Creates Monday alarm
        alarmManager(startCal,1,7,sun,startAlarm);   // Creates Tuesday alarm
        alarmManager(finishCal,1,7,sun,endAlarm);    // Creates Monday alarm
    }

    // Use Timer Dialog to input alarm time from user
    private void setAlarmTime(final Calendar myCalendar){
        int hour   = myCalendar.get(Calendar.HOUR_OF_DAY);        // Initial dialog hour set to current hour
        int minute = myCalendar.get(Calendar.MINUTE);             // Initial dialog minute set to current minute
        TimePickerDialog mTimerPicker;
        mTimerPicker = new TimePickerDialog(Alarm.this, new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker, int alarmHour, int alarmMinute) {
                myCalendar.set(Calendar.MINUTE,alarmMinute);      // Input alarm hour from user
                myCalendar.set(Calendar.HOUR_OF_DAY,alarmHour);   // Input alarm minute from user
            }
        }, hour,minute,true);
        mTimerPicker.show();
    }
    // Ensure alarm is not set for past date
    private void checkDAY(Calendar calendar){
        if(calendar.getTimeInMillis() < System.currentTimeMillis()){
            Toast.makeText(getApplicationContext(),"True",Toast.LENGTH_SHORT).show();
            calendar.add(Calendar.DAY_OF_YEAR, 7);
        }
    }
    // Create alarm for given week day
    private void alarmManager(Calendar calendar, int day, int uniqueId, boolean start, Intent intent){
        PendingIntent alarmIntent = PendingIntent.getBroadcast(Alarm.this, uniqueId, intent, 0);
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
                break;
            case R.id.Tuesday:
                tues = !tues;                      // Track state of checkbox
                break;
            case R.id.Wednesday:
                wed = !wed;                        // Track state of checkbox
                break;
            case R.id.Thursday:
                thurs = !thurs;                    // Track state of checkbox
                break;
            case R.id.Friday:
                fri = !fri;                        // Track state of checkbox
                break;
            case R.id.Saturday:
                sat = !sat;                        // Track state of checkbox
                break;
            case R.id.Sunday:
                sun = !sun;                        // Track state of checkbox
                break;
            default:
                Toast.makeText(getApplicationContext(),"Default",Toast.LENGTH_SHORT).show();
        }
    }
}