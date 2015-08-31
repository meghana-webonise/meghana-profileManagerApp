package com.weboniselab.meghana.android.app.profilemanager;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by webonise on 28/8/15.
 */

/*
this activity contains a listview which has information of time set by user and an add button which moves to another activity where the timer is shown
to set time
 */
public class TimerActivity extends Activity implements View.OnClickListener{
    Button ivAdd;
    SimpleDateFormat timeFormat;
    private String time;
    Calendar calendar;
    Intent setTimerActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_activity);
        initialise();
       // getPhoneTime();

    }

    public void getPhoneTime(){
        calendar=Calendar.getInstance();
        timeFormat=new SimpleDateFormat("HH:mm:ss");
        time=timeFormat.format(calendar.getTime());
        Toast.makeText(TimerActivity.this, time, Toast.LENGTH_SHORT).show();
    }

    public void initialise(){
        ivAdd=(Button) findViewById(R.id.tvAdd);
        ivAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvAdd:
                setTimerActivity=new Intent(this,SetTimerActivity.class);
                this.startActivity(setTimerActivity);
                break;
        }

    }


}
