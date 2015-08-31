package com.weboniselab.meghana.android.app.profilemanager;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by webonise on 28/8/15.
 */

/*
this activity begins a fragment which contains the timer to set time
 */
public class SetTimerActivity extends Activity implements View.OnClickListener{
    TimePicker fromTimePicker,toTimePicker;
    int hour,minute,fromTime,fromMinute;
    Calendar calendar;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_timer_activity);
        initialise();


    }
    public void initialise(){
        fromTimePicker=(TimePicker) findViewById(R.id.fromTimePicker);
        toTimePicker=(TimePicker) findViewById(R.id.toTimePicker);
        calendar=Calendar.getInstance();
        hour=calendar.get(Calendar.HOUR_OF_DAY);
        minute=calendar.get(Calendar.MINUTE);
        btnAdd=(Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        getTime();
    }

    public void getTime(){
        fromTimePicker.clearFocus();
        fromTime=fromTimePicker.getCurrentHour();
        fromMinute=fromTimePicker.getCurrentMinute();
        String h=String.valueOf(fromTime);
        Log.v("---------------",h);
    }

}
