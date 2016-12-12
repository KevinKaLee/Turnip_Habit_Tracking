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

    Button     setAlarm;
    AlarmManager alarmManager;
    Calendar calendar;
    PendingIntent monIntent, tuesIntent, wedIntent, thursIntent, friIntent, satIntent, sunIntent;

    CheckBox Monday;
    CheckBox Tuesday;
    CheckBox Wednesday;
    CheckBox Thursday;
    CheckBox Friday;
    CheckBox Saturday;
    CheckBox Sunday;

    boolean mon = false;
    boolean tues  = false;
    boolean wed   = false;
    boolean thurs = false;
    boolean fri   = false;
    boolean sat   = false;
    boolean sun   = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        setTitle("Create Habit Alarm");

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        setAlarm     = (Button)     findViewById(R.id.setTime);
        calendar     = Calendar.getInstance();
        Monday       = (CheckBox)findViewById(R.id.Monday);
        Tuesday      = (CheckBox)findViewById(R.id.Tuesday);
        Wednesday    = (CheckBox)findViewById(R.id.Wednesday);
        Thursday     = (CheckBox)findViewById(R.id.Thursday);
        Friday       = (CheckBox)findViewById(R.id.Friday);
        Saturday     = (CheckBox)findViewById(R.id.Saturday);
        Sunday       = (CheckBox)findViewById(R.id.Sunday);

        Monday.setOnClickListener(this);
        Tuesday.setOnClickListener(this);
        Wednesday.setOnClickListener(this);
        Thursday.setOnClickListener(this);
        Friday.setOnClickListener(this);
        Saturday.setOnClickListener(this);
        Sunday.setOnClickListener(this);

        // Intent for the Alarm Receiver Class
        setAlarm.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        // Get  Hour & Minute of Day
                        int currentHour   = calendar.get(Calendar.HOUR_OF_DAY);
                        int currentMinute = calendar.get(Calendar.MINUTE);
                        // Time Dialog for User
                        TimePickerDialog mTimerPicker;
                        mTimerPicker = new TimePickerDialog(Alarm.this, new TimePickerDialog.OnTimeSetListener(){
                            @Override
                            public void onTimeSet(TimePicker timePicker, int alarmHour, int alarmMinute) {
                                // Add Selected Hour to Alarm
                                calendar.set(Calendar.MINUTE,alarmMinute);
                                // Add Selected Minute to Alarm
                                calendar.set(Calendar.HOUR_OF_DAY,alarmHour);
                            }
                        }, currentHour,currentMinute,true);
                        mTimerPicker.show();
                    }
                }
        );
    }
    // Make Sure an Alarm is not set in the past
    public void checkDAY(){
        if(calendar.getTimeInMillis() < System.currentTimeMillis()){
            calendar.add(Calendar.DAY_OF_YEAR, 7);
        }
    }

    // Switch Used to Create Alarm Instance for Each Day of the Week, when clicked.
    @Override
    public void onClick(View day) {
        final Intent alarmRx = new Intent(Alarm.this, AlarmReceiver.class);
        int id = day.getId();
        switch (id) {
            case R.id.Monday:
                mon = !mon;
                if(mon){
                    calendar.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
                    checkDAY();
                    monIntent = PendingIntent.getBroadcast(Alarm.this, 1, alarmRx, PendingIntent.FLAG_UPDATE_CURRENT);
                }
                else{
                    PendingIntent.getBroadcast(Alarm.this, 1, alarmRx, PendingIntent.FLAG_UPDATE_CURRENT).cancel();
                }
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),monIntent);
                break;
            case R.id.Tuesday:
                tues = !tues;
                if(tues){
                    calendar.set(Calendar.DAY_OF_WEEK,Calendar.TUESDAY);
                    checkDAY();
                    tuesIntent = PendingIntent.getBroadcast(Alarm.this, 2, alarmRx, PendingIntent.FLAG_UPDATE_CURRENT);
                }
                else{
                    PendingIntent.getBroadcast(Alarm.this, 2, alarmRx, PendingIntent.FLAG_UPDATE_CURRENT).cancel();
                }
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),tuesIntent);
                break;
            case R.id.Wednesday:
                wed = !wed;
                if(wed){
                    calendar.set(Calendar.DAY_OF_WEEK,Calendar.WEDNESDAY);
                    checkDAY();
                    wedIntent = PendingIntent.getBroadcast(Alarm.this, 3, alarmRx, PendingIntent.FLAG_UPDATE_CURRENT);
                }
                else{
                    PendingIntent.getBroadcast(Alarm.this, 3, alarmRx, PendingIntent.FLAG_UPDATE_CURRENT).cancel();
                }
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),wedIntent);
                break;
            case R.id.Thursday:
                thurs = !thurs;
                if(thurs){
                    calendar.set(Calendar.DAY_OF_WEEK,Calendar.THURSDAY);
                    checkDAY();
                    thursIntent = PendingIntent.getBroadcast(Alarm.this, 4, alarmRx, PendingIntent.FLAG_UPDATE_CURRENT);
                }
                else{
                    PendingIntent.getBroadcast(Alarm.this, 4, alarmRx, PendingIntent.FLAG_UPDATE_CURRENT).cancel();
                }
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),thursIntent);
                break;
            case R.id.Friday:
                fri = !fri;
                if(fri){
                    calendar.set(Calendar.DAY_OF_WEEK,Calendar.FRIDAY);
                    checkDAY();
                    friIntent = PendingIntent.getBroadcast(Alarm.this, 5, alarmRx, PendingIntent.FLAG_UPDATE_CURRENT);
                }
                else{
                    PendingIntent.getBroadcast(Alarm.this, 5, alarmRx, PendingIntent.FLAG_UPDATE_CURRENT).cancel();
                }
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),friIntent);
                break;
            case R.id.Saturday:
                sat = !sat;
                if(sat){
                    calendar.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
                    checkDAY();
                    satIntent = PendingIntent.getBroadcast(Alarm.this, 6, alarmRx, PendingIntent.FLAG_UPDATE_CURRENT);
                }
                else{
                    PendingIntent.getBroadcast(Alarm.this, 6, alarmRx, PendingIntent.FLAG_UPDATE_CURRENT).cancel();
                }
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),satIntent);
                break;
            case R.id.Sunday:
                sun = !sun;
                if(sun){
                    calendar.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
                    checkDAY();
                    sunIntent = PendingIntent.getBroadcast(Alarm.this, 7, alarmRx, PendingIntent.FLAG_UPDATE_CURRENT);
                }
                else{
                    PendingIntent.getBroadcast(Alarm.this, 7, alarmRx, PendingIntent.FLAG_UPDATE_CURRENT).cancel();
                }
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),sunIntent);
                break;
            default:
                Toast.makeText(getApplicationContext(),"Default",Toast.LENGTH_SHORT).show();
        }
    }
}
