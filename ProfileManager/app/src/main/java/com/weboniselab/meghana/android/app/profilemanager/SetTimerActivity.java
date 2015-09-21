package com.weboniselab.meghana.android.app.profilemanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;

/**
 * Created by webonise on 28/8/15.
 */

/*
Activity to select From and To Time
 */
public class SetTimerActivity extends AppCompatActivity implements View.OnClickListener
{
    TimePickerFragment timePickerFragment;
    Button btnFromTime,btnToTime,btnDone,btnPhoneMode;
     String[] items;
    AlertDialog alertDialog;
    String modeOfPhone,fromTime,toTime;
    DatabaseOperations databaseOperations;
    private android.support.v7.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_timer_activity);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        initialise();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnFromTime:
                showFromDialog(view);
                break;
            case R.id.btnToTime:
                showToDialog(view);
                break;
            case R.id.btnPhoneMode:
                modeOfPhone=showPopUp();
                break;
            case R.id.btnDone:
                if (validateInputData()) {
                    databaseOperations.addDetailsToDatabase(fromTime, toTime, modeOfPhone);
                    Intent intent = new Intent(this, TimeService.class);
                    startService(intent);
                    finish();
                }
                break;
        }
    }

    public boolean validateInputData(){
        if (TextUtils.isEmpty(btnFromTime.getText().toString())){
            Toast.makeText(SetTimerActivity.this, getResources().getString(R.string.btnFromTime), Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (TextUtils.isEmpty(btnToTime.getText().toString())){
            Toast.makeText(SetTimerActivity.this, getResources().getString(R.string.btnToTime), Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (TextUtils.isEmpty(btnPhoneMode.getText().toString())){
            Toast.makeText(SetTimerActivity.this, getResources().getString(R.string.btnPhoneMode), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //DialogFragment to set From Time
    public void showFromDialog(View view) {
        timePickerFragment.setFlag(TimePickerFragment.FLAG_FROM_TIME);
        timePickerFragment.show(this.getFragmentManager(), getResources().getString(R.string.fromTimePickerTag));
    }
    //DialogFragment to set To Time
    public void showToDialog(View view){
        timePickerFragment.setFlag(TimePickerFragment.FLAG_TO_TIME);
        timePickerFragment.show(this.getFragmentManager(), getResources().getString(R.string.toTimePickerTag));
    }
    //AlertDialog to select Phone Mode
    public String showPopUp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.popTitle));
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        Toast.makeText(SetTimerActivity.this, items[0], Toast.LENGTH_SHORT).show();
                        modeOfPhone=items[0];
                        btnPhoneMode.setText(modeOfPhone);
                        break;
                    case 1:
                        Toast.makeText(SetTimerActivity.this, items[1], Toast.LENGTH_SHORT).show();
                        modeOfPhone=items[1];
                        btnPhoneMode.setText(modeOfPhone);
                        break;
                    case 2:
                        Toast.makeText(SetTimerActivity.this, items[2], Toast.LENGTH_SHORT).show();
                        modeOfPhone=items[2];
                        btnPhoneMode.setText(modeOfPhone);
                        break;
                }

                alertDialog.dismiss();
            }
        });

        alertDialog = builder.create();
        alertDialog.show();
        return modeOfPhone;
    }
    public class TimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {
        public static final int FLAG_FROM_TIME = 0;
        public static final int FLAG_TO_TIME = 1;
        private int flag = 0;
        public TimePickerFragment() {
        }
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }
        public void setFlag(int i){
            flag=i;
        }
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if (flag == FLAG_FROM_TIME) {
                btnFromTime.setText(getResources().getString(R.string.selectedFromTime)+" " + hourOfDay + getResources().getString(R.string.timeSeparator) + minute);
                fromTime=convertionOfTimeToString(hourOfDay, minute);
            } else if (flag == FLAG_TO_TIME) {
                btnToTime.setText(getResources().getString(R.string.selectedToTime)+" " + hourOfDay + getResources().getString(R.string.timeSeparator) + minute);
                toTime=convertionOfTimeToString(hourOfDay, minute);
            }
        }
        public String convertionOfTimeToString(int hour,int minute){
            StringBuilder stringBuilder=new StringBuilder();
            String strHour=String.valueOf(hour);
            String strMinute=String.valueOf(minute);
            stringBuilder.append(strHour);
            stringBuilder.append(":");
            stringBuilder.append(strMinute);
            return stringBuilder.toString();
        }
    }
    public void initialise(){
        databaseOperations=new DatabaseOperations(this);
        timePickerFragment=new TimePickerFragment();
        btnFromTime = (Button) findViewById(R.id.btnFromTime);
        btnFromTime.setOnClickListener(this);
        btnToTime = (Button) findViewById(R.id.btnToTime);
        btnToTime.setOnClickListener(this);
        btnPhoneMode=(Button) findViewById(R.id.btnPhoneMode);
        btnPhoneMode.setOnClickListener(this);
        btnDone = (Button) findViewById(R.id.btnDone);
        btnDone.setOnClickListener(this);
        items= getResources().getStringArray(R.array.popUp);
    }
}
