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
 * Created by webonise on 1/9/15.
 */
public class Adapter extends BaseAdapter {
    ListView listView;
    TextView tvFromTime,tvToTime,tvModeOfPhone;
    private Context context;
    DatabaseOperations databaseOperations;
    private List<DetailsOfPhone> items;

    public Adapter(Context context,List<DetailsOfPhone> items){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_view_row, null);
        }
        listView=(ListView) convertView.findViewById(R.id.lvTimeSetByUser);
        tvFromTime=(TextView) convertView.findViewById(R.id.tvFromTime);
        tvFromTime.setText(items.get(position).getFromTime());
        tvToTime=(TextView) convertView.findViewById(R.id.tvToTime);
        tvToTime.setText(items.get(position).getToTime());
        tvModeOfPhone=(TextView) convertView.findViewById(R.id.tvModeOfPhone);
        tvModeOfPhone.setText(items.get(position).getModeOfPhone());
        return convertView;
    }

}
