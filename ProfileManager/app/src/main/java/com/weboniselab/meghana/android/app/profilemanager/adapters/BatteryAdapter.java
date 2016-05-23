package com.weboniselab.meghana.android.app.profilemanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.weboniselab.meghana.android.app.profilemanager.models.BatteryModel;
import com.weboniselab.meghana.android.app.profilemanager.utils.DatabaseOperations;
import com.weboniselab.meghana.android.app.profilemanager.R;

import java.util.List;

/**
 * Created by webonise on 10/9/15.
 */
public class BatteryAdapter extends BaseAdapter {
    ListView listView;
    TextView tvBattery;
    private Context context;
    DatabaseOperations databaseOperations;
    private List<BatteryModel> items;

    public BatteryAdapter(Context context,List<BatteryModel> items){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_view_row_battery, null);
        }
       // listView=(ListView) convertView.findViewById(R.id.lvBatterySetByUser);
        tvBattery=(TextView) convertView.findViewById(R.id.tvBattery);
        tvBattery.setText(items.get(position).getModeOfNetwork());
        tvBattery.append(",");
        tvBattery.append(items.get(position).getModeOfPhoneBattery());
        return convertView;
    }
}
