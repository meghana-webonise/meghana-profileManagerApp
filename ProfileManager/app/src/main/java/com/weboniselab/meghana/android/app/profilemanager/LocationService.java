package com.weboniselab.meghana.android.app.profilemanager;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

/**
 * Created by webonise on 29/9/15.
 */
public class LocationService extends Service implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status>{

    protected GoogleApiClient mGoogleApiClient;
    protected ArrayList<Geofence> geofenceList;
    private boolean geofencesAdded;
    private PendingIntent geofencePendingIntent;
    DatabaseOperations databaseOperations;

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
        databaseOperations=new DatabaseOperations(getApplicationContext());
        geofenceList = new ArrayList<Geofence>();
        geofencePendingIntent = null;
        populateGeofenceList();
        buildGoogleApiClient();

        if (!mGoogleApiClient.isConnected()) {
            Toast.makeText(this, " not Connected", Toast.LENGTH_SHORT).show();
            mGoogleApiClient.connect();
        }
    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
    public void populateGeofenceList(){
        for (LocationModel entry : databaseOperations.getAllDetailsFromLocationTable()){
            geofenceList.add(new Geofence.Builder()
                    .setRequestId(String.valueOf(entry.getId()))
                    .setCircularRegion(
                            entry.getLatitude(),
                            entry.getLongitude(),
                            entry.getRadius()
                    )

                    .setExpirationDuration(Constants.GEOFENCE_EXPIRATION_IN_MILLISECONDS)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                            Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build());
            Log.d(getClass().getName(), String.valueOf(entry.getId()));
        }
    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(geofenceList);
        return builder.build();
    }
    private void logSecurityException(SecurityException securityException) {
        Log.e(getClass().getName(), "Invalid location permission. " +
                "You need to use ACCESS_FINE_LOCATION with geofences", securityException);
    }
    private PendingIntent getGeofencePendingIntent() {
        if (geofencePendingIntent != null) {
            return geofencePendingIntent;
        }
        Intent intent = new Intent(this, LocationIntentService.class);
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
    @Override
    public void onConnected(Bundle bundle) {
        if (mGoogleApiClient.isConnected()){
            Toast.makeText(LocationService.this, "Connected to Api", Toast.LENGTH_SHORT).show();
            addGeofence();
        }
        Log.d(getClass().getName(),"Connected to API");
    }
    public void addGeofence(){
        try {
            LocationServices.GeofencingApi.addGeofences(
                    mGoogleApiClient,
                    // The GeofenceRequest object.
                    getGeofencingRequest(),
                    // A pending intent that that is reused when calling removeGeofences(). This
                    // pending intent is used to generate an intent when a matched geofence
                    // transition is observed.
                    getGeofencePendingIntent()
            ).setResultCallback(this); // Result processed in onResult().
        } catch (SecurityException securityException) {
            // Catch exception generated if the app does not use ACCESS_FINE_LOCATION permission.
            logSecurityException(securityException);
        }
    }
    @Override
    public void onConnectionSuspended(int i) {
        Log.d(getClass().getName(),"Connection suspended");
        mGoogleApiClient.connect();
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(getClass().getName(),"Connection Failed");
        if (mGoogleApiClient.isConnected()){
            mGoogleApiClient.connect();
        }
    }
    @Override
    public void onResult(Status status) {
        if (status.isSuccess()) {
            geofencesAdded = !geofencesAdded;
        } else {
           Log.d(getClass().getName(),"Geofence not added");
        }
    }
}
