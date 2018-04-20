package com.yacob.weekend.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yacob.weekend.R;

import java.util.ArrayList;

/**
 * Created by Dhahri on 19/01/2018.
 */

public class Provider_item_adapter extends ArrayAdapter<String> {
    private ArrayList<String> dataSet;
    Context mContext;
    static class ViewHolder_provider {
        TextView txt;
        ImageView images;
        ImageView supp;
    }
    public Provider_item_adapter(ArrayList<String> data, Context context) {
        super(context, R.layout.custom_item_provider, data);
        this.dataSet = data;
        this.mContext=context;

    }
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final String dataModel = dataSet.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final Provider_item_adapter.ViewHolder_provider viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new Provider_item_adapter.ViewHolder_provider();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.custom_item_provider, parent, false);
            viewHolder.txt = (TextView) convertView.findViewById(R.id.provider_text);
            viewHolder.images = (ImageView) convertView.findViewById(R.id.provider_logo);
            viewHolder.supp =  convertView.findViewById(R.id.provider_supp);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Provider_item_adapter.ViewHolder_provider) convertView.getTag();
            result=convertView;
        }

         int lastPosition = position;
        viewHolder.txt.setText(dataModel);


        // Return the completed view to render on screen
        return convertView;
    }

}
