package com.weboniselab.meghana.android.app.profilemanager;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.List;

/**
 * Created by webonise on 21/9/15.
 */
public class LocationSearchActivity extends AppCompatActivity implements View.OnClickListener{
    private android.support.v7.widget.Toolbar toolbar;
    GoogleMap googleMap;
    MarkerOptions markerOptions;
    LatLng latLng;
    EditText etLocation;
    Button btnSearch;
    String location;
    SupportMapFragment supportMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_search_activity);
        initialise();
    }
    public void initialise(){
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        supportMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        googleMap = supportMapFragment.getMap();
        btnSearch=(Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);
        etLocation = (EditText) findViewById(R.id.etLocation);
    }

    @Override
    public void onClick(View v) {
        location = etLocation.getText().toString();
        if(location!=null && !location.equals("")){
            new GeocoderTask().execute(location);
        }
    }

    private class GeocoderTask extends AsyncTask<String, Void, List<Address>> {
        String addressText;
        protected List<Address> doInBackground(String... locationName) {
            Geocoder geocoder = new Geocoder(getBaseContext());
            List<Address> addresses = null;
            try {
                // Getting a maximum of 3 Address that matches the input text
                addresses = geocoder.getFromLocationName(locationName[0], 3);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return addresses;
        }
        protected void onPostExecute(List<Address> addresses) {
            if(addresses==null || addresses.size()==0){
                Toast.makeText(getBaseContext(), getResources().getString(R.string.NoLocationFound), Toast.LENGTH_SHORT).show();
            }
            googleMap.clear();
            // Adding Markers on Google Map for each matching address
            for(int i=0;i<addresses.size();i++){
                Address address = (Address) addresses.get(i);
                // Creating an instance of GeoPoint, to display in Google Map
                latLng = new LatLng(address.getLatitude(), address.getLongitude());
                addressText = String.format("%s, %s",
                        address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                        address.getCountryName());
                markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(addressText);
                googleMap.addMarker(markerOptions);
                Log.d(getClass().getName(),"$$$$$$$$$$" +address.getLatitude()+ " and " +address.getLongitude());
                // Locate the first location
                if(i==0)
                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        }

    }

}
