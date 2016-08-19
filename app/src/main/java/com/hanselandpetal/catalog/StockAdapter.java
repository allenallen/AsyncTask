package com.hanselandpetal.catalog;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class StockAdapter extends ArrayAdapter<StockModel>{

    private Context context;
    private List<StockModel> list;

    public StockAdapter(Context context, int resource, List<StockModel> objects) {
        super(context, resource, objects);

        this.context = context;
        this.list = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.list_layout,parent,false);

        StockModel model = list.get(position);
        TextView tv = (TextView) view.findViewById(R.id.textView1);
        tv.setText(model.getName());

        return view;
    }
}
