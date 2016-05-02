package com.pineapple.euguardei;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Vinicius on 18/04/16.
 */
public class CustomList extends ArrayAdapter<String> {
    private String[] descs;
    private String[] dates;
    private String[] ids;
    private Activity context;

    public CustomList(Activity context, String[] descs, String[] dates, String[] ids) {
        super(context,R.layout.list_item, descs);
        this.context = context;
        this.descs = descs;
        this.dates = dates;
        this.ids = ids;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_item, null, true);
        TextView desc = (TextView) listViewItem.findViewById(R.id.desc);
        TextView date = (TextView) listViewItem.findViewById(R.id.date);
        TextView id = (TextView) listViewItem.findViewById(R.id.id_item);

        desc.setText(descs[position]);
        date.setText(dates[position]);
        id.setText(ids[position]);

        return listViewItem;
    }
}