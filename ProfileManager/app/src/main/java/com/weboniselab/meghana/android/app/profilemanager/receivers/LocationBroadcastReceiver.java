package com.weboniselab.meghana.android.app.profilemanager.receivers;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.weboniselab.meghana.android.app.profilemanager.utils.DatabaseOperations;
import com.weboniselab.meghana.android.app.profilemanager.utils.GeofenceUtils;
import com.weboniselab.meghana.android.app.profilemanager.R;
import com.weboniselab.meghana.android.app.profilemanager.activities.MainActivity;
import com.weboniselab.meghana.android.app.profilemanager.models.LocationModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by webonise on 5/10/15.
 */
public class LocationBroadcastReceiver extends BroadcastReceiver {

    private NotificationManager notificationManager;
    DatabaseOperations databaseOperations;
    String modeOfPhone;
    int id;
    List<Geofence> triggeringGeofences;
    ArrayList triggeringGeofencesIdsList = new ArrayList();
    enum ModeOfPhone{
        Silent,Vibration,Loud
    }
    private AudioManager audioManager;
    private Context mContext;

    public LocationBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        databaseOperations=new DatabaseOperations(context);
        audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

        mContext = context;
        Intent broadcastIntent = new Intent();
        broadcastIntent.addCategory(GeofenceUtils.CATEGORY_LOCATION_SERVICES);

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Log.d(getClass().getName(),"Error");
            return;
        }
        int geofenceTransition = geofencingEvent.getGeofenceTransition();
        if (geofenceTransition==Geofence.GEOFENCE_TRANSITION_ENTER){
            triggeringGeofences = geofencingEvent.getTriggeringGeofences();
            for (Geofence geofence : triggeringGeofences) {
                triggeringGeofencesIdsList.add(geofence.getRequestId());
                for (LocationModel model : databaseOperations.getAllDetailsFromLocationTable()) {
                    Log.d("$$$$$$$$$",geofence.getRequestId());
                    Log.d("***************", String.valueOf(model.getId()));
                    if ((geofence.getRequestId().toString()).equals(String.valueOf(model.getId()))) {
                        Log.i("^^^^^^^", ")))))");
                        modeOfPhone=model.getModeOfPhone();
                        Log.d("(((((((((((((((((((",modeOfPhone);
                        sendNotification(model.getId(),model.getAddress());
                        changePhoneMode(modeOfPhone);
                        Log.d(getClass().getName(), "&&&" + String.valueOf(model.getId()));
                    }
                }
            }
        }
        else if (geofenceTransition==Geofence.GEOFENCE_TRANSITION_EXIT){
            try{
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                notificationManager.cancel(id);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        /*for(Geofence geofence : triggeringGeofences){
            triggeringGeofencesIdsList.remove(geofence.getRequestId());
        }*/
    }

    private Context getContext(){
        return mContext;
    }

    public void changePhoneMode(String modeOfPhone){
        if (TextUtils.equals(modeOfPhone, ModeOfPhone.Loud.toString())){
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }
        else if (TextUtils.equals(modeOfPhone,ModeOfPhone.Vibration.toString()))
        {
            audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        }
        else if(TextUtils.equals(modeOfPhone,ModeOfPhone.Silent.toString()))
        {
            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void sendNotification(int id, String address){
        Log.d(getClass().getName(),"Sending notification");
        Log.d("&&&&&&&&&", String.valueOf(id));
        Log.d("**********",address);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext());
        builder.setContentTitle("Profile Manager")
                .setContentText("Entered Geofence " +address).setSmallIcon(R.drawable.marker)
                .setAutoCancel(true);
        Intent notificationIntent = new Intent(getContext(), MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getContext());
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        notificationManager.notify(id, builder.build());
    }
}