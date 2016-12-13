package com.example.android.turnip_habit_tracking_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

    // constants for db name and version
    private static final String DATABASE_NAME = "habits.db";
    private static final int DATABASE_VERSION = 1;

    // Constants for identifying table and columns
    public static final String TABLE_HABITS = "habits";
    public static final String HABIT_ID = "_id";
    public static final String HABIT_NAME = "habitName";
    public static final String HABIT_START_DATE = "startDate";
    public static final String HABIT_START_TIME = "startTime";
    public static final String HABIT_CREATED = "habitCREATED";

    public static final String[] ALL_COLUMNS =
            {HABIT_ID,HABIT_NAME,HABIT_START_DATE,HABIT_START_TIME,HABIT_CREATED};

    //SQL to create Table
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_HABITS + " ( " +
                HABIT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                HABIT_NAME + " TEXT, " +
                HABIT_START_DATE + " TEXT, " +
                HABIT_START_TIME + " TEXT, " +
                HABIT_CREATED + " TEXT default CURRENT_TIMESTAMP " +
                " ) " ;



    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXIST" + HABIT_NAME );
        onCreate(db);
    }


}
