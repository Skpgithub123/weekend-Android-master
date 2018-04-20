package com.yacob.weekend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.yacob.weekend.structure.Home_data;

import java.util.ArrayList;
import java.util.List;

public class Reservation3 extends AppCompatActivity {

    public static Home_data hotelsData;
    Button btnonline,btncash;
    ImageView online,cash;
    String fromdate,todate,price;
    int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation3);
        HorizontalStepView setpview3 = (HorizontalStepView) findViewById(R.id.step_view3);
        List<StepBean> stepsBeanList = new ArrayList<>();
        StepBean stepBean0 = new StepBean("",1);
        StepBean stepBean1 = new StepBean("",1);
        StepBean stepBean2 = new StepBean("",0);
        StepBean stepBean3 = new StepBean("",-1);
        stepsBeanList.add(stepBean0);
        stepsBeanList.add(stepBean1);
        stepsBeanList.add(stepBean2);
        stepsBeanList.add(stepBean3);



        setpview3
                .setStepViewTexts(stepsBeanList)
                .setTextSize(12)//set textSize
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(this, android.R.color.white))//tepsViewIndicator
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(this, android.R.color.darker_gray))//StepsViewIndicator
                .setStepViewComplectedTextColor(ContextCompat.getColor(this, android.R.color.white))//StepsView text
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(this, R.color.uncompleted_text_color))//stepsView text
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(this, R.drawable.step1))//StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(this, R.drawable.step3))//StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(this, R.drawable.step2));//StepsViewIndicator AttentionIcon
        // end stepview
        Intent intent=getIntent();
        fromdate=intent.getStringExtra("fromdate");
        todate=intent.getStringExtra("todate");
        num = intent.getIntExtra("num",0);
        price = intent.getStringExtra("price");
        btncash=findViewById(R.id.btncash);
        cash = findViewById(R.id.cash);
        btnonline=findViewById(R.id.btnonline);
        online = findViewById(R.id.ligne);
        btncash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Reservation3.this,Reservation4.class);
                Reservation4.hotelsData=hotelsData;
                intent.putExtra("fromdate",fromdate);
                intent.putExtra("todate",todate);
                intent.putExtra("num",num);
                intent.putExtra("price",price);
                startActivity(intent);
                finish();


            }
        });
        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Reservation3.this,Reservation4.class);
                Reservation4.hotelsData=hotelsData;
                intent.putExtra("fromdate",fromdate);
                intent.putExtra("todate",todate);
                intent.putExtra("num",num);
                intent.putExtra("price",price);
                startActivity(intent);
                finish();
            }
        });
        btnonline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Reservation3.this,Reservation4.class);
                Reservation4.hotelsData=hotelsData;
                intent.putExtra("fromdate",fromdate);
                intent.putExtra("todate",todate);
                intent.putExtra("num",num);
                intent.putExtra("price",price);
                startActivity(intent);
                finish();
            }
        });
        online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Reservation3.this,Reservation4.class);
                Reservation4.hotelsData=hotelsData;
                intent.putExtra("fromdate",fromdate);
                intent.putExtra("todate",todate);
                intent.putExtra("num",num);
                intent.putExtra("price",price);
                startActivity(intent);
                finish();

            }
        });
    }
    @Override
    public void onBackPressed() {
        /*Intent intent = new Intent(Reservation3.this, MainActivity.class);
        startActivity(intent);
        finish();*/
        super.onBackPressed();
    }
}
