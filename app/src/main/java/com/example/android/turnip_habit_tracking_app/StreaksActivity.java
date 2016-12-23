package com.example.android.turnip_habit_tracking_app;

/**
 * Created by emma on 21/12/16.
 *
 * Populates the View Points screen which displays a list of habits with their respective points
 * and a total.
 */

import android.database.Cursor;
import android.os.Bundle;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

public class StreaksActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private CursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streaks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cursorAdapter = new DataAdapter(this, null, 0);
        ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(cursorAdapter);

        TextView tv = (TextView) findViewById(R.id.DataValue);

        getLoaderManager().initLoader(0, null, this);
    }

    /**
     * This method calculates the sum of the points for all habits stored in the database
     * by using a cursor to iterate over each row in the database and retrieve the points value
     * @return total points value
     */


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

}





