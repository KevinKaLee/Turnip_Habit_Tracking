package com.example.android.turnip_habit_tracking_app;

/**
 * Created by emma on 21/12/16.
 */

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class PointsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private CursorAdapter cursorAdapter;
    private String habitFilter;
    private  Uri uri;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cursorAdapter = new PointsAdapter(this, null, 0);
        ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(cursorAdapter);

        TextView tv = (TextView) findViewById(R.id.PointsTotal);
        int points = getTotal();//DBOpenHelper.getPointsTotal();
        tv.setText(Integer.toString(points));

        getLoaderManager().initLoader(0, null, this);

        Toast.makeText(getApplicationContext(),"Creating",Toast.LENGTH_SHORT).show();
    }


    private void restartLoader() {
        getLoaderManager().restartLoader(0, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, HabitsProvider.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }


    public int getTotal() {
        int total = 0;
        Cursor cursor = getContentResolver().query(HabitsProvider.CONTENT_URI, DBOpenHelper.ALL_COLUMNS, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            // null could happen if we used our empty constructor
            int points = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.HABIT_POINTS));
            total+=points;
            cursor.moveToNext();
        }
        return total;
    }


}
