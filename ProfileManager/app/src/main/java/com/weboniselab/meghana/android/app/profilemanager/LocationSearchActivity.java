package com.weboniselab.meghana.android.app.profilemanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.List;

/**
 * Created by webonise on 21/9/15.
 */
public class LocationSearchActivity extends AppCompatActivity implements LocationListener{
    private android.support.v7.widget.Toolbar toolbar;
    GoogleMap googleMap;
    Marker marker;
    double latitude,longitude;
    Button btnModeOfPhone;
    String modeOfPhone;
    String[] items;
    AlertDialog alertDialog;
    DatabaseOperations databaseOperations;
    String address;
    Intent locationService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_search_activity);
        initialise();
    }
    public void initialise() {
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        locationService=new Intent(this,LocationService.class);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseOperations.addDetailsToLocationTable(latitude, longitude, Constants.Radius_Of_Geofence, address, modeOfPhone);
                startService(locationService);
                onBackPressed();
            }
        });
        try {
            initialiseMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
        items= getResources().getStringArray(R.array.popUp);
        btnModeOfPhone=(Button) findViewById(R.id.btnModeOfPhone);
        btnModeOfPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modeOfPhone = showPopUp();
            }
        });
        databaseOperations=new DatabaseOperations(this);
    }
    //AlertDialog to select Phone Mode
    public String showPopUp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.popTitle));
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        Toast.makeText(LocationSearchActivity.this, items[0], Toast.LENGTH_SHORT).show();
                        modeOfPhone = items[0];
                        btnModeOfPhone.setText(modeOfPhone);
                        break;
                    case 1:
                        Toast.makeText(LocationSearchActivity.this, items[1], Toast.LENGTH_SHORT).show();
                        modeOfPhone = items[1];
                        btnModeOfPhone.setText(modeOfPhone);
                        break;
                    case 2:
                        Toast.makeText(LocationSearchActivity.this, items[2], Toast.LENGTH_SHORT).show();
                        modeOfPhone = items[2];
                        btnModeOfPhone.setText(modeOfPhone);
                        break;
                }
                alertDialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
        return modeOfPhone;
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
           // locationManager.requestLocationUpdates(provider, 20000, 0, this);
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
                latitude = latLng.latitude;
                longitude = latLng.longitude;
                /*Log.d("latLng.latitude", String.valueOf(latitude));
                Log.d("latLng.longitude", String.valueOf(longitude));*/
                StringBuffer addressOfSelectedPlace=getAddressOfSelectedPlace(latitude, longitude);
                address=addressOfSelectedPlace.toString();
                Log.d("Address of place", String.valueOf(addressOfSelectedPlace));
            }
        });

    }
    //method to get address from the selected coordinates
    public StringBuffer getAddressOfSelectedPlace(double latitude,double longitude){
        StringBuffer fullAddress = null;
        Geocoder  geocoder=new Geocoder(getApplicationContext());
        if (geocoder.isPresent()) {
            try {
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                if(addresses != null && addresses.size()>0) {
                    Address returnedAddress = addresses.get(0);
                    StringBuffer stringBuffer = new StringBuffer("Address : ");
                    for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) {
                        stringBuffer.append(returnedAddress.getAddressLine(i)).append("\n");
                    }
                    Toast.makeText(LocationSearchActivity.this, stringBuffer, Toast.LENGTH_SHORT).show();
                    fullAddress=stringBuffer;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }
        return fullAddress;
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
