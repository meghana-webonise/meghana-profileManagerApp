package com.weboniselab.meghana.android.app.profilemanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.weboniselab.meghana.android.app.profilemanager.utils.DatabaseOperations;
import com.weboniselab.meghana.android.app.profilemanager.adapters.LocationAdapter;
import com.weboniselab.meghana.android.app.profilemanager.R;

/**
 * Created by webonise on 22/9/15.
 */
public class LocationActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;
    Intent locationSearchActivity;
    private ListView listView;
    private LocationAdapter adapter;
    DatabaseOperations databaseOperations;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_activity);
        initialise();
    }

    public void initialise(){
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        listView=(ListView) findViewById(R.id.lvLocationSetByUser);
        databaseOperations=new DatabaseOperations(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnAdd:
                locationSearchActivity=new Intent(this,LocationSearchActivity.class);
                this.startActivity(locationSearchActivity);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        adapter=new LocationAdapter(LocationActivity.this,databaseOperations.getAllDetailsFromLocationTable());
        listView.setAdapter(adapter);
    }

}