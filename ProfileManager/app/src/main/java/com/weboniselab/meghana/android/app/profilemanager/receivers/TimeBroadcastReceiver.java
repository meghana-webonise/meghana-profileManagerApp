package com.weboniselab.meghana.android.app.profilemanager.receivers;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.text.TextUtils;

import com.weboniselab.meghana.android.app.profilemanager.utils.Constants;
import com.weboniselab.meghana.android.app.profilemanager.utils.DatabaseOperations;
import com.weboniselab.meghana.android.app.profilemanager.utils.DetailsOfPhone;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by webonise on 2/9/15.
 */
public class TimeBroadcastReceiver extends android.content.BroadcastReceiver {
    Calendar calendar;
    SimpleDateFormat timeFormat,fromTimeFormat;
    private String timeOfDevice,time;
    DatabaseOperations databaseOperations;
    List<DetailsOfPhone> detailsOfPhoneList;
    String toTime,fromTime,modeOfPhone,formattedFromTime,formattedToTime;
    enum ModeOfPhone{
        Silent,Vibration,Loud
    }
    private AudioManager audioManager;
    @Override
    public void onReceive(Context context, Intent intent) {
        audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        databaseOperations=new DatabaseOperations(context);
        detailsOfPhoneList=databaseOperations.getAllDetails();
        for (int i=0;i<detailsOfPhoneList.size();i++) {
            DetailsOfPhone detailsOfPhone = detailsOfPhoneList.get(i);
            fromTime = detailsOfPhone.getFromTime();
            formattedFromTime=convertToTime(fromTime);
            toTime = detailsOfPhone.getToTime();
            formattedToTime=convertToTime(toTime);
            modeOfPhone = detailsOfPhone.getModeOfPhone();
        }
        time=getCurrentTime();
        changePhoneMode();
    }
    public String convertToTime(String time){
        String formattedTime = null;
        fromTimeFormat=new SimpleDateFormat(Constants.Format_Of_Time);
        try {
            Date date=fromTimeFormat.parse(time);
            formattedTime=fromTimeFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedTime;
    }
    public String getCurrentTime(){
        calendar= Calendar.getInstance();
        timeFormat=new SimpleDateFormat(Constants.Format_Of_Time);
        timeOfDevice =timeFormat.format(calendar.getTime());
        return timeOfDevice;
    }
    public void changePhoneMode(){
        if (TextUtils.equals(formattedFromTime,time)){
            if (TextUtils.equals(modeOfPhone,ModeOfPhone.Silent.toString()))
            {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            }
            else if (TextUtils.equals(modeOfPhone,ModeOfPhone.Vibration.toString()))
            {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            }
            else if (TextUtils.equals(modeOfPhone,ModeOfPhone.Loud.toString())){
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            }
        }
        if (TextUtils.equals(formattedToTime, time)) {
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }

    }
}
