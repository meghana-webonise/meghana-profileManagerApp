package com.weboniselab.meghana.android.app.profilemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvMovementOfUser,tvTimer,tvBatteryOfPhone,tvLocationOfUser;
    Intent timerActivityIntent,movementActivityIntent;
    private android.support.v7.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        initialise();
    }
    public void initialise(){
        tvLocationOfUser=(TextView) findViewById(R.id.tvLocationOfUser);
        tvLocationOfUser.setOnClickListener(this);
        tvMovementOfUser=(TextView) findViewById(R.id.tvMovementOfUser);
        tvMovementOfUser.setOnClickListener(this);
        tvTimer=(TextView) findViewById(R.id.tvTimer);
        tvTimer.setOnClickListener(this);
        tvBatteryOfPhone=(TextView) findViewById(R.id.tvBatteryOfPhone);
        tvBatteryOfPhone.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvTimer:
                timerActivityIntent=new Intent(this,TimerActivity.class);
                this.startActivity(timerActivityIntent);
                break;
            case R.id.tvMovementOfUser:
                movementActivityIntent=new Intent(this,MovementActivity.class);
                this.startActivity(movementActivityIntent);
                break;
        }

    }
}
