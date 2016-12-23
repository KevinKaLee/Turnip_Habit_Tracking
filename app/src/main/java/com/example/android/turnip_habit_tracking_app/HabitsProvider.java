package com.example.android.turnip_habit_tracking_app;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Content Provider Class used to manage access with the database providing co-ordinated access
 * to the Content Resolver Object.
 */

public class HabitsProvider extends ContentProvider{

    private static final String AUTHORITY = "com.example.android.turnip_habit_tracking_app.habitsprovider";
    private static final String BASE_PATH = "habits";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH );


    // Constant to identify the requested operation
    private static final int HABITS = 1;
    private static final int HABITS_ID = 2;
    private static  final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    public static final String CONTENT_ITEM_TYPE  = "Habit";

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, HABITS);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", HABITS_ID);
    }
    private SQLiteDatabase database;

    /**
     * Once database is opened, it is the cached. getWritableDatabase() returns a
     * SQLiteDatabase which provides methods to perform SQL queries.
     * @return
     */
    @Override
    public boolean onCreate() {
        DBOpenHelper helper = new DBOpenHelper(getContext());
        database = helper.getWritableDatabase();
        return true;
    }

    /**
     *  Matches the uri with the unique identifier for the habit and returns back that row.
     * @param uri
     * @param strings
     * @param selection
     * @param strings1
     * @param s1
     * @return
     */
    @Override
    public Cursor query(Uri uri, String[] strings, String selection , String[] strings1, String s1) {

        if (uriMatcher.match(uri) == HABITS_ID) {
            selection = DBOpenHelper.HABIT_ID + "=" + uri.getLastPathSegment();
        }

        return database.query(DBOpenHelper.TABLE_HABITS, DBOpenHelper.ALL_COLUMNS, selection, null,null,null, DBOpenHelper.HABIT_CREATED + " DESC ");
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    /**
     * Inserts in a new row into the database
     * @param uri
     * @param contentValues
     * @return
     */
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        long id = database.insert(DBOpenHelper.TABLE_HABITS, null ,contentValues);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    /**
     * Deletes the corresponding row
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return database.delete(DBOpenHelper.TABLE_HABITS,selection,selectionArgs);
    }

    /**
     * Updates the corresponding row
     * @param uri
     * @param contentValues
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int update( Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        return database.update(DBOpenHelper.TABLE_HABITS, contentValues , selection,selectionArgs);
    }



}