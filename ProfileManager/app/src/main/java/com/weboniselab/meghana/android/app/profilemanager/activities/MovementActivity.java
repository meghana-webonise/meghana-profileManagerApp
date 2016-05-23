package com.weboniselab.meghana.android.app.profilemanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.weboniselab.meghana.android.app.profilemanager.utils.DatabaseOperations;
import com.weboniselab.meghana.android.app.profilemanager.adapters.MovementAdapter;
import com.weboniselab.meghana.android.app.profilemanager.R;

/**
 * Created by webonise on 3/9/15.
 */
public class MovementActivity extends AppCompatActivity {
    private ListView listView;
    Intent setMovementActivity;
    DatabaseOperations databaseOperations;
    private MovementAdapter movementAdapter;
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movement_activity);
        initialise();

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnAdd:
                setMovementActivity = new Intent(this, SetMovementActivity.class);
                this.startActivity(setMovementActivity);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initialise() {
        listView = (ListView) findViewById(R.id.lvMovementSetByUser);
        databaseOperations = new DatabaseOperations(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        movementAdapter = new MovementAdapter(MovementActivity.this, databaseOperations.getAllDetailsFromMovementTable());
        listView.setAdapter(movementAdapter);
    }
}
