package com.yacob.weekend;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.igalata.bubblepicker.model.PickerItem;
import com.igalata.bubblepicker.rendering.BubblePicker;

import java.util.ArrayList;

public class Account extends AppCompatActivity {
    BubblePicker picker;
    int[] color={
            Color.parseColor("#9400D3"),
            Color.parseColor("#CD5C5C"),
            Color.parseColor("#32CD32"),
            Color.parseColor("#4169E1"),
    };
    int[] images= {
            R.drawable.logo,
            R.drawable.logo,
            R.drawable.logo,
            R.drawable.logo,
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
     /*   picker = findViewById(R.id.picker);
        ArrayList<PickerItem> list = new ArrayList<PickerItem>();
        for (int i=0 ; i< color.length;i++){
            PickerItem item = new PickerItem("ici");
            item.setTextColor(R.color.tw__solid_white);
            item.setColor(color[i]);
            list.add(item);

        }
        picker.setCenterImmediately(true);
        picker.setItems(list);*/

    }
    @Override
    protected void onResume() {
        super.onResume();
      //  picker.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    //    picker.onPause();
    }
}
