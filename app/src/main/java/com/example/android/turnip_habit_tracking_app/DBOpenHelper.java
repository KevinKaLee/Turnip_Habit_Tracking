package com.example.android.turnip_habit_tracking_app;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This Class is a helper class that helps manage database creation.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    // constants for db name and version
    public static final String DATABASE_NAME = "habits.db";
    private static final int DATABASE_VERSION = 1;

    // Constants for identifying table and columns
    public static final String TABLE_HABITS = "habits";
    public static final String HABIT_ID = "_id";               // Key identifier making each habit uniquely identifiable
    public static final String HABIT_NAME = "habitName";
    public static final String HABIT_DESC = "habitDescription";
    public static final String HABIT_CREATED = "habitCreated"; // Used for showing that the habit has been created
    public static final String HABIT_POINTS = "habitPoints";
    public static final String HABIT_STREAK = "habitStreak";

    public static final String[] ALL_COLUMNS = {HABIT_ID,HABIT_NAME,HABIT_DESC,HABIT_CREATED, HABIT_POINTS, HABIT_STREAK};

    //SQL to create Table
    private static final String TABLE_CREATE =
            " CREATE TABLE " + TABLE_HABITS + " ( " +
                    HABIT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    HABIT_NAME + " TEXT, " +
                    HABIT_DESC + " TEXT, " +
                    HABIT_CREATED + " TEXT default CURRENT_TIMESTAMP, " +
                    HABIT_POINTS + " INTEGER default 0, " +
                    HABIT_STREAK + " INTEGER default 0 " +
                    " ) " ;


    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Method to instantiate the database on the creation of SQLiteDatabase object
     * Only runs when database file does not exist and was just created. Does not get called
     * after first time execution.
     * @param db This is the database object
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    /**
     * This Method is called when the database file exist but stored version number is lower
     * then requested in constructor. Will update table schema to the requested version and
     * delete old database.
     * @param db This is the database object
     * @param oldversion This is the old version number of the database
     * @param newversion This is the new version number of the database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        db.execSQL("DROP TABLE IF EXIST" + HABIT_NAME );
        onCreate(db);
    }

}