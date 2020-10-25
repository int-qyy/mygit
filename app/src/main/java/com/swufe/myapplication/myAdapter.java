package com.swufe.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class myAdapter extends ArrayAdapter {
    private static final String TAG = "MyAdapter";
    public myAdapter(Context context,
                     int resource,
                     List<RateItem> list){
        super(context, resource,list);
    }

    public View getView(int position, View convertView, ViewGroup parent){

        View itemView = convertView;
        if(itemView == null){
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.editlist, parent, false);
        }
        Map<String, String> map = (Map<String, String>) getItem(position);
        TextView title = (TextView)itemView.findViewById(R.id.itemTitle);
        TextView detail = (TextView)itemView.findViewById(R.id.itemDetail);

        title.setText(map.get("ItemTitle"));
        detail.setText(map.get("ItemDetail"));

        return itemView;
    }
}
