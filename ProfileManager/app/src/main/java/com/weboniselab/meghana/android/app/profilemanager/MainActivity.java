package com.weboniselab.meghana.android.app.profilemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {
    TextView tvMovementOfUser,tvTimer,tvBatteryOfPhone,tvLocationOfUser;
    Intent timerActivityIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        }

    }
}
