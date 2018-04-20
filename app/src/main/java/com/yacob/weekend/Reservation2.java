package com.yacob.weekend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.yacob.weekend.structure.Home_data;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Reservation2 extends AppCompatActivity {
    public static Home_data hotelsData;
    Button agree;
    String fromdate,todate,price;
    int num;
    TextView rule,pricetxt;
    ImageView ruleimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation2);
        HorizontalStepView setpview2 = (HorizontalStepView) findViewById(R.id.step_view2);
        List<StepBean> stepsBeanList = new ArrayList<>();
        StepBean stepBean0 = new StepBean("", 1);
        StepBean stepBean1 = new StepBean("", 0);
        StepBean stepBean2 = new StepBean("", -1);
        StepBean stepBean3 = new StepBean("", -1);
        stepsBeanList.add(stepBean0);
        stepsBeanList.add(stepBean1);
        stepsBeanList.add(stepBean2);
        stepsBeanList.add(stepBean3);


        setpview2
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
        Intent intent = getIntent();
        rule = findViewById(R.id.txtrule);
        ruleimg = findViewById(R.id.rule);
        if (hotelsData.getTypeOfPeopleAllowedToRent().equals("mix")){
            ruleimg.setImageResource(R.drawable.mix_b);
            rule.setText("هذا المنزل يسمح للكراء للعائلات و للاصدقاء");
        }
        if (hotelsData.getTypeOfPeopleAllowedToRent().equals("freinds")){
            ruleimg.setImageResource(R.drawable.freinds_b);
            rule.setText("هذا المنزل يسمح للكراء للاصدقاء");
        }
        if (hotelsData.getTypeOfPeopleAllowedToRent().equals("family")){
            ruleimg.setImageResource(R.drawable.family_b);
            rule.setText("هذا المنزل يسمح للكراء للعائلات");
        }
        pricetxt=findViewById(R.id.price2);

        fromdate=intent.getStringExtra("fromdate");
        todate=intent.getStringExtra("todate");
        num = intent.getIntExtra("num",0);
        price = intent.getStringExtra("price");
        Double prix= Double.valueOf(price);
        pricetxt.setText("لقد حجزت " + String.valueOf(num-1) + " يوم بثمن\n" + new DecimalFormat("###.##").format(prix) + "د.ك");
        agree = findViewById(R.id.agree);
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Reservation2.this,Reservation3.class);
                Reservation3.hotelsData=hotelsData;
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
        super.onBackPressed();
        /*Intent intent = new Intent(Reservation2.this, MainActivity.class);
        startActivity(intent);
        finish();*/
    }

}
