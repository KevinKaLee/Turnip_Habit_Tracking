package com.example.android.turnip_habit_tracking_app;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.CheckBox;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.Calendar;


import static org.junit.Assert.*;

/**
 * Created by eoint on 12/23/2016.
 */
public class AlarmTest extends Instrumentation{
    @Test
    public void checkDAY() throws Exception {
        Calendar testCal;
        testCal = Calendar.getInstance();
        int hour   = testCal.get(Calendar.HOUR_OF_DAY);
        int minute = testCal.get(Calendar.MINUTE);
        long output = testCal.getTimeInMillis();
        testCal.set(Calendar.MINUTE,minute-1);
        testCal.set(Calendar.HOUR_OF_DAY,hour-1);
        Alarm alarm = new Alarm();
        alarm.checkDAY(testCal);
        boolean isGreaterthan = testCal.getTimeInMillis()>output;
        assertTrue(isGreaterthan);
    }
}