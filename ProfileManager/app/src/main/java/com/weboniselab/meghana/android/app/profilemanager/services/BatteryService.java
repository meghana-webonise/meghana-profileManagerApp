package com.weboniselab.meghana.android.app.profilemanager.services;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.weboniselab.meghana.android.app.profilemanager.receivers.BatteryBroadcastReceiver;

/**
 * Created by webonise on 11/9/15.
 */
public class BatteryService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        BatteryBroadcastReceiver broadcastReceiver = new BatteryBroadcastReceiver();
        IntentFilter batteryChanged = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(broadcastReceiver, batteryChanged);
        IntentFilter flightMode=new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(broadcastReceiver, flightMode);
    }
}
