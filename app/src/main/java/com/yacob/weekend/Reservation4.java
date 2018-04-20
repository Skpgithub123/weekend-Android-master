package com.yacob.weekend;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.yacob.weekend.structure.Home_data;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reservation4 extends AppCompatActivity {

    public static Home_data hotelsData;
    public static List<CalendarDay> datelist;
    ImageView photo;
    TextView name,location,price4,price3,deposit,total;
    EditText nameuser,phone;
    Button agree;
    String fromdate,todate,price;
    int num;
    FirebaseDatabase database;
    DatabaseReference mRootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation4);
        HorizontalStepView setpview4 = (HorizontalStepView) findViewById(R.id.step_view4);
        List<StepBean> stepsBeanList = new ArrayList<>();
        StepBean stepBean0 = new StepBean("",1);
        StepBean stepBean1 = new StepBean("",1);
        StepBean stepBean2 = new StepBean("",1);
        StepBean stepBean3 = new StepBean("",0);
        stepsBeanList.add(stepBean0);
        stepsBeanList.add(stepBean1);
        stepsBeanList.add(stepBean2);
        stepsBeanList.add(stepBean3);



        setpview4
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
        database = FirebaseDatabase.getInstance();
        mRootRef = database.getReference("/No5tha/BookingRequest/");
        price4=findViewById(R.id.price4);
        agree=findViewById(R.id.agree4);
        photo =findViewById(R.id.photo4);
        nameuser=findViewById(R.id.editText3);
        phone=findViewById(R.id.editText4);
        price3=findViewById(R.id.price3);
        deposit=findViewById(R.id.deposit);
        total=findViewById(R.id.total);
        Glide.with(this).load(hotelsData.getPhotos()[0]).into(photo);
        name= findViewById(R.id.name4);
        name.setText(hotelsData.getHouseName());
        location= findViewById(R.id.locate);
        location.setText(hotelsData.getLocation());
        final Intent intent = getIntent();
        fromdate=intent.getStringExtra("fromdate");
        todate=intent.getStringExtra("todate");
        num = intent.getIntExtra("num",0);
        price = intent.getStringExtra("price");
        Double prix= Double.valueOf(price);
        price4.setText("لقد حجزت " + String.valueOf(num-1) + " يوم بثمن\n" + new DecimalFormat("###.##").format(prix) + "د.ك");
        price3.setText(new DecimalFormat("###.##").format(prix)+" د.ك");
        deposit.setText("100 د.ك");
        total.setText(new DecimalFormat("###.##").format(prix+100)+" د.ك");
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> childdates = new HashMap<>();
                for (int i=0;i<num;i++){
                    Calendar cal = datelist.get(i).getCalendar();
                    childdates.put(String.valueOf(i),cal.getTimeInMillis());

                }
                if(!nameuser.getText().toString().equals("") && !phone.getText().toString().equals("") && phone.length()==8) {
                    Map<String, Object> child = new HashMap<String, Object>();
                    child.put("Bool", "true");
                    child.put("dates", childdates);
                    child.put("deposit", "100");
                    child.put("houseId", hotelsData.getHouseId());
                    child.put("houseName", hotelsData.getHouseName());
                    child.put("isOnline", "true");
                    child.put("name", nameuser.getText().toString());
                    child.put("phoneNumber", phone.getText().toString());
                    child.put("priceShown", String.valueOf(price));
                    child.put("timestamp", Calendar.getInstance().getTimeInMillis());
                    child.put("totalPrice", String.valueOf(Double.valueOf(price) + 100));
                    mRootRef.push().updateChildren(child);
                    AlertDialog.Builder builder = new AlertDialog.Builder(Reservation4.this);
                    builder.setMessage("لقد تم تسجيل طلب حجزك بنجاح.\n سوف نتصل بك في أقرب الأجال")
                            .setCancelable(false)
                            .setPositiveButton("تم", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                    Intent intent = new Intent(Reservation4.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }else{
                   // يرجى استكمال جميع البيانات
                    AlertDialog.Builder builder = new AlertDialog.Builder(Reservation4.this);
                    builder.setMessage("ايرجى إكمال جميع البيانات باستخدام كلمة المرور المكونة من 8 أرقام")
                            .setCancelable(false)
                            .setPositiveButton("تم", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();

                }

            }
        });
    }
    @Override
    public void onBackPressed() {
        /*Intent intent = new Intent(Reservation4.this, MainActivity.class);
        startActivity(intent);
        finish();*/
        super.onBackPressed();
    }
}
