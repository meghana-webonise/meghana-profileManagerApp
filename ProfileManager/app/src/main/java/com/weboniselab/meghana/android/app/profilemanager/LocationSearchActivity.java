package com.weboniselab.meghana.android.app.profilemanager;

import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by webonise on 21/9/15.
 */
public class LocationSearchActivity extends AppCompatActivity implements LocationListener{
    private android.support.v7.widget.Toolbar toolbar;
    GoogleMap googleMap;
    Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_search_activity);
        initialise();
    }


    public void initialise() {
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar);
        ImageView iv=(ImageView) findViewById(R.id.iv);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        try {
            initialiseMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void initialiseMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
            googleMap.setMyLocationEnabled(true);
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, true);
            Location location = locationManager.getLastKnownLocation(provider);
            if(location!=null){
                onLocationChanged(location);
            }
            locationManager.requestLocationUpdates(provider, 20000, 0, this);
        }
        addMarker();
        if (googleMap == null) {
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.SorryUnableToCreateMap), Toast.LENGTH_SHORT)
                    .show();
        }
    }
//add marker to center of screen
    public void addMarker(){
        googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            public void onCameraChange(CameraPosition cameraPosition) {
                LatLng latLng = cameraPosition.target;
                MarkerOptions options = new MarkerOptions()
                        .position(latLng);
                if (marker != null) {
                    marker.remove();
                }
                marker = googleMap.addMarker(options);
                marker.setVisible(false);
                Log.d("latLng.latitude",String.valueOf(latLng.latitude));
                Log.d("latLng.longitude", String.valueOf(latLng.longitude));
            }
        });
    }
    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }
    @Override
    public void onProviderDisabled(String provider) {

    }
    @Override
    public void onProviderEnabled(String provider) {

    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
    @Override
    protected void onResume() {
        super.onResume();
        googleMap.clear();
        initialiseMap();
    }
}
