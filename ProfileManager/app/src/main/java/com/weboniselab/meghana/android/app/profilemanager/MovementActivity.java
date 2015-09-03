package com.weboniselab.meghana.android.app.profilemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by webonise on 3/9/15.
 */
public class MovementActivity extends Activity implements View.OnClickListener{
    private Button btnAdd;
    private ListView listView;
    Intent setMovementActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movement_activity);
        initialise();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAdd:
                setMovementActivity=new Intent(this,SetMovementActivity.class);
                this.startActivity(setMovementActivity);
                break;
        }

    }

    public void initialise(){
        listView=(ListView) findViewById(R.id.lvMovementSetByUser);
        btnAdd=(Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

    }
}
