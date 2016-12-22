package com.example.android.turnip_habit_tracking_app;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
/**
 * <h1>Habit Tracking App</h1>
 * The Habit Tracking App implements an application that
 * allows for the addition of habits and enables the setting of
 * an alarm to remind the user to perform the habit.
 * <p>
 *
 * @author  Kevin Lee
 * @version 1.0
 * @since   21-12-2016
 */
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int EDITOR_REQUEST_CODE = 1001;
    private CursorAdapter cursorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        cursorAdapter = new HabitsCursorAdapter(this, null, 0);
        ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(cursorAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                Uri uri = Uri.parse(HabitsProvider.CONTENT_URI + "/" + id);
                intent.putExtra(HabitsProvider.CONTENT_ITEM_TYPE, uri);
                startActivityForResult(intent, EDITOR_REQUEST_CODE);
            }
        });
        getLoaderManager().initLoader(0, null, this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);



    }

    /**
     * Inflates the menu; This adds items to the action bar if it is present.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * This method calls the respective methods on selection of items
     * in the action bar.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        if  (id == R.id.action_delete_all) {
            deleteAllHabits();
        }

        if (id == R.id.rewards){
            openRewards();
        }


        return super.onOptionsItemSelected(item);
    }

    /**
     * Opens up to the  Reward Activity
     */
    private void openRewards() {
        Intent intent = new Intent (this, EditorActivity.class);
        startActivity(intent);
    }

    /**
     * This method asks the user if they are sure they want to delete all habits ,
     * if returns positive , the habits get deleted and the loader is restarted.
     */
    private void deleteAllHabits() {
        DialogInterface.OnClickListener dialogClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int button) {
                        if (button == DialogInterface.BUTTON_POSITIVE) {
                            //Insert Data management code here
                            getContentResolver().delete(HabitsProvider.CONTENT_URI, null, null);
                            restartLoader();
                            Toast.makeText(MainActivity.this,
                                    getString(R.string.all_habits_deleted),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage((getString(R.string.are_you_sure)))
                .setPositiveButton(getString(android.R.string.yes), dialogClickListener)
                .setNegativeButton(getString(android.R.string.no), dialogClickListener)
                .show();

    }

    /**
     * This method restarts the Loader and is used to refresh the listView when the database
     * has changed.
     */
    private void restartLoader() {
        getLoaderManager().restartLoader(0, null, this);
    }

    /**
     * This method creates the Loader
     * @param id
     * @param args
     * @return
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, HabitsProvider.CONTENT_URI, null, null, null, null);
    }

    /**
     * Swap in a new Cursor, returning the old Cursor
     * @param loader
     * @param data
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }

    /**
     * This method opens up the Editor Activity when the Floating Action
     * Button is pressed
     * @param view
     */
    public void openEditorForNewHabit(View view) {
        Intent intent = new Intent(this, EditorActivity.class);
        startActivityForResult(intent, EDITOR_REQUEST_CODE);
    }

    /**
     * This method checks if there has been a change in the database and if
     * the edited action has been completed then restart the loader to reflect these changes.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDITOR_REQUEST_CODE && resultCode == RESULT_OK) {
            restartLoader();
        }
    }

}