package com.example.android.turnip_habit_tracking_app;

/**
 * Created by emma on 22/12/16.
 *
 * Custom adapter to populate list items for the PointsActivity view from the database.
 *
 *  exposes data from a Cursor to a ListView widget.
 */


import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DataAdapter extends CursorAdapter {

    public DataAdapter(AppCompatActivity context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        if (context instanceof MainHabitsActivity){
            return LayoutInflater.from(context).inflate(R.layout.node_list_item,parent,false);
        }
        else {
            return LayoutInflater.from(context).inflate(R.layout.data_item, parent, false);
        }
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        setTexttoHabitName(view, cursor);

        if(context instanceof PointsActivity ){
            setDataToPoints(view, cursor);
        }
        else if(context instanceof StreaksActivity){
            setDataToStreak(view, cursor);
        }

    }

    /**
     *  Stores the habit's name in habitText and checks if there is a line feed in text.
     *  Replaces with ... if there is.
     *  Then sets the habits name into the textView of the listView node
     * @param view
     * @param cursor
     */

    public void setTexttoHabitName(View view, Cursor cursor) {
        String habitName = cursor.getString(cursor.getColumnIndex(DBOpenHelper.HABIT_NAME));
        int pos = habitName.indexOf(10);
        if (pos != -1 ){
            habitName = habitName.substring(0,pos) + "...";
        }
        TextView tv = (TextView) view.findViewById(R.id.habitName);
        tv.setText(habitName);
    }

    public void setDataToPoints(View view, Cursor cursor) {
        int habitPoints = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.HABIT_POINTS));
        TextView tv1 = (TextView) view.findViewById(R.id.DataValue);
        tv1.setText(Integer.toString(habitPoints));
    }

    public void setDataToStreak(View view, Cursor cursor) {
        int habitStreak = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.HABIT_STREAK));
        TextView tv1 = (TextView) view.findViewById(R.id.DataValue);
        tv1.setText(Integer.toString(habitStreak));
    }

}
