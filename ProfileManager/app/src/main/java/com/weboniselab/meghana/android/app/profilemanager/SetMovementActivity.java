package com.weboniselab.meghana.android.app.profilemanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;

/**
 * Created by webonise on 3/9/15.
 */
public class SetMovementActivity extends Activity implements View.OnClickListener {
    Button btnWalk, btnDrive, btnDone;
    String[] items;
    AlertDialog alertDialog;
    String modeOfPhone, modeOfMovement;
    DatabaseOperations databaseOperations;
    Intent movementRecognitionService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_movement_activity);
        initialise();
    }

    public void initialise() {
        btnWalk = (Button) findViewById(R.id.btnWalk);
        btnWalk.setOnClickListener(this);
        btnDrive = (Button) findViewById(R.id.btnDrive);
        btnDrive.setOnClickListener(this);
        btnDone = (Button) findViewById(R.id.btnDone);
        btnDone.setOnClickListener(this);
        items = getResources().getStringArray(R.array.popUp);
        databaseOperations = new DatabaseOperations(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnWalk:
                modeOfMovement = getResources().getString(R.string.walk);
                showPopUp();
                break;
            case R.id.btnDrive:
                modeOfMovement = getResources().getString(R.string.drive);
                showPopUp();
                break;
            case R.id.btnDone:
                movementRecognitionService = new Intent(this, MovementRecognitionService.class);
                startService(movementRecognitionService);
                finish();
                break;
        }
    }

    //AlertDialog to select Phone Mode
    public void showPopUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.popTitle));
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        Toast.makeText(SetMovementActivity.this, items[0], Toast.LENGTH_SHORT).show();
                        modeOfPhone = items[0];
                        databaseOperations.insertOrUpdateToDatabaseMovementTable(modeOfMovement, modeOfPhone);
                        break;
                    case 1:
                        Toast.makeText(SetMovementActivity.this, items[1], Toast.LENGTH_SHORT).show();
                        modeOfPhone = items[1];
                        databaseOperations.insertOrUpdateToDatabaseMovementTable(modeOfMovement, modeOfPhone);

                        break;
                    case 2:
                        Toast.makeText(SetMovementActivity.this, items[2], Toast.LENGTH_SHORT).show();
                        modeOfPhone = items[2];
                        databaseOperations.insertOrUpdateToDatabaseMovementTable(modeOfMovement, modeOfPhone);
                        break;
                }
                alertDialog.dismiss();
            }
        });

        alertDialog = builder.create();
        alertDialog.show();
    }


}