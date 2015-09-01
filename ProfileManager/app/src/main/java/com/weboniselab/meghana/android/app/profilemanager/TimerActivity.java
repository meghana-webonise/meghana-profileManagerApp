package com.weboniselab.meghana.android.app.profilemanager;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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
    private Adapter adapter;
    DatabaseOperations databaseOperations;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_activity);
        initialise();
       // getPhoneTime();

    }

    public void getPhoneTime(){
        calendar=Calendar.getInstance();
        timeFormat=new SimpleDateFormat(getResources().getString(R.string.timeFormat));
        time=timeFormat.format(calendar.getTime());
        Toast.makeText(TimerActivity.this, time, Toast.LENGTH_SHORT).show();
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
    public void initialise(){
        databaseOperations=new DatabaseOperations(this);
        listView=(ListView) findViewById(R.id.lvTimeSetByUser);
        adapter=new Adapter(TimerActivity.this,databaseOperations.getAllDetails());
        listView.setAdapter(adapter);
        ivAdd=(Button) findViewById(R.id.tvAdd);
        ivAdd.setOnClickListener(this);
    }
}
