package com.weboniselab.meghana.android.app.profilemanager;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationServices;
import java.util.ArrayList;

/**
 * Created by webonise on 29/9/15.
 */
public class LocationService extends Service implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status>{

    protected GoogleApiClient googleApiClient;
    protected ArrayList<Geofence> geofenceList;
    private boolean geofencesAdded;
    private PendingIntent geofencePendingIntent;
    DatabaseOperations databaseOperations=new DatabaseOperations(getApplicationContext());
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
        geofenceList = new ArrayList<Geofence>();
        geofencePendingIntent = null;
        buildGoogleApiClient();
    }


    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onResult(Status status) {

    }
}
