package com.weboniselab.meghana.android.app.profilemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by webonise on 10/9/15.
 */
public class BatteryActivity extends AppCompatActivity implements View.OnClickListener{
    Button tvLowBattery,tvVeryLowBattery,tvNormalBattery;
    private android.support.v7.widget.Toolbar toolbar;
    Intent setBatteryActivity;
    DatabaseOperations databaseOperations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.battery_activity);
        initialise();
    }
    public void initialise(){
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvVeryLowBattery=(Button) findViewById(R.id.tvVeryLowBattery);
        tvVeryLowBattery.setOnClickListener(this);
        tvLowBattery=(Button) findViewById(R.id.tvLowBattery);
        tvLowBattery.setOnClickListener(this);
        tvNormalBattery=(Button) findViewById(R.id.tvNormalBattery);
        tvNormalBattery.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvVeryLowBattery:
                setBatteryActivity=new Intent(this,SetBatteryActivity.class);
                setBatteryActivity.putExtra("battery Level","1");
                startActivity(setBatteryActivity);
                break;
            case R.id.tvLowBattery:
                setBatteryActivity=new Intent(this,SetBatteryActivity.class);
                setBatteryActivity.putExtra("battery Level","2");
                startActivity(setBatteryActivity);
                break;
            case R.id.tvNormalBattery:
                setBatteryActivity=new Intent(this,SetBatteryActivity.class);
                setBatteryActivity.putExtra("battery Level","3");
                startActivity(setBatteryActivity);
                break;
        }

    }
}
