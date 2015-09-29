package com.weboniselab.meghana.android.app.profilemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by webonise on 29/9/15.
 */
public class LocationAdapter extends BaseAdapter {

    ListView listView;
    TextView tvModeOfPhone,tvAddress;
    private Context context;
    DatabaseOperations databaseOperations;
    private List<LocationModel> items;

    public LocationAdapter(Context context,List<LocationModel> items){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_view_row_location, null);
        }
        listView=(ListView) convertView.findViewById(R.id.lvLocationSetByUser);
        tvAddress=(TextView) convertView.findViewById(R.id.tvAddress);
        tvAddress.setText(items.get(position).getAddress());
        tvModeOfPhone=(TextView) convertView.findViewById(R.id.tvModeOfPhone);
        tvModeOfPhone.setText(items.get(position).getModeOfPhone());
        return convertView;
    }
}
