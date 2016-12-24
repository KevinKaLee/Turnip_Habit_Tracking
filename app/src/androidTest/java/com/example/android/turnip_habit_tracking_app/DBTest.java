package com.example.android.turnip_habit_tracking_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by kevin on 23/12/16.
 */
@RunWith(AndroidJUnit4.class)
public class DBTest {
    private Context appContext;
    private static String habitName ="Go to the Gym";
    private static String habitDesc = "Workout Legs";
    private static long habitID = 1;


    /**
     * This method asserts that the correct Context is being used
     * @throws Exception
     */
    @Before
    public void useAppContext() throws Exception {
        // Context of the app under test.
        appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.android.turnip_habit_tracking_app", appContext.getPackageName());
    }


    /**
     * This method is a tear down of the table at the end of the test
     */
    @After
    public void testDropDB(){
        assertTrue("Test DropDB pass", appContext.deleteDatabase(DBOpenHelper.DATABASE_NAME));
    }

    /**
     * This method tests the creation of the database
     */
    @Test
    public void testCreateDB(){
        DBOpenHelper dbOpenHelper = new DBOpenHelper(appContext);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        assertTrue("Test CreateDB pass ", db.isOpen());
    }

    /**
     * This method tests the insertion of data into the database
     */
    @Test
    public void testInsertData(){
        DBOpenHelper dbOpenHelper = new DBOpenHelper(appContext);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();


        ContentValues contentValues = new ContentValues();
        contentValues.put(DBOpenHelper.HABIT_NAME, habitName);
        contentValues.put(DBOpenHelper.HABIT_DESC,habitDesc);

        habitID = db.insert(DBOpenHelper.TABLE_HABITS , null, contentValues);
        assertTrue("Test InsertData pass", habitID!= -1);



    }

    /**
     * This method tests if the data retrieved from the database correct
     */
    @Test
    public void testIsDataCorrectInDB() {
        DBOpenHelper dbOpenHelper = new DBOpenHelper(appContext);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();


        ContentValues contentValues = new ContentValues();
        contentValues.put(DBOpenHelper.HABIT_NAME, habitName);
        contentValues.put(DBOpenHelper.HABIT_DESC,habitDesc);

        habitID = db.insert(DBOpenHelper.TABLE_HABITS , null, contentValues);

        Cursor cursor = db.query(DBOpenHelper.TABLE_HABITS, null,null,null,null,null,null);
        assertTrue(cursor.moveToFirst());

        int idColumnIndex = cursor.getColumnIndex(DBOpenHelper.HABIT_ID);
        int dbID = cursor.getInt(idColumnIndex);

        int habitNameColumnIndex = cursor.getColumnIndex(DBOpenHelper.HABIT_NAME);
        String dbName = cursor.getString(habitNameColumnIndex);

        int habitDescColumnIndex = cursor.getColumnIndex(DBOpenHelper.HABIT_DESC);
        String dbDesc = cursor.getString(habitDescColumnIndex);

        assertEquals("Habit ID is correct" , habitID , dbID );
        assertEquals("Habit Name is correct", habitName , dbName);
        assertEquals("Habit Desc is correct", habitDesc, dbDesc);

    }

}
