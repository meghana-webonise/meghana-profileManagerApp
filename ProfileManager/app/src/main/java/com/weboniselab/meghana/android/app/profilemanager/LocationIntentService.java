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
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Log.d(getClass().getName(),"Error");
            return;
        }
        int geofenceTransition = geofencingEvent.getGeofenceTransition();



        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {

            // Get the geofences that were triggered. A single event can trigger multiple geofences.
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

            // Get the transition details as a String.
            String geofenceTransitionDetails = getGeofenceTransitionDetails(
                    this,
                    geofenceTransition,
                    triggeringGeofences
            );

            // Send notification and log the transition details.
            sendNotification(geofenceTransitionDetails);
            Log.i(getClass().getName(), geofenceTransitionDetails);
        } else {
            // Log the error.
            Log.e(getClass().getName(), "Geofence transition error");
        }
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        databaseOperations=new DatabaseOperations(this);
        locationModelList=databaseOperations.getAllDetailsFromLocationTable();
        for (int i=0;i<locationModelList.size();i++) {
            LocationModel locationModel = locationModelList.get(i);
            modeOfPhone=locationModel.getModeOfPhone();
        }
        changePhoneMode();
    }

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
        else
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    }



    private String getGeofenceTransitionDetails(
            Context context,
            int geofenceTransition,
            List<Geofence> triggeringGeofences) {

        String geofenceTransitionString = getTransitionString(geofenceTransition);

        // Get the Ids of each geofence that was triggered.
        ArrayList triggeringGeofencesIdsList = new ArrayList();
        for (Geofence geofence : triggeringGeofences) {
            triggeringGeofencesIdsList.add(geofence.getRequestId());
        }
        String triggeringGeofencesIdsString = TextUtils.join(", ", triggeringGeofencesIdsList);

        return geofenceTransitionString + ": " + triggeringGeofencesIdsString;
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void sendNotification(String notificationDetails) {
        // Create an explicit content Intent that starts the main Activity.
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);

        // Construct a task stack.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Add the main Activity to the task stack as the parent.
        stackBuilder.addParentStack(MainActivity.class);

        // Push the content Intent onto the stack.
        stackBuilder.addNextIntent(notificationIntent);

        // Get a PendingIntent containing the entire back stack.
        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get a notification builder that's compatible with platform versions >= 4
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        // Define the notification settings.
        builder.setSmallIcon(R.drawable.marker)
                // In a real app, you may want to use a library like Volley
                // to decode the Bitmap.
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.drawable.marker))
                .setColor(Color.RED)
                .setContentTitle(notificationDetails)
                .setContentText("Click to return to app")
                .setContentIntent(notificationPendingIntent);

        // Dismiss notification once the user touches it.
        builder.setAutoCancel(true);

        // Get an instance of the Notification manager
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Issue the notification
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
