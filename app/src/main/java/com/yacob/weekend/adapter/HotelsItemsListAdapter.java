package com.yacob.weekend.adapter;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yacob.weekend.Detail_home;
import com.yacob.weekend.R;
import com.yacob.weekend.data.DBHelper;
import com.yacob.weekend.structure.Home_data;

import java.util.ArrayList;

public class HotelsItemsListAdapter extends ArrayAdapter<Home_data>{

    private ArrayList<Home_data> dataSet;
    Context mContext;
    private Cursor cursor ;
    private  int columnIndex, position = 0;
    float initialX ;
    final String TAG = "ici ";
    DBHelper dbHelper;
    boolean favbool=false;
    // View lookup cache
    static class ViewHolder {
        TextView txttitle;
        ImageView images;
        TextView txtlocation;
        TextView txtprice;
        TextView numroom;
        TextView numwc;
        ImageButton fav;
        ImageView swim;
        Button details;
    }

    public HotelsItemsListAdapter(ArrayList<Home_data> data, Context context) {
        super(context, R.layout.custom_home_item, data);
        this.dataSet = data;
        this.mContext=context;

    }


    private int lastPosition = -1;
    private  int pos =0;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Home_data dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;
        dbHelper =  new DBHelper(getContext());

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_home_item, parent, false);
            viewHolder.txttitle = (TextView) convertView.findViewById(R.id.info);
            viewHolder.images = (ImageView) convertView.findViewById(R.id.imageView1);
            viewHolder.txtlocation = (TextView) convertView.findViewById(R.id.textView5);
            viewHolder.txtprice = (TextView) convertView.findViewById(R.id.textView3);
            viewHolder.numroom = (TextView) convertView.findViewById(R.id.numroom);
            viewHolder.numwc = (TextView) convertView.findViewById(R.id.numwc);
            viewHolder.fav = (ImageButton) convertView.findViewById(R.id.imageView3);
            viewHolder.details= convertView.findViewById(R.id.detail);
            viewHolder.swim= convertView.findViewById(R.id.swim);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        lastPosition = position;

        viewHolder.txttitle.setText(dataModel.getHouseName());
        viewHolder.txtlocation.setText(dataModel.getLocation());
        viewHolder.txtprice.setText("");
        viewHolder.numwc.setText(dataModel.getToilets());
        viewHolder.numroom.setText(dataModel.getRooms());
        if (parent.getId()==R.id.lstFavItems){
            Glide.with(mContext).load(R.drawable.heartr).into(viewHolder.fav);
            favbool=true;

        }else{
            long value = dbHelper.insertFavHotelsData(dataModel);
            if (value == -1) {
                //Toast.makeText(getContext(), "Already Saved To Favourite", Toast.LENGTH_SHORT).show();
                Glide.with(mContext).load(R.drawable.heartr).into(viewHolder.fav);

                favbool=false;
            } else {
                Glide.with(mContext).load(R.drawable.heartg).into(viewHolder.fav);
                boolean testdelete= dbHelper.deleteSingleFavHotelData(dataModel);
                favbool=true;
            }
        }
        if (dataModel.getPrivateSwimmingPool()=="false")
           viewHolder.swim.setVisibility(View.GONE);


        viewHolder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // if(!favbool) {
                    //Glide.with(mContext).load(R.drawable.heartr).into(viewHolder.fav);
                    long value = dbHelper.insertFavHotelsData(dataModel);
                    if (value == -1) {
                        //Toast.makeText(getContext(), "Already Saved To Favourite", Toast.LENGTH_SHORT).show();
                        Glide.with(mContext).load(R.drawable.heartg).into(viewHolder.fav);
                        boolean testdelete= dbHelper.deleteSingleFavHotelData(dataModel);
                        if(testdelete)
                            Toast.makeText(getContext(), "تم فسخ الشاليه من المفضلات", Toast.LENGTH_SHORT).show();
                    } else {
                        Glide.with(mContext).load(R.drawable.heartr).into(viewHolder.fav);
                        Toast.makeText(getContext(), "تم اضافت الشاليه  للمفاضلات", Toast.LENGTH_SHORT).show();
                    }
               /* }else{
                    Glide.with(mContext).load(R.drawable.heartg).into(viewHolder.fav);
                    boolean testdelete= dbHelper.deleteSingleFavHotelData(dataModel);
                    if(testdelete)
                        Toast.makeText(getContext(), "delete Favourite", Toast.LENGTH_SHORT).show();


                }*/
            }
        });
        //String cc=dataSet.get(position).getHouseName();
        Glide.with(mContext).load(dataSet.get(position).getSingleImagePhoto()).into(viewHolder.images);
        viewHolder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,Detail_home.class);
                Detail_home.hotelsData= dataModel;
                intent.putExtra("KEY_FROM","fromMain");
                mContext.startActivity(intent);
            }
        });


        // Return the completed view to render on screen
        return convertView;
    }

}

