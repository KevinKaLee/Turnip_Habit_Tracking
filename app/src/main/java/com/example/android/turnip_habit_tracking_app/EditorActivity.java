package com.example.android.turnip_habit_tracking_app;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
            // For the case of editiing a habit.
            action = Intent.ACTION_EDIT;
            habitFilter = DBOpenHelper.HABIT_ID +  "=" + uri.getLastPathSegment();

            Cursor cursor = getContentResolver().query(uri, DBOpenHelper.ALL_COLUMNS, habitFilter, null , null);
            cursor.moveToFirst();
            oldText = cursor.getString(cursor.getColumnIndex(DBOpenHelper.HABIT_NAME));
            editor.setText(oldText);
            editor.requestFocus();
        }
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

}
