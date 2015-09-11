package com.weboniselab.meghana.android.app.profilemanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by webonise on 11/9/15.
 */
public class BatteryBroadcastReceiver extends BroadcastReceiver {
    private AudioManager audioManager;
    DatabaseOperations databaseOperations;
    List<BatteryModel> batteryList;
    String batteryLevel,modeOfPhone,modeOfNetwork;
    int level= -1;
    static boolean isAirplaneEnabled;
    WifiManager wifi;
    enum ModeOfPhone{
        Silent,Vibration,Loud
    }
    @Override
    public void onReceive(Context context, Intent intent) {

        isAirplaneEnabled=Settings.System.getInt(context.getContentResolver(),
                Settings.Global.AIRPLANE_MODE_ON, 0) != 0;

        audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        databaseOperations=new DatabaseOperations(context);
        batteryList=databaseOperations.getAllDetailsFromBatteryTable();
        for (int i=0;i<batteryList.size();i++){
            BatteryModel batteryModel=batteryList.get(i);
            batteryLevel=batteryModel.getBatteryLevel();
            modeOfPhone=batteryModel.getModeOfPhoneBattery();
            modeOfNetwork=batteryModel.getModeOfNetwork();
        }
        getCurrentBatteryLevel(intent);
        changePhoneMode(context);
    }
    public void getCurrentBatteryLevel(Intent intent){
        int currentLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        if (currentLevel >= 0 && scale > 0) {
            level = (currentLevel * 100) / scale;
        }
    }
    public void changePhoneMode(Context context){
        if (level<15 && TextUtils.equals(batteryLevel,"1")) {
            if (TextUtils.equals(modeOfPhone, ModeOfPhone.Silent.toString())) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

                try {
                    split(context, modeOfNetwork);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            } else if (TextUtils.equals(modeOfPhone, ModeOfPhone.Vibration.toString())) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

                try {
                    split(context, modeOfNetwork);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            } else if (TextUtils.equals(modeOfPhone, ModeOfPhone.Loud.toString())) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

                try {
                    split(context, modeOfNetwork);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }

           /* try {
                split(context, modeOfNetwork);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }*/

        }
        if ((level >15 && level<30) && TextUtils.equals(batteryLevel,"2")) {
            if (TextUtils.equals(modeOfPhone, ModeOfPhone.Silent.toString())) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

                try {
                    split(context, modeOfNetwork);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            } else if (TextUtils.equals(modeOfPhone, ModeOfPhone.Vibration.toString())) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

                try {
                    split(context, modeOfNetwork);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            } else if (TextUtils.equals(modeOfPhone, ModeOfPhone.Loud.toString())) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);


                try {
                    split(context, modeOfNetwork);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
            /*try {
                split(context, modeOfNetwork);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }*/
        }
        if (level>30 && TextUtils.equals(batteryLevel,"3")) {
            if (TextUtils.equals(modeOfPhone, ModeOfPhone.Silent.toString())) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

                try {
                    split(context, modeOfNetwork);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            } else if (TextUtils.equals(modeOfPhone, ModeOfPhone.Vibration.toString())) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

                try {
                    split(context, modeOfNetwork);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            } else if (TextUtils.equals(modeOfPhone, ModeOfPhone.Loud.toString())) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

                try {
                    split(context, modeOfNetwork);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
           /* try {
                split(context, modeOfNetwork);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }*/
        }
    }
    public void getModeOfNetwork(Context context,String modeOfNetwork) throws ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        if (TextUtils.equals(modeOfNetwork,"2")){
            enableFlightMode(context);
        }
        if (TextUtils.equals(modeOfNetwork,"0")){
            disableWifi(context);
        }

        if (TextUtils.equals(modeOfNetwork,"1")){
            disableMobileData(context,false);
        }
        if (TextUtils.equals(modeOfNetwork,"0") && TextUtils.equals(modeOfNetwork,"1")){
            disableWifi(context);
            disableMobileData(context,false);
        }
    }
    public void split(Context context,String network) throws ClassNotFoundException,
            NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        String [] parts=network.split(",");
        for (String i:parts) {
             modeOfNetwork=i;
            getModeOfNetwork(context,modeOfNetwork);
        }
    }
    private void disableMobileData(Context context,boolean enable) throws ClassNotFoundException,
            NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final Class connectivityManagerClass = Class.forName(connectivityManager.getClass().getName());
        final Field iConnectivityManagerField = connectivityManagerClass.getDeclaredField("mService");
        iConnectivityManagerField.setAccessible(true);
        final Object iConnectivityManager = iConnectivityManagerField.get(connectivityManager);
        final Class iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
        final Method setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
        setMobileDataEnabledMethod.setAccessible(true);
        setMobileDataEnabledMethod.invoke(iConnectivityManager, enable);
    }
    public void disableWifi(Context context){
        wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifi.isWifiEnabled()) {
            wifi.setWifiEnabled(false);
        }
    }
    public void enableFlightMode(Context context){
        Settings.System.putInt(context.getContentResolver(),
                Settings.Global.AIRPLANE_MODE_ON, isAirplaneEnabled ? 0 : 1);
        /*if (isAirplaneEnabled){
        }*/
    }
}
