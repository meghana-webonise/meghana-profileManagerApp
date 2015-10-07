package com.weboniselab.meghana.android.app.profilemanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * Created by webonise on 10/9/15.
 */
public class SetBatteryActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnphoneMode,btnNetworkConnectivity,btnDone;
    AlertDialog alertDialog,alertDialog1;
    String modeOfPhone,modeOfNetwork, batteryLevel;
    String[] items;
    String [] networkMode;
    boolean [] isSelectedArray={false,false,false};
    private ArrayList<Integer> selectedItemIndexList=new ArrayList<Integer>();
    Intent intent;
    private android.support.v7.widget.Toolbar toolbar;
    DatabaseOperations databaseOperations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_battery_activity);
        intent=getIntent();
        batteryLevel=intent.getStringExtra("battery Level");
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
        btnphoneMode=(Button) findViewById(R.id.btnPhoneMode);
        btnphoneMode.setOnClickListener(this);
        btnNetworkConnectivity=(Button) findViewById(R.id.btnNetworkConnectivity);
        btnNetworkConnectivity.setOnClickListener(this);
        btnDone=(Button) findViewById(R.id.btnDone);
        btnDone.setOnClickListener(this);
        items= getResources().getStringArray(R.array.popUp);
        networkMode=getResources().getStringArray(R.array.networkConnectivityPopUp);
        databaseOperations=new DatabaseOperations(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnPhoneMode:
                showPopUp();
                break;
            case R.id.btnNetworkConnectivity:
                showNetworkConnectivityPopUp();
                break;
            case R.id.btnDone:
                if (modeOfPhone==null){
                    modeOfPhone=String.valueOf(-1);
                }
                if (modeOfNetwork==null){
                    modeOfNetwork= String.valueOf(-1);
                }
                Log.d(getClass().getName(),modeOfPhone);
                Log.d(getClass().getName(),modeOfNetwork);
                databaseOperations.insertOrUpdateToDatabaseBatteryTable(batteryLevel, modeOfPhone, modeOfNetwork);
                Intent intent=new Intent(this,BatteryService.class);
                startService(intent);
                finish();
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
                        modeOfPhone = items[0];
                        break;
                    case 1:
                        modeOfPhone = items[1];
                        break;
                    case 2:
                        modeOfPhone = items[2];
                        break;
                }
                alertDialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }
    public void showNetworkConnectivityPopUp(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.NetworkConnectivity));
       builder.setMultiChoiceItems(networkMode, isSelectedArray, new DialogInterface.OnMultiChoiceClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which, boolean isChecked) {
               switch (which) {
                   case 0:
                       break;
                   case 1:
                       break;
                   case 2:
                       break;
               }
               if (isChecked) {
                   selectedItemIndexList.add(which);
               } else if (selectedItemIndexList.contains(which)) {
                   selectedItemIndexList.remove(Integer.valueOf(which));
               }
           }
       });
        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                modeOfNetwork= TextUtils.join(getResources().getString(R.string.separator),selectedItemIndexList);
                Log.d(getClass().getName(), modeOfNetwork);
                alertDialog1.dismiss();
                btnNetworkConnectivity.setText(modeOfNetwork);
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                isSelectedArray[0] = false;
                isSelectedArray[1] = false;
                isSelectedArray[2] = false;
                selectedItemIndexList.clear();
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();
    }


}
