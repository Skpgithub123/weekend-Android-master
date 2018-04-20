package com.yacob.weekend;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yacob.weekend.adapter.HotelsItemsListAdapter;
import com.yacob.weekend.data.DBHelper;
import com.yacob.weekend.structure.Home_data;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_favorite extends Fragment {
    View view;
    ListView lstFavItems;
    ImageView nofav_img;
    TextView nofav_txt;
    DBHelper dbHelper;
    ArrayList<Home_data> hotelsDatas = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_favorite, container, false);
        lstFavItems = (ListView)view.findViewById(R.id.lstFavItems);
        nofav_img =view.findViewById(R.id.no_favimg);
        nofav_txt =view.findViewById(R.id.no_favtxt);

        dbHelper = new DBHelper(getActivity());
        hotelsDatas = dbHelper.GetFavHotelsData();
        Log.d("FAV_SIZE",""+hotelsDatas.size());
        if (hotelsDatas.size()==0) {
            lstFavItems.setVisibility(View.GONE);

        }else{
            nofav_img.setVisibility(View.GONE);
            nofav_txt.setVisibility(View.GONE);
            lstFavItems.setAdapter(new HotelsItemsListAdapter(hotelsDatas, getActivity()));

            lstFavItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getActivity(),
                            Detail_home.class);
                    Detail_home.hotelsData = hotelsDatas.get(i);
                    intent.putExtra("KEY_FROM", "fromFavourite");
                    startActivity(intent);

                }
            });
        }

        return view;
    }

}
