package com.weboniselab.meghana.android.app.profilemanager;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by webonise on 2/9/15.
 */
public class BroadcastReceiver extends android.content.BroadcastReceiver {
    Calendar calendar;
    SimpleDateFormat timeFormat;
    private String timeOfDevice,time;
    DatabaseOperations databaseOperations;
    List<DetailsOfPhone> detailsOfPhoneList;
    String toTime,fromTime,modeOfPhone;
    int id;
    AlarmManager alarmManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        databaseOperations=new DatabaseOperations(context);
        detailsOfPhoneList=databaseOperations.getAllDetails();
        for (int i=0;i<detailsOfPhoneList.size();i++) {
            DetailsOfPhone object1 = detailsOfPhoneList.get(i);
            fromTime = object1.getFromTime();
            toTime = object1.getToTime();
            modeOfPhone = object1.getModeOfPhone();
            Log.v("$$$$$$$$$$$$$$$", fromTime);
            Log.v("$$$$$$$$$$$$$$$", toTime);
            Log.v("$$$$$$$$$$$$$$$", modeOfPhone);
        }
        time=getCurrentTime();
        Log.d("",time);
        Log.d("",fromTime);
        if (fromTime==time){
            Log.v("*********************", "yes!!!!");
        }
    }
    public String getCurrentTime(){
        calendar= Calendar.getInstance();
        timeFormat=new SimpleDateFormat("HH:mm");
        timeOfDevice =timeFormat.format(calendar.getTime());
        Log.v("$$$$$$$$$$$$$$$$$$", timeOfDevice);
        return timeOfDevice;
    }
}
