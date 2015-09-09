package com.weboniselab.meghana.android.app.profilemanager;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognition;

/**
 * Created by webonise on 9/9/15.
 */
public class MovementRecognitionService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    protected GoogleApiClient googleApiClient;
    Intent movementRecognitionIntentService;
    private PendingIntent pendingIntent;
    private GoogleApiClient activityRecognitionClient;
    private int DETECTION_INTERVAL_MILLISECONDS = 10;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(ActivityRecognition.API)
                .build();
    }

   /* private GoogleApiClient getActivityRecognitionClient() {
        if (activityRecognitionClient == null) {

            activityRecognitionClient = new GoogleApiClient.Builder(this)
                    .addApi(ActivityRecognition.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
        return activityRecognitionClient;
    }
*/
    @Override
    public void onCreate() {
        buildGoogleApiClient();
        googleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (googleApiClient.isConnected()) {
            ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(
                    googleApiClient,
                    DETECTION_INTERVAL_MILLISECONDS,
                    getActivityDetectionPendingIntent()
            ).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(Status status) {
                    Log.d(getClass().getName(), "####################" + status);
                }
            });
        Toast.makeText(MovementRecognitionService.this, getResources().getString(R.string.connectedToApi), Toast.LENGTH_SHORT).show();}
    }

    private PendingIntent getActivityDetectionPendingIntent() {
        if (pendingIntent != null) {
            return pendingIntent;
        }
        Intent intent = new Intent(this, MovementRecognitionIntentService.class);
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(MovementRecognitionService.this, getResources().getString(R.string.connectionSuspended), Toast.LENGTH_SHORT).show();
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(MovementRecognitionService.this, getResources().getString(R.string.connectionFailed), Toast.LENGTH_SHORT).show();

    }


}

