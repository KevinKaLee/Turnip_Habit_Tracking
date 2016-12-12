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

    public alarmManager monday;
    public alarmManager tuesday;
    public alarmManager wednesday;
    public alarmManager thursday;
    public alarmManager friday;
    public alarmManager saturday;
    public alarmManager sunday;

    Button   setAlarm;
    Calendar calendar;
    Calendar test;

    CheckBox Monday;
    CheckBox Tuesday;
    CheckBox Wednesday;
    CheckBox Thursday;
    CheckBox Friday;
    CheckBox Saturday;
    CheckBox Sunday;

    boolean mon  = false;
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

        setAlarm     = (Button)     findViewById(R.id.setTime);
        calendar     = Calendar.getInstance();
        test         = Calendar.getInstance();
        Monday       = (CheckBox)findViewById(R.id.Monday);
        Tuesday      = (CheckBox)findViewById(R.id.Tuesday);
        Wednesday    = (CheckBox)findViewById(R.id.Wednesday);
        Thursday     = (CheckBox)findViewById(R.id.Thursday);
        Friday       = (CheckBox)findViewById(R.id.Friday);
        Saturday     = (CheckBox)findViewById(R.id.Saturday);
        Sunday       = (CheckBox)findViewById(R.id.Sunday);

         monday = new alarmManager();
         tuesday = new alarmManager();
         wednesday = new alarmManager();
         thursday = new alarmManager();
         friday = new alarmManager();
         saturday = new alarmManager();
         sunday = new alarmManager();

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

    private class alarmManager {
        int dayOfWeek;
        int tag;
        Calendar calendar;
        Intent alarmRx = new Intent(Alarm.this, AlarmReceiver.class);
        AlarmManager alarmMan = (AlarmManager) getSystemService(ALARM_SERVICE);

        private void getAlarmInfo(Calendar cal, int day, int t){
            calendar = cal;
            dayOfWeek = day;
            tag = t;
            calendar.set(Calendar.DAY_OF_WEEK,day);
        }

        private void checkDAY(Calendar cal){
            if(cal.getTimeInMillis() < System.currentTimeMillis()){
                cal.add(Calendar.DAY_OF_YEAR, 7);
            }
        }

        private void createAlarm(){
            checkDAY(calendar);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(Alarm.this, tag, alarmRx, 0);
            alarmMan.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
        }


        private void cancel(){
            PendingIntent alarmIntent = PendingIntent.getBroadcast(Alarm.this, tag, alarmRx, 0);
            alarmIntent.cancel();
            alarmMan.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
        }
    }
    // Switch Used to Create Alarm Instance for Each Day of the Week, when clicked.
    @Override
    public void onClick(View day) {
        //final Intent alarmRx = new Intent(Alarm.this, AlarmReceiver.class);
        int id = day.getId();
        switch (id) {
            case R.id.Monday:
                mon = !mon;
                if(mon){
                    monday.getAlarmInfo(calendar,2,1);
                    monday.createAlarm();
                }
                else{
                    monday.cancel();
                }
                break;
            case R.id.Tuesday:
                tues = !tues;
                if(tues){
                    test.set(Calendar.HOUR_OF_DAY,16);
                    test.set(Calendar.MINUTE,56);
                    tuesday.getAlarmInfo(test,2,2);
                    tuesday.createAlarm();
                }
                else{
                    tuesday.cancel();
                }
                break;
            case R.id.Wednesday:
                wed = !wed;
                if(wed){
                    wednesday.getAlarmInfo(calendar,4,3);
                    wednesday.createAlarm();
                }
                else{
                    wednesday.cancel();
                }
                break;
            case R.id.Thursday:
                thurs = !thurs;
                if(thurs){
                    thursday.getAlarmInfo(calendar,5,4);
                    thursday.createAlarm();
                }
                else{
                    thursday.cancel();
                }
                break;
            case R.id.Friday:
                fri = !fri;
                if(fri){
                    friday.getAlarmInfo(calendar,6,5);
                    friday.createAlarm();
                }
                else{
                    friday.cancel();
                }
                break;
            case R.id.Saturday:
                sat = !sat;
                if(sat){
                    saturday.getAlarmInfo(calendar,7,6);
                    saturday.createAlarm();
                }
                else{

                }
                break;
            case R.id.Sunday:
                sun = !sun;
                if(sun){
                    sunday.getAlarmInfo(calendar,1,7);
                    sunday.createAlarm();
                }
                else{
                    sunday.cancel();
                }
                break;
            default:
                Toast.makeText(getApplicationContext(),"Default",Toast.LENGTH_SHORT).show();
        }
    }
}

