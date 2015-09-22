package com.weboniselab.meghana.android.app.profilemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

/**
 * Created by webonise on 22/9/15.
 */
public class LocationActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;
    Intent locationSearchActivity;
    ListView listView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_activity);
        initialise();
    }

    public void initialise(){
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        listView=(ListView) findViewById(R.id.lvTimeSetByUser);
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

}
