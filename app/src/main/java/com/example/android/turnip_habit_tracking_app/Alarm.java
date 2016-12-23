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
/**
 * <h1>Alarm</h1>
 * This class generates an alarm which tells the user a habit session is about to begin
 * and also an alarm when the habit session ends.
 * Alarms can be repeated for specified days of the week
 * <p>
 *
 * @author  Eoin Thompson
 * @version 1.0
 * @since   12-12-2016
 */
public class Alarm extends AppCompatActivity implements View.OnClickListener {

    private String habitID;          // ID of the Habit
    private Button startTime;        // Set Start Time of Habit Session
    private Button finishTime;       // Set Finish Time of Habit Session
    private Button saveButton;       // Save Alarm button

    Calendar startCal;               // Calendar - Start Alarm
    Calendar finishCal;              // Calendar - End Alarm

    CheckBox Monday;                 // Monday checkbox
    CheckBox Tuesday;                // Tuesday checkbox
    CheckBox Wednesday;              // Wednesday checkbox
    CheckBox Thursday;               // Thursday checkbox
    CheckBox Friday;                 // Friday checkbox
    CheckBox Saturday;               // Saturday checkbox
    CheckBox Sunday;                 // Sunday checkbox

    boolean  mon   = false;           // Switch for Monday checkbox
    boolean  tues  = false;           // Switch for Tuesday checkbox
    boolean  wed   = false;           // Switch for Wednesday checkbox
    boolean  thurs = false;           // Switch for Thursday checkbox
    boolean  fri   = false;           // Switch for Friday checkbox
    boolean  sat   = false;           // Switch for Saturday checkbox
    boolean  sun   = false;           // Switch for Sunday checkbox

    Intent   startAlarm;              // Start Alarm Receiver Intent
    Intent   endAlarm;                // End Alarm Receiver Intent
    AlarmManager alarmMan;            // Instance of Alarm Manager Class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        setTitle("Create Habit Alarm");

        Bundle bundle1 = getIntent().getExtras();
        habitID        = bundle1.getString("ID");
        init();
        initClickListen();
    }
    /**
     * Initializes the variables for the Alarm class.
     * <p>
     * This method intialises the check box for each day of the weak,
     * the buttons used to set the start, finish time and to save the alarm
     * and also the calendar used to create the start and finish alarms.
     * The method also adds the habit ID into the intents.
     */
    private void init() {
        startTime      = (Button) findViewById(R.id.StartButton);
        finishTime     = (Button) findViewById(R.id.FinishButton);
        startCal       = Calendar.getInstance();
        finishCal      = Calendar.getInstance();
        startAlarm     = new Intent(Alarm.this, AlarmReceiver.class);
        endAlarm       = new Intent(Alarm.this,SessionOverRx.class);
        saveButton     = (Button) findViewById(R.id.SaveAlarm);
        alarmMan       = (AlarmManager) getSystemService(ALARM_SERVICE);
        Monday         = (CheckBox) findViewById(R.id.Monday);
        Tuesday        = (CheckBox) findViewById(R.id.Tuesday);
        Wednesday      = (CheckBox) findViewById(R.id.Wednesday);
        Thursday       = (CheckBox) findViewById(R.id.Thursday);
        Friday         = (CheckBox) findViewById(R.id.Friday);
        Saturday       = (CheckBox) findViewById(R.id.Saturday);
        Sunday         = (CheckBox) findViewById(R.id.Sunday);
        Bundle bundle2 = new Bundle();
        bundle2.putString("ID",habitID);
        startAlarm.putExtras(bundle2);
        endAlarm.putExtras(bundle2);
    }
    /**
     * Sets the click listeners for checkboxes & buttons.
     * <p>
     * This method sets the click listeners for the seven
     * check boxes and three button.
     */
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
    /**
     * This method creates the start & end alarms.
     * <p>
     * Each day of the week has a start and end alarm associated
     * with it. These are enabled when the user clicks the
     * corresponding checkbox.
     * Once this method is called the alarms are created.
     */
    private void createAlarms() {
        alarmManager(startCal ,2,1,mon  ,startAlarm);
        alarmManager(finishCal,2,1,mon  ,endAlarm);
        alarmManager(startCal ,3,2,tues ,startAlarm);
        alarmManager(finishCal,3,2,tues ,endAlarm);
        alarmManager(startCal ,4,3,wed  ,startAlarm);
        alarmManager(finishCal,4,3,wed  ,endAlarm);
        alarmManager(startCal ,5,4,thurs,startAlarm);
        alarmManager(finishCal,5,4,thurs,endAlarm);
        alarmManager(startCal ,6,5,fri  ,startAlarm);
        alarmManager(finishCal,6,5,fri  ,endAlarm);
        alarmManager(startCal ,7,6,sat  ,startAlarm);
        alarmManager(finishCal,7,6,sat  ,endAlarm);
        alarmManager(startCal ,1,7,sun  ,startAlarm);
        alarmManager(finishCal,1,7,sun  ,endAlarm);
    }
    /**
     * This method sets the time for the alarm.
     * <p>
     * The method uses a Timer Dialog to prompt the user
     * for the hour and minute the alarm should occur at.
     * This time is added into a calendar, which is used
     * when creating an alarm.
     * @param  myCalendar  calendar that will be used to create the alarm
     */
    private void setAlarmTime(final Calendar myCalendar){
        int hour   = myCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = myCalendar.get(Calendar.MINUTE);
        TimePickerDialog mTimerPicker;
        mTimerPicker = new TimePickerDialog(Alarm.this, new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker, int alarmHour, int alarmMinute) {
                myCalendar.set(Calendar.MINUTE,alarmMinute);
                myCalendar.set(Calendar.HOUR_OF_DAY,alarmHour);
            }
        }, hour,minute,true);
        mTimerPicker.show();
    }
    /**
     * This method checks if the alarm is set in the past
     * <p>
     * This method checks if the time and date given to an alarm
     * are in the past, if so it adds one week to the alarm time.
     * @param  calendar  calendar that will be used to create the alarm
     */
    private void checkDAY(Calendar calendar){
        if(calendar.getTimeInMillis() < System.currentTimeMillis()){
            Toast.makeText(getApplicationContext(),"True",Toast.LENGTH_SHORT).show();
            calendar.add(Calendar.DAY_OF_YEAR, 7);
        }
    }
    /**
     * This method creates an alarm for a given date & time
     * <p>
     * alarmManager is used to create an alarm for a given time & date. The
     * time is set using the setAlarmTime method and the calendar is input this method.
     * The date is set using the switch associated with the checkbox
     * @param  calendar  calendar that will be used to create the alarm
     * @param  day  which day the alarm should occur on, 1 - Sunday, 2 - Monday etc.
     * @param  uniqueId each alarm is given a different id to avoid overwritting alarms
     * @param  start  switch used to create or cancel the alarm
     * @param  intent intent determine which reveiver the alarm uses
     */
    private void alarmManager(Calendar calendar, int day, int uniqueId, boolean start, Intent intent){
        PendingIntent alarmIntent = PendingIntent.getBroadcast(Alarm.this, uniqueId, intent, 0);
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