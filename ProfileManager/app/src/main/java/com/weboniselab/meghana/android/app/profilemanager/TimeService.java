package com.weboniselab.meghana.android.app.profilemanager;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by webonise on 9/9/15.
 */
public class TimeService extends Service {
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
        TimeBroadcastReceiver broadcastReceiver = new TimeBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_TIME_TICK);
        registerReceiver(broadcastReceiver, intentFilter);
    }
}
