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

public class PointsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private CursorAdapter cursorAdapter;
    private String habitFilter;

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
        int points = getTotal();
        tv.setText(Integer.toString(points));

        getLoaderManager().initLoader(0, null, this);
    }

    /**
     * This method calculates the sum of the points for all habits stored in the database
     * by using a cursor to iterate over each row in the database and retrieve the points value
     * @return total points value
     */

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





