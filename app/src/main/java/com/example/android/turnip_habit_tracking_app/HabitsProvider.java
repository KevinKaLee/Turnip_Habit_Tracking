package com.example.android.turnip_habit_tracking_app;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

public class HabitsProvider extends ContentProvider{

    private static final String AUTHORITY = "com.example.android.turnip_habit_tracking_app.habitsprovider";
    private static final String BASE_PATH = "habits";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH );


    // Constant to identify the requested operation
    private static final int HABITS = 1;
    private static final int HABITS_ID = 2;
    private static  final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, HABITS);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", HABITS_ID);
    }
    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        DBOpenHelper helper = new DBOpenHelper(getContext());
        database = helper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String selection , String[] strings1, String s1) {
        return database.query(DBOpenHelper.TABLE_HABITS, DBOpenHelper.ALL_COLUMNS,selection,null,null,null,DBOpenHelper.HABIT_CREATED + " DESC");
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        long id = database.insert(DBOpenHelper.TABLE_HABITS, null ,contentValues);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return database.delete(DBOpenHelper.TABLE_HABITS,selection,selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        return database.update(DBOpenHelper.TABLE_HABITS, contentValues , selection,selectionArgs);
    }
}
