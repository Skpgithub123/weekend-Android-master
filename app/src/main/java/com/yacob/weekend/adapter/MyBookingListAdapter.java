package com.yacob.weekend.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.yacob.weekend.R;
import com.yacob.weekend.structure.MyBookingData;

import java.util.ArrayList;


public class MyBookingListAdapter extends BaseAdapter {

    ArrayList<MyBookingData> list_data = new ArrayList<>();
    Context context;
    LayoutInflater inflater;

    public MyBookingListAdapter(ArrayList<MyBookingData> list_data, Context context) {
        this.list_data = list_data;
        //data.addAll(list_data);
        this.context = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public int getCount() {
        return list_data.size();
    }

    @Override
    public Object getItem(int position) {
        return list_data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    class ViewHodler
    {
        TextView lblHouseName,lblFromDate,lblToDate,lblPrice;
        CircularImageView itemsImgFriends;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      ViewHodler viewHodler;
        if(convertView == null)
        {
            viewHodler = new ViewHodler();
            convertView = inflater.inflate(R.layout.items_my_booking,null);
           /* Typeface font = Typeface.createFromAsset(context.getAssets(),
                    "RobotoCondensedRegular.ttf");*/
            viewHodler.lblHouseName = (TextView)convertView.findViewById(R.id.lblHouseName);
            viewHodler.lblFromDate = (TextView)convertView.findViewById(R.id.lblFromDate);
            viewHodler.lblToDate = (TextView)convertView.findViewById(R.id.lblToDate);
            viewHodler.lblPrice = (TextView)convertView.findViewById(R.id.lblPrice);
            viewHodler.itemsImgFriends = (CircularImageView) convertView.findViewById(R.id.itemsImgFriends);
            convertView.setTag(viewHodler);
        }
        else {

            viewHodler = (ViewHodler)convertView.getTag();
        }

        viewHodler.lblHouseName.setText(list_data.get(position).getHouseName());
        viewHodler.lblFromDate.setText(list_data.get(position).getFromDate());
        viewHodler.lblToDate.setText(list_data.get(position).getToDate());
        viewHodler.lblPrice.setText(list_data.get(position).getPrice());

        Glide.with(context).load(list_data.get(position).getItemPhoto()).into(viewHodler.itemsImgFriends);

        return convertView;
    }

    /*public void filter(String text)
    {

        text = text.toLowerCase();
        list_data.clear();
        if(text.equals(""))
        {
            list_data.addAll(data);
        }
        else
        {
            for (StockData kdata : data)
            {
                if(kdata.getItem().toLowerCase().contains(text)
                        || kdata.getWarehouse().toLowerCase().contains(text))
                {
                    list_data.add(kdata);
                }
            }
        }

        notifyDataSetChanged();

    }*/
}
