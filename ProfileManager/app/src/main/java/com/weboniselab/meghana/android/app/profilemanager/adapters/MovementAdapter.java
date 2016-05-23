package com.weboniselab.meghana.android.app.profilemanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.weboniselab.meghana.android.app.profilemanager.utils.DatabaseOperations;
import com.weboniselab.meghana.android.app.profilemanager.models.MovementModel;
import com.weboniselab.meghana.android.app.profilemanager.R;

import java.util.List;

/**
 * Created by webonise on 3/9/15.
 */
public class MovementAdapter extends BaseAdapter {
    ListView listView;
    TextView tvModeOfMovement,tvModeOfPhone;
    private Context context;
    DatabaseOperations databaseOperations;
    private List<MovementModel> items;

    public MovementAdapter(Context context,List<MovementModel> items){
        this.items=items;
        this.context=context;
        databaseOperations=new DatabaseOperations(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_view_row_movement, null);
        }
        listView=(ListView) convertView.findViewById(R.id.lvMovementSetByUser);
        tvModeOfMovement=(TextView) convertView.findViewById(R.id.tvModeOfMovement);
        tvModeOfMovement.setText(items.get(position).getModeOfMovement());
        tvModeOfPhone=(TextView) convertView.findViewById(R.id.tvModeOfPhone);
        tvModeOfPhone.setText(items.get(position).getModeOfPhone());
        return convertView;
    }

}
