package com.yacob.weekend.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.yacob.weekend.R;
import com.yacob.weekend.structure.Home_data;

import java.util.ArrayList;


/**
 * Created by Appsthentic on 5/16/2017.
 */

public class HorizontalListAdapter extends BaseAdapter {

    ArrayList<Home_data> list_data = new ArrayList<>();
    Context context;
    LayoutInflater inflater;

    public HorizontalListAdapter(ArrayList<Home_data> list_data, Context context) {
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
        TextView itemLblHouseName,itemLblLocation,itemLblPrice,itemsLblToilet,itemsLblBedroom;
        ImageView itemsImgSwimmingPool;
        RoundedImageView itemsImg;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler;
        if(convertView == null)
        {
            viewHodler = new ViewHodler();
            convertView = inflater.inflate(R.layout.items_horiz,null);
           /* Typeface font = Typeface.createFromAsset(context.getAssets(),
                    "RobotoCondensedRegular.ttf");*/
            viewHodler.itemLblHouseName = (TextView)convertView.findViewById(R.id.itemLblHouseName);
            viewHodler.itemLblLocation = (TextView)convertView.findViewById(R.id.itemLblLocation);
            viewHodler.itemLblPrice = (TextView)convertView.findViewById(R.id.itemLblPrice);
            viewHodler.itemsImg = (RoundedImageView) convertView.findViewById(R.id.itemsImg);
            /*viewHodler.itemsLblToilet = (TextView)convertView.findViewById(R.id.itemsLblToilet);
            viewHodler.itemsLblBedroom = (TextView)convertView.findViewById(R.id.itemsLblBedroom);

            viewHodler.itemsImgSwimmingPool = (ImageView) convertView.findViewById(itemsImgSwimmingPool);*/
            /*viewHodler.lblInventoryItem.setTypeface(font);
            viewHodler.lblQuantity.setTypeface(font);
            viewHodler.lblWarehouse.setTypeface(font);*/
            convertView.setTag(viewHodler);
        }
        else {

            viewHodler = (ViewHodler)convertView.getTag();
        }

        viewHodler.itemLblHouseName.setText(list_data.get(position).getHouseName());
        viewHodler.itemLblLocation.setText(list_data.get(position).getLocation());
        viewHodler.itemLblPrice.setText(list_data.get(position).getHouseType());
        /*viewHodler.itemsLblToilet.setText(list_data.get(position).getToilets());
        viewHodler.itemsLblBedroom.setText(list_data.get(position).getRooms());*/

        Glide.with(context).load(list_data.get(position).getSingleImagePhoto()).into(viewHodler.itemsImg);

        /*String PrivateSwimmingPool = list_data.get(position).getPrivateSwimmingPool();
        Log.d("PrivateSwimmingPool",""+PrivateSwimmingPool);

        if (PrivateSwimmingPool.equals("true"))
        {
            viewHodler.itemsImgSwimmingPool.setVisibility(View.VISIBLE);
        }else {
            viewHodler.itemsImgSwimmingPool.setVisibility(View.GONE);
        }*/

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

