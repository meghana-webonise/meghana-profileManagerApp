package com.weboniselab.meghana.android.app.profilemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by webonise on 10/9/15.
 */
public class BatteryActivity extends AppCompatActivity implements View.OnClickListener{
    TextView tvLowBattery,tvVeryLowBattery,tvNormalBattery;
    private android.support.v7.widget.Toolbar toolbar;
    Intent setBatteryActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.battery_activity);
        initialise();
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
    }

    public void initialise(){
        tvVeryLowBattery=(TextView) findViewById(R.id.tvVeryLowBattery);
        tvVeryLowBattery.setOnClickListener(this);
        tvLowBattery=(TextView) findViewById(R.id.tvLowBattery);
        tvLowBattery.setOnClickListener(this);
        tvNormalBattery=(TextView) findViewById(R.id.tvNormalBattery);
        tvNormalBattery.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvVeryLowBattery:
                setBatteryActivity=new Intent(this,SetBatteryActivity.class);
                startActivity(setBatteryActivity);
                break;
            case R.id.tvLowBattery:
                setBatteryActivity=new Intent(this,SetBatteryActivity.class);
                startActivity(setBatteryActivity);
                break;
            case R.id.tvNormalBattery:
                setBatteryActivity=new Intent(this,SetBatteryActivity.class);
                startActivity(setBatteryActivity);
                break;
        }

    }
}
