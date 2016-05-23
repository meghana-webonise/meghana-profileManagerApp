package com.weboniselab.meghana.android.app.profilemanager.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.weboniselab.meghana.android.app.profilemanager.services.BatteryService;
import com.weboniselab.meghana.android.app.profilemanager.utils.DatabaseOperations;
import com.weboniselab.meghana.android.app.profilemanager.R;

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
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        intent=getIntent();
        batteryLevel=intent.getStringExtra("battery Level");
        Toast.makeText(SetBatteryActivity.this, ""+batteryLevel, Toast.LENGTH_SHORT).show();
        initialise();
    }
    public void initialise(){
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
                        Toast.makeText(SetBatteryActivity.this, items[0], Toast.LENGTH_SHORT).show();
                        modeOfPhone = items[0];
                        break;
                    case 1:
                        Toast.makeText(SetBatteryActivity.this, items[1], Toast.LENGTH_SHORT).show();
                        modeOfPhone = items[1];
                        break;
                    case 2:
                        Toast.makeText(SetBatteryActivity.this, items[2], Toast.LENGTH_SHORT).show();
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
                       Toast.makeText(SetBatteryActivity.this, "" + networkMode[which], Toast.LENGTH_SHORT).show();
                       break;
                   case 1:
                       Toast.makeText(SetBatteryActivity.this, "" + networkMode[which], Toast.LENGTH_SHORT).show();
                       break;
                   case 2:
                       Toast.makeText(SetBatteryActivity.this, "" + networkMode[which], Toast.LENGTH_SHORT).show();
                       break;
               }
               if (isChecked) {
                   selectedItemIndexList.add(which);
               } else if (selectedItemIndexList.contains(which)) {
                   selectedItemIndexList.remove(Integer.valueOf(which));
               }
               Toast.makeText(SetBatteryActivity.this, "" + selectedItemIndexList, Toast.LENGTH_SHORT).show();
           }
       });
        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(SetBatteryActivity.this, "New" + selectedItemIndexList, Toast.LENGTH_SHORT).show();
                modeOfNetwork= TextUtils.join(getResources().getString(R.string.separator),selectedItemIndexList);
                Log.d(getClass().getName(),modeOfNetwork);
                alertDialog1.dismiss();
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
