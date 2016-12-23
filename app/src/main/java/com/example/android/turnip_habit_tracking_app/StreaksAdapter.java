package com.example.android.turnip_habit_tracking_app;

/**
 * Created by emma on 22/12/16.
 *
 * Custom adapter to populate list items for the PointsActivity view from the database.
 *
 */

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StreaksAdapter extends CursorAdapter {

    public StreaksAdapter(StreaksActivity context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.data_item,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        String habitText = cursor.getString(cursor.getColumnIndex(DBOpenHelper.HABIT_NAME));
        int Data = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.HABIT_STREAK));
        int pos = habitText.indexOf(10);
        if (pos != -1 ){
            habitText = habitText.substring(0,pos) + "...";
        }
        TextView tv = (TextView) view.findViewById(R.id.HabitName);
        tv.setText(habitText);
        TextView tv1 = (TextView) view.findViewById(R.id.DataValue);
        tv1.setText(Integer.toString(Data));

    }

}
