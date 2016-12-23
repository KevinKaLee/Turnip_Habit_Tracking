package com.example.android.turnip_habit_tracking_app;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.w3c.dom.Text;


public class HabitInfo extends AppCompatActivity {
    private TextView habitTitle , habitDesc;
    private String habitFilter, oldText, oldHabitDesc;
    private Button finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_info);

        Bundle bundle = getIntent().getExtras();
        String habitID = bundle.getString("ID");

        init();

        setText(habitID);

    }

    /**
     * This method takes in the unique habit identifier and extracts the habits name and description
     * and sets them to the textViews on the screen
     * @param habitID This is the unique identifier for the habit
     */

    private void setText(String habitID) {
        habitFilter = DBOpenHelper.HABIT_ID +  "=" + habitID;

        Cursor cursor = getContentResolver().query(HabitsProvider.CONTENT_URI, DBOpenHelper.ALL_COLUMNS, habitFilter, null , null);

        cursor.moveToFirst();
        oldText = cursor.getString(cursor.getColumnIndex(DBOpenHelper.HABIT_NAME));
        oldHabitDesc = cursor.getString(cursor.getColumnIndex(DBOpenHelper.HABIT_DESC));
        habitTitle.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.list_item_text));
        habitDesc.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.list_item_text));
        habitTitle.setText(oldText);
        habitDesc.setText(oldHabitDesc);
    }

    /**
     * This method initializes the screen elements.
     */
    private void init() {
        habitTitle =  (TextView) findViewById(R.id.habitTitle);
        habitDesc =  (TextView) findViewById(R.id.habitDesc);
        finishButton = (Button) findViewById((R.id.doneButton));

        finishButton.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }

        );
    }


}
