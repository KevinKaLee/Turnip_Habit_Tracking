package com.example.android.turnip_habit_tracking_app;

import android.content.Context;
import android.database.Cursor;
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
    public static final String HABIT_DESC = "habitDescription";
<<<<<<< HEAD
    public static final String HABIT_CREATED = "habitCreated";
=======
    public static final String HABIT_CREATED = "habitCreated"; // Used for showing that the habit has been created
>>>>>>> becb5aba28c3d48769ad0186fc7602a956257370
    public static final String HABIT_POINTS = "habitPoints";

    public static final String[] ALL_COLUMNS = {HABIT_ID,HABIT_NAME,HABIT_DESC,HABIT_CREATED, HABIT_POINTS};

    //SQL to create Table
    private static final String TABLE_CREATE =
            " CREATE TABLE " + TABLE_HABITS + " ( " +
                    HABIT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    HABIT_NAME + " TEXT, " +
                    HABIT_DESC + " TEXT, " +
                    HABIT_CREATED + " TEXT default CURRENT_TIMESTAMP, " +
                    HABIT_POINTS + " INTEGER default 0 " +
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

<<<<<<< HEAD

}
=======
}
>>>>>>> becb5aba28c3d48769ad0186fc7602a956257370
