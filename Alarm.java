package com.example.eoint.turnip;

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


    Button   setAlarm;
    Calendar calendar;

    CheckBox Monday;
    CheckBox Tuesday;
    CheckBox Wednesday;
    CheckBox Thursday;
    CheckBox Friday;
    CheckBox Saturday;
    CheckBox Sunday;

    boolean mon   = false;
    boolean tues  = false;
    boolean wed   = false;
    boolean thurs = false;
    boolean fri   = false;
    boolean sat   = false;
    boolean sun   = false;

    Intent alarmRx;
    AlarmManager alarmMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        setTitle("Create Habit Alarm");
        // Initialise Variables
        init();
        // Set OnClick Listeners
        initClickListen();


    }

    private void init(){
        setAlarm     = (Button)     findViewById(R.id.setTime);
        calendar     = Calendar.getInstance();
        alarmRx      =  new Intent(Alarm.this, AlarmReceiver.class);
        alarmMan     =  (AlarmManager) getSystemService(ALARM_SERVICE);
        Monday       = (CheckBox)findViewById(R.id.Monday);
        Tuesday      = (CheckBox)findViewById(R.id.Tuesday);
        Wednesday    = (CheckBox)findViewById(R.id.Wednesday);
        Thursday     = (CheckBox)findViewById(R.id.Thursday);
        Friday       = (CheckBox)findViewById(R.id.Friday);
        Saturday     = (CheckBox)findViewById(R.id.Saturday);
        Sunday       = (CheckBox)findViewById(R.id.Sunday);
    }

    private void initClickListen(){
        Monday   .setOnClickListener(this);
        Tuesday  .setOnClickListener(this);
        Wednesday.setOnClickListener(this);
        Thursday .setOnClickListener(this);
        Friday   .setOnClickListener(this);
        Saturday .setOnClickListener(this);
        Sunday   .setOnClickListener(this);
        // Intent for the Alarm Receiver Class
        setAlarm.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        setAlarmTime();
                    }
                }
        );

    }

    private void setAlarmTime(){
        int hour   = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog mTimerPicker;
        mTimerPicker = new TimePickerDialog(Alarm.this, new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker, int alarmHour, int alarmMinute) {
                calendar.set(Calendar.MINUTE,alarmMinute);
                calendar.set(Calendar.HOUR_OF_DAY,alarmHour);
            }
        }, hour,minute,true);
        mTimerPicker.show();
    }

    private void checkDAY(Calendar calendar){
        if(calendar.getTimeInMillis() < System.currentTimeMillis()){
            calendar.add(Calendar.DAY_OF_YEAR, 7);
        }
    }

    private void alarmManager(Calendar calendar, int day, int uniqueId, boolean start){
        PendingIntent alarmIntent = PendingIntent.getBroadcast(Alarm.this, uniqueId, alarmRx, 0);
        if(start){
            calendar.set(Calendar.DAY_OF_WEEK,day);
            checkDAY(calendar);
            alarmMan.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY *7 , alarmIntent);
        }
        else{
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
                mon = !mon;
                alarmManager(calendar,2,1,mon);
                break;
            case R.id.Tuesday:
                tues = !tues;
                alarmManager(calendar,3,2,tues);
                break;
            case R.id.Wednesday:
                wed = !wed;
                alarmManager(calendar,4,3,wed);
                break;
            case R.id.Thursday:
                thurs = !thurs;
                alarmManager(calendar,5,4,thurs);
                break;
            case R.id.Friday:
                fri = !fri;
                alarmManager(calendar,6,5,fri);
                break;
            case R.id.Saturday:
                sat = !sat;
                alarmManager(calendar,7,6,sat);
                break;
            case R.id.Sunday:
                sun = !sun;
                alarmManager(calendar,1,7,sun);
                break;
            default:
                Toast.makeText(getApplicationContext(),"Default",Toast.LENGTH_SHORT).show();
        }
    }
}

