package com.example.android.turnip_habit_tracking_app;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditorActivity extends AppCompatActivity {

    private String action;
    private EditText editor;
    private static final int ALARM_REQUEST_CODE = 1002;
    private String habitFilter;
    private String oldText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        editor = (EditText) findViewById(R.id.editText);
        Intent intent = getIntent();

        // Would be null if insert habit button is pressed
        Uri uri = intent.getParcelableExtra(HabitsProvider.CONTENT_ITEM_TYPE);

        if (uri == null){
            action = Intent.ACTION_INSERT;
            setTitle(getString(R.string.new_habit));
        } else {
            // For the case of editing a habit.
            action = Intent.ACTION_EDIT;
            setTitle(getString(R.string.edit_habit));

            habitFilter = DBOpenHelper.HABIT_ID +  "=" + uri.getLastPathSegment();

            Cursor cursor = getContentResolver().query(uri, DBOpenHelper.ALL_COLUMNS, habitFilter, null , null);


            cursor.moveToFirst();
            oldText = cursor.getString(cursor.getColumnIndex(DBOpenHelper.HABIT_NAME));
            editor.setText(oldText);
            editor.requestFocus();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                finishedEditing();
                break;
            case R.id.action_delete:
                deleteHabit();
                break;
        }
        return true;
    }

    private void finishedEditing(){
        String newText = editor.getText().toString().trim();

        switch(action){
            case Intent.ACTION_INSERT:
                if (newText.length() == 0 ){
                    setResult(RESULT_CANCELED);
                }
                else {
                    insertHabit(newText);
                }
                break;
            case Intent.ACTION_EDIT:
                if (newText.length() == 0 ){
                    deleteHabit();

                }else if (oldText.equals(newText)){
                    setResult(RESULT_CANCELED);
                }else {
                    updateHabit(newText);
                }
        }
        finish();

    }

    private void updateHabit(String newText) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.HABIT_NAME, newText);
        getContentResolver().update(HabitsProvider.CONTENT_URI, values, habitFilter, null);
        Toast.makeText(this,"Habit updated", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);

    }

    private void deleteHabit() {
        getContentResolver().delete(HabitsProvider.CONTENT_URI,habitFilter,null);
        Toast.makeText(this, R.string.habit_deleted, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    private void insertHabit(String newText) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.HABIT_NAME, newText);
        Uri habitURI = getContentResolver().insert(HabitsProvider.CONTENT_URI, values);
        setResult(RESULT_OK);
    }

    public void openAlarmview (View view) {
        Intent intent = new Intent(this ,EditorActivity.class);
        startActivityForResult(intent , ALARM_REQUEST_CODE);
    }

    @Override
    public void onBackPressed() {
        finishedEditing();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (action.equals(Intent.ACTION_EDIT)){
            getMenuInflater().inflate(R.menu.menu_editor, menu);
        }
        return true;
    }

}
