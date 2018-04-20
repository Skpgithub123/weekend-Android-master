package com.yacob.weekend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yacob.weekend.structure.Home_data;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Reservation extends AppCompatActivity  implements OnMapReadyCallback {
    public static Home_data hotelsData;
    private GoogleMap mMap;
    Button next;
    TextView date_reserv,name_reserv,txtprice;
    String fromdate,todate,price;
    int num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        date_reserv =findViewById(R.id.date_reserv);
        name_reserv =findViewById(R.id.name_reserv);
        next = findViewById(R.id.button4);
        txtprice=findViewById(R.id.textView6);
        //step view
        HorizontalStepView setpview5 = (HorizontalStepView) findViewById(R.id.step_view);
        List<StepBean> stepsBeanList = new ArrayList<>();
        StepBean stepBean0 = new StepBean("",0);
        StepBean stepBean1 = new StepBean("",-1);
        StepBean stepBean2 = new StepBean("",-1);
        StepBean stepBean3 = new StepBean("",-1);

        stepsBeanList.add(stepBean0);
        stepsBeanList.add(stepBean1);
        stepsBeanList.add(stepBean2);
        stepsBeanList.add(stepBean3);



        setpview5
                .setStepViewTexts(stepsBeanList)
                .setTextSize(12)//set textSize
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(this, android.R.color.white))//tepsViewIndicator
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(this, android.R.color.darker_gray))//StepsViewIndicator
                .setStepViewComplectedTextColor(ContextCompat.getColor(this, android.R.color.white))//StepsView text
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(this, R.color.uncompleted_text_color))//stepsView text
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(this, R.drawable.step1))//StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(this, R.drawable.step3))//StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(this, R.drawable.step2));//StepsViewIndicator AttentionIcon
        //Initialization of Map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_reserv);
        mapFragment.getMapAsync(this);
        //picked date
        Intent intent = getIntent();

        name_reserv.setText(hotelsData.getHouseName());

        fromdate=intent.getStringExtra("fromdate");
        todate=intent.getStringExtra("todate");
        num = intent.getIntExtra("num",0);
        price = intent.getStringExtra("price");
        date_reserv.setText(fromdate+"-"+todate);
        Double prix= Double.valueOf(price);
        txtprice.setText("لقد حجزت " + String.valueOf(num-1) + " يوم بثمن\n" + new DecimalFormat("###.##").format(prix) + "د.ك");
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Reservation.this,Reservation2.class);
                Reservation2.hotelsData=hotelsData;
                intent.putExtra("fromdate",fromdate);
                intent.putExtra("todate",todate);
                intent.putExtra("num",num);
                intent.putExtra("price",price);
                startActivity(intent);

            }
        });


    }
    @Override
    public void onBackPressed() {
        /*Intent intent = new Intent(Reservation.this, MainActivity.class);
        startActivity(intent);
        finish();*/
        super.onBackPressed();
    }
    //Displaying Marker on Map
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        double lat = Double.valueOf(hotelsData.getLatitude());
        double longi = Double.valueOf(hotelsData.getLongitude());
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lat, longi);
        mMap.addMarker(new MarkerOptions().position(sydney).title(hotelsData.getHouseName()));
        float zoomLevel = 15.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel));
    }
}
