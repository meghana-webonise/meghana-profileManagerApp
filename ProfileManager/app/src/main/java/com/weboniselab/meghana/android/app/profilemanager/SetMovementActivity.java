package com.weboniselab.meghana.android.app.profilemanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by webonise on 3/9/15.
 */
public class SetMovementActivity extends Activity implements View.OnClickListener{
    Button btnIdle,btnWalk,btnDrive;
    String[] items;
    AlertDialog alertDialog;
    String modeOfPhone,modeOfMovement;
    DatabaseOperations databaseOperations;
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
        databaseOperations=new DatabaseOperations(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnIdle:
                modeOfMovement=getResources().getString(R.string.idle);
                showPopUp();
                break;
            case R.id.btnWalk:
                modeOfMovement=getResources().getString(R.string.walk);
                showPopUp();
                break;
            case R.id.btnDrive:
                modeOfMovement=getResources().getString(R.string.drive);
                showPopUp();
                break;
        }

    }


    //AlertDialog to select Phone Mode
    public void showPopUp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.popTitle));
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        Toast.makeText(SetMovementActivity.this, items[0], Toast.LENGTH_SHORT).show();
                        modeOfPhone=items[0];
                        databaseOperations.addDetailsToDatabaseMovementTable(modeOfMovement,modeOfPhone);
                        break;
                    case 1:
                        Toast.makeText(SetMovementActivity.this, items[1], Toast.LENGTH_SHORT).show();
                        modeOfPhone=items[1];
                        databaseOperations.addDetailsToDatabaseMovementTable(modeOfMovement,modeOfPhone);

                        break;
                    case 2:
                        Toast.makeText(SetMovementActivity.this, items[2], Toast.LENGTH_SHORT).show();
                        modeOfPhone=items[2];
                        databaseOperations.addDetailsToDatabaseMovementTable(modeOfMovement,modeOfPhone);
                        break;
                }
                alertDialog.dismiss();
            }
        });

        alertDialog = builder.create();
        alertDialog.show();
    }
}