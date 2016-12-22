package com.example.android.turnip_habit_tracking_app;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Adapter that exposes data from a Cursor to a ListView widget.
 */
public class HabitsCursorAdapter extends CursorAdapter {

    public HabitsCursorAdapter(MainActivity context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
       return LayoutInflater.from(context).inflate(R.layout.node_list_item,parent,false);
    }

    /**
     *  Stores the habit's name in habitText and checks if there is a line feed in text.
     *  Replaces with ... if there is.
     *  Then sets the habits name into the textView of the listView node
     * @param view
     * @param context
     * @param cursor
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        String habitText = cursor.getString(cursor.getColumnIndex(DBOpenHelper.HABIT_NAME));
        // Finds ascii character of new line
        int pos = habitText.indexOf(10);
        if (pos != -1 ){
            habitText = habitText.substring(0,pos) + "...";
        }

        TextView tv = (TextView) view.findViewById(R.id.tvNote);
        tv.setText(habitText);
    }
}
