package com.weboniselab.meghana.android.app.profilemanager;


import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

/**
 * Created by webonise on 28/8/15.
 */

/*
this activity contains a listview which has information of time set by user and an add button which moves to another activity where the timer is shown
to set time
 */
public class TimerActivity extends AppCompatActivity {
    Intent setTimerActivity;
    private Adapter adapter;
    DatabaseOperations databaseOperations;
    private ListView listView;
    private android.support.v7.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_activity);
        initialise();
        /*TimeBroadcastReceiver broadcastReceiver = new TimeBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_TIME_TICK);
        registerReceiver(broadcastReceiver, intentFilter);*/
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnAdd:
                setTimerActivity=new Intent(this,SetTimerActivity.class);
                this.startActivity(setTimerActivity);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void initialise(){
        databaseOperations=new DatabaseOperations(this);
        listView=(ListView) findViewById(R.id.lvTimeSetByUser);
    }
    @Override
    protected void onResume() {
        super.onResume();
        adapter=new Adapter(TimerActivity.this,databaseOperations.getAllDetails());
        listView.setAdapter(adapter);
    }
}
