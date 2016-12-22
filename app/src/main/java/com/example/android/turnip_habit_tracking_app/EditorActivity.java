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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

<<<<<<< HEAD
import static java.lang.Double.parseDouble;
=======
import static android.R.attr.id;
>>>>>>> 868c07b0cd8bdb70975378bcf46913cf84d78e9d

public class EditorActivity extends AppCompatActivity {

    private String action;
    private EditText editor, desc_editor;
    private static final int ALARM_REQUEST_CODE = 1002;
    private String habitFilter;
    private String oldText, oldHabitDesc;
<<<<<<< HEAD
    private Button createAlarm;
    private double habitID;
=======
    private String habit_id;
    private  Uri uri;
>>>>>>> 868c07b0cd8bdb70975378bcf46913cf84d78e9d

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        editor = (EditText) findViewById(R.id.editText);
        desc_editor = (EditText) findViewById(R.id.editText1);
        createAlarm = (Button) findViewById(R.id.button);
        //habitID = Double.parseDouble(DBOpenHelper.HABIT_ID);

        Toast.makeText(getApplicationContext(),/*String.valueOf(habitID)*/"Hello",Toast.LENGTH_SHORT).show();

        createAlarm.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(EditorActivity.this, Alarm.class);
                        Bundle bundle = new Bundle();
                        bundle.putDouble("ID",habitID);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
        }
        );

        Intent intent = getIntent();

        // Would be null if insert habit button is pressed
        uri = intent.getParcelableExtra(HabitsProvider.CONTENT_ITEM_TYPE);

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
            oldHabitDesc = cursor.getString(cursor.getColumnIndex(DBOpenHelper.HABIT_DESC));
            habit_id = uri.getLastPathSegment();
            desc_editor.setText(oldHabitDesc);
            editor.setText(oldText);
            editor.requestFocus();
        }

    }

    /**
     * If item selected is to go home then call finishedEditing method
     * or if bin icon is selected, delete the habit
     * @param item
     * @return
     */
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

    /**
     * Captures the text entered by user into the text boxes
     * if the intent is to insert habit, check if text has been inserted into the
     * habit title , if not set the result to cancelled , otherwise call the insertHabit
     * method. If the intent was an edit check if the edited habit title has been deleted
     * then delete the habit otherwise if nothing has changed , set result to cancelled
     * otherwise update the new habit using updateHabit()
     */
    private void finishedEditing(){
        String newText = editor.getText().toString().trim();
        String habitDesc = desc_editor.getText().toString().trim();
        switch(action){
            case Intent.ACTION_INSERT:
                if (newText.length() == 0 ){
                    setResult(RESULT_CANCELED);
                }
                else {
                    insertHabit(newText, habitDesc);
                }
                break;
            case Intent.ACTION_EDIT:
                if (newText.length() == 0 ){
                    deleteHabit();

                }else if (oldText.equals(newText) && oldHabitDesc.equals(habitDesc)){
                    setResult(RESULT_CANCELED);
                }else {
                    updateHabit(newText, habitDesc);
                }
        }
        finish();

    }

    /**
     * Places the edited text into the Content resolver which then interacts with the
     * Content provider class which the updates the edited habits in the database
     *
     * @param newText This is the text in the habit title text box
     * @param habitDesc This is the text in the habit description text box
     */
    private void updateHabit(String newText, String habitDesc) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.HABIT_NAME, newText);
        values.put(DBOpenHelper.HABIT_DESC, habitDesc);
        getContentResolver().update(HabitsProvider.CONTENT_URI, values, habitFilter, null);
        Toast.makeText(this,"Habit updated", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);

    }

    /**
     * Interacts with the Content Resolver which in turn tells the Content Provider to
     * delete the selected habit
     */
    private void deleteHabit() {
        getContentResolver().delete(HabitsProvider.CONTENT_URI,habitFilter,null);
        Toast.makeText(this, R.string.habit_deleted, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    /**
     * Places the  text into the Content resolver which then interacts with the
     * Content provider class which then inserts a new habit into the database
     * The habit id pf the last inserted row is then stored.
     * @param newText This is the text from the habit title text box
     * @param habitDesc This is the text from the habit description text box
     */
    private void insertHabit(String newText, String habitDesc) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.HABIT_NAME, newText);
        values.put(DBOpenHelper.HABIT_DESC, habitDesc);
        Uri habitURI = getContentResolver().insert(HabitsProvider.CONTENT_URI, values);
        habit_id = habitURI.getLastPathSegment();
        setResult(RESULT_OK);
    }

    /**
     *  When user presses the Create Alarm button it finishes the editing and stores
     *  the habit_id into the intent to be passed onto the Alarm activity
     * @param view
     */
    public void openAlarmview (View view) {

        finishedEditing();
        Intent intent = new Intent(this, Alarm.class );
        intent.putExtra("habit_id", habit_id);
        startActivity(intent );
    }

    /**
     * Method gets the menu layout for the menu bar
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (action.equals(Intent.ACTION_EDIT)){
            getMenuInflater().inflate(R.menu.menu_editor, menu);
        }
        return true;
    }

}
