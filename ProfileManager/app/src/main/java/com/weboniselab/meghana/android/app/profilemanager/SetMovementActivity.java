package com.weboniselab.meghana.android.app.profilemanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by webonise on 3/9/15.
 */
public class SetMovementActivity extends Activity implements View.OnClickListener{
    Button btnIdle,btnWalk,btnDrive;
    String[] items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_movement_activity);
        initialise();
    }


    public void initialise(){
        btnIdle=(Button) findViewById(R.id.btnIdle);
        btnIdle.setOnClickListener(this);
        btnWalk=(Button) findViewById(R.id.btnWalk);
        btnWalk.setOnClickListener(this);
        btnDrive=(Button) findViewById(R.id.btnDrive);
        btnDrive.setOnClickListener(this);
        items=getResources().getStringArray(R.array.popUp);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnIdle:
                break;
        }

    }
}
