package com.example.android.turnip_habit_tracking_app;

/**
 * Created by emma on 22/12/16.
 */

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PointsAdapter extends CursorAdapter {

    public PointsAdapter(PointsActivity context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.points_item,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        String habitText = cursor.getString(cursor.getColumnIndex(DBOpenHelper.HABIT_NAME));
        int habitPoints = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.HABIT_POINTS));
        //int habitPoints = 5;
        // Finds ascii character of new line
        int pos = habitText.indexOf(10);
        if (pos != -1 ){
            habitText = habitText.substring(0,pos) + "...";
        }

        TextView tv = (TextView) view.findViewById(R.id.HabitName);
        tv.setText(habitText);
        TextView tv1 = (TextView) view.findViewById(R.id.PointsValue);
        tv1.setText(Integer.toString(habitPoints));
    }

    /*public PointsAdapter(PointsActivity context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.points_item,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        String habitText = cursor.getString(cursor.getColumnIndex(DBOpenHelper.HABIT_NAME));
        // Finds ascii character of new line
        int pos = habitText.indexOf(10);
        if (pos != -1 ){
            habitText = habitText.substring(0,pos) + "...";
        }

        TextView tv = (TextView) view.findViewById(R.id.HabitName);
        tv.setText(habitText);
    }*/


    /*@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater myinflater = LayoutInflater.from(getContext());
        View customView = myinflater.inflate(R.layout.points_item, parent, false);

        String habitname = getItem(position);
        TextView habitText = (TextView)customView.findViewById(R.id.HabitName);

        habitText.setText(habitname);
        return customView;
    }*/
}
