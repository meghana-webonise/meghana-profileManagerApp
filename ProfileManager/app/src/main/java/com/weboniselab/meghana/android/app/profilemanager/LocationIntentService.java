package com.weboniselab.meghana.android.app.profilemanager;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by webonise on 5/10/15.
 */
public class LocationIntentService extends IntentService {
    private AudioManager audioManager;
    String modeOfPhone;
    enum ModeOfPhone{
        Silent,Vibration,Loud
    }
    DatabaseOperations databaseOperations;
    List<LocationModel> locationModelList;
    public LocationIntentService() {
        super("");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(getClass().getName(), "%%%%%%%%%%%%%%%%%%%%");
        return super.onStartCommand(intent,flags,startId);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        databaseOperations=new DatabaseOperations(this);
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Log.d(getClass().getName(),"Error");
            return;
        }
        int geofenceTransition = geofencingEvent.getGeofenceTransition();
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
            String geofenceTransitionDetails = getGeofenceTransitionDetails(
                    this,
                    geofenceTransition,
                    triggeringGeofences
            );
            sendNotification(geofenceTransitionDetails);
            Log.i(getClass().getName(), geofenceTransitionDetails);
            Log.d(getClass().getName(), String.valueOf(triggeringGeofences));

           /* for (Geofence geofence : triggeringGeofences) {
                if (geofence.getRequestId().equals(model.getId())) {
                    changePhoneMode();
                }
            }
        }else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT){
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }

        else {
            Log.e(getClass().getName(), "Geofence transition error");
        }*/
        /*audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        locationModelList=databaseOperations.getAllDetailsFromLocationTable();
        for (int i=0;i<locationModelList.size();i++) {
            LocationModel locationModel = locationModelList.get(i);
            modeOfPhone=locationModel.getModeOfPhone();
        }*/
    }}
    public void changePhoneMode(){
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
    private String getGeofenceTransitionDetails(
            Context context,
            int geofenceTransition,
            List<Geofence> triggeringGeofences) {
        String geofenceTransitionString = getTransitionString(geofenceTransition);
        ArrayList triggeringGeofencesIdsList = new ArrayList();
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        locationModelList=databaseOperations.getAllDetailsFromLocationTable();
        for (int i=0;i<locationModelList.size();i++) {
            LocationModel locationModel = locationModelList.get(i);
            modeOfPhone=locationModel.getModeOfPhone();
        }
        for (Geofence geofence : triggeringGeofences) {
            triggeringGeofencesIdsList.add(geofence.getRequestId());
            for (LocationModel model : databaseOperations.getAllDetailsFromLocationTable()) {
                Log.d("$$$$$$$$$",geofence.getRequestId());
                Log.d("***************", String.valueOf(model.getId()));

                    if ((geofence.getRequestId().toString()).equals(String.valueOf(model.getId()))) {
                        Log.i("^^^^^^^", ")))))");
                        changePhoneMode();
                        Log.d(getClass().getName(), "&&&" + String.valueOf(model.getId()));
                    }

                else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT){
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                }
                else {
                    Log.e(getClass().getName(), "Geofence transition error");
                }
            }
        }
        String triggeringGeofencesIdsString = TextUtils.join(", ", triggeringGeofencesIdsList);

        return geofenceTransitionString + ": " + triggeringGeofencesIdsString;
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void sendNotification(String notificationDetails) {
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.marker)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.drawable.marker))
                .setContentTitle("Profile Manager")
                .setContentText("Click to return to app")
                .setContentIntent(notificationPendingIntent);
        builder.setAutoCancel(true);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, builder.build());
    }
    private String getTransitionString(int transitionType) {
        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return "Transtition entered";
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return "Transition exited";
            default:
                return "Unknown geofence";
        }
    }
}
