package com.example.android.turnip_habit_tracking_app;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.*;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

@RunWith(AndroidJUnit4.class)
public class DBtest {
    private Context appContext;

    @Before
    public void useAppContext() throws Exception {
        // Context of the app under test.
        appContext = InstrumentationRegistry.getTargetContext();
        DBOpenHelper dbOpenHelper = new DBOpenHelper(appContext);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        assertEquals("com.example.android.turnip_habit_tracking_app", appContext.getPackageName());
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testPoints() {


    }
}