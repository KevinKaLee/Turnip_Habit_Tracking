package com.example.android.turnip_habit_tracking_app;

/**
 * Created by emma on 21/12/16.
 */


        import android.app.AlertDialog;
        import android.content.ContentValues;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.database.Cursor;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.design.widget.FloatingActionButton;
        import android.app.LoaderManager;
        import android.content.CursorLoader;
        import android.content.Loader;
        import android.support.v4.widget.CursorAdapter;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ListView;
        import android.widget.Toast;


public class PointsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private CursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cursorAdapter = new PointsAdapter(this, null, 0);
        ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(cursorAdapter);

        getLoaderManager().initLoader(0, null, this);
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

}
