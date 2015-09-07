package com.weboniselab.meghana.android.app.profilemanager;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;
import java.util.List;

/**
 * Created by webonise on 7/9/15.
 */
public class MovementRecognitionIntentService extends IntentService implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    DatabaseOperations databaseOperations;
    List<MovementModel> movementModelList;
    private AudioManager audioManager;
    protected GoogleApiClient googleApiClient;
    int start;
    String modeOfMovement,modeOfPhone;
    public MovementRecognitionIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        buildGoogleApiClient();
        if (ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result =
                    ActivityRecognitionResult.extractResult(intent);
            DetectedActivity mostProbableActivity
                    = result.getMostProbableActivity();
            int confidence = mostProbableActivity.getConfidence();
            int activityType = mostProbableActivity.getType();
            String activity=getActivityType(activityType, confidence);

            audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
            databaseOperations=new DatabaseOperations(this);
            movementModelList=databaseOperations.getAllDetailsFromMovementTable();
            for (int i=0;i<movementModelList.size();i++) {
                MovementModel movementModel = movementModelList.get(i);
                modeOfMovement=movementModel.getModeOfMovement();
                modeOfPhone=movementModel.getModeOfPhone();
            }
        }
    }

    public String getActivityType(int activityType,int confidence){
        String activity=null,Walk=null,Drive=null;
        if (activityType==DetectedActivity.ON_FOOT && confidence>0){
            activity=Walk;
        }
        else if (activityType==DetectedActivity.IN_VEHICLE && confidence>0){
            activity=Drive;
        }
        return activity;
    }
    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(ActivityRecognition.API)
                .build();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        googleApiClient.connect();
        return start;
    }
    @Override
    public void onDestroy() {
        googleApiClient.disconnect();
    }
    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(MovementRecognitionIntentService.this, "Connected to API", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(MovementRecognitionIntentService.this, "Connection Suspended ", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(MovementRecognitionIntentService.this, "Connection to API failed", Toast.LENGTH_SHORT).show();
    }
}
