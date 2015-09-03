package com.weboniselab.meghana.android.app.profilemanager;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by webonise on 28/8/15.
 */

/*
this activity contains a listview which has information of time set by user and an add button which moves to another activity where the timer is shown
to set time
 */
public class TimerActivity extends Activity implements View.OnClickListener{
    Button btnAdd;
    Intent setTimerActivity;
    private Adapter adapter;
    DatabaseOperations databaseOperations;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_activity);
        initialise();
        TimeBroadcastReceiver broadcastReceiver = new TimeBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_TIME_TICK);
        registerReceiver(broadcastReceiver, intentFilter);
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
        btnAdd =(Button) findViewById(R.id.tvAdd);
        btnAdd.setOnClickListener(this);
    }
}
