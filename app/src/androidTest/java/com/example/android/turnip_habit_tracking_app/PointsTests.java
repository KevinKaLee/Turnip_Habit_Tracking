package com.example.android.turnip_habit_tracking_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by emma on 24/12/16.
 */

public class PointsTests {

    private Context appContext;
    private static String habitName ="Go to the Gym";
    private static String habitDesc = "Workout Legs";
    private static long habitID = 1;



    @Before
    public void useAppContext() throws Exception {
        // Context of the app under test.
        appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.android.turnip_habit_tracking_app", appContext.getPackageName());

        DBOpenHelper dbOpenHelper = new DBOpenHelper(appContext);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();


        ContentValues contentValues = new ContentValues();
        contentValues.put(DBOpenHelper.HABIT_NAME, habitName);
        contentValues.put(DBOpenHelper.HABIT_DESC,habitDesc);

        habitID = db.insert(DBOpenHelper.TABLE_HABITS , null, contentValues);
        assertTrue("Test InsertData pass", habitID!= -1);
    }

    @After
    public void testDropDB(){
        assertTrue("Test DropDB pass", appContext.deleteDatabase(DBOpenHelper.DATABASE_NAME));

    }

    @Test
    public void totalPointsZeroTest() throws Exception {
        int i = getTotal();
    }
}
