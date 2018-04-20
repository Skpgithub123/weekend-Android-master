package com.yacob.weekend;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;
import com.yacob.weekend.structure.Home_data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Check_date extends AppCompatActivity implements OnDateSelectedListener
{
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private double price=0,prix;
    MaterialCalendarView widget;
    TextView lblSelectedDates,fromdate,todate;
    Toolbar mToolbar;
    List<CalendarDay> calendarDays = new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference mRootRef,mchild;
    public static Home_data hotelsData;
    String booked;
    CalendarDay firstDay;
    CalendarDay lastDay;
    Button save;
    ArrayList<CalendarDay> dates;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_dates);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Check Availability");
        //mToolbar.setLogo(R.drawable.icon);
        mToolbar.setNavigationOnClickListener
                (new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();

                    }
                });
        //lblSelectedDates = (TextView)findViewById(R.id.lblSelectedDates);
        widget = (MaterialCalendarView)findViewById(R.id.calendarView);
        fromdate= findViewById(R.id.fromdate);
        todate= findViewById(R.id.todate);
        save = findViewById(R.id.savedate);
        //database = FirebaseDatabase.getInstance();
        //fromdate.setText("");
        //todate.setText("");



        //widget.setSelectionMode(SELECTION_MODE_MULTIPLE);
          widget.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);
        //widget.setCurrentDate(widget.getCurrentDate().getDate());

        Date currentTime = Calendar.getInstance().getTime();

        widget.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(currentTime)
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
        dates = new ArrayList<CalendarDay>();
        getcheckeddates();
        //dates.add(CalendarDay.from(2018,0,8));
       // widget.setOnRangeSelectedListener(this);
        //widget.OnDateSelectedListener(this);
        widget.setOnDateChangedListener(this);
        //widget.setOnMonthChangedListener(this);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (widget.getSelectedDates()!=null) {
                    Boolean test = verifydate();
                    if (test) {
                        Intent intent = new Intent(Check_date.this, Detail_home.class);
                        Detail_home.hotelsData = hotelsData;
                        Reservation4.datelist = widget.getSelectedDates();
                        intent.putExtra("KEY_FROM", "fromCheck");
                        intent.putExtra("fromdate", dateTostring(firstDay));
                        intent.putExtra("todate", dateTostring(lastDay));
                        intent.putExtra("num", widget.getSelectedDates().size());
                        intent.putExtra("price", String.valueOf(price));
                        startActivity(intent);
                    } else
                        Toast.makeText(Check_date.this, "الرجاء اعاده اختيار ايام الحجز", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Check_date.this, "الرجاء اختيار ايام الحجز", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }



    @Override
    public void onDateSelected(@NonNull final MaterialCalendarView widget, @Nullable final CalendarDay date, final boolean selected) {
        int day = date.getDay();
        int weekOfMonth  = date.getCalendar().get(Calendar.WEEK_OF_MONTH);
        int weekOfYear  = date.getCalendar().get(Calendar.WEEK_OF_YEAR)+1;
        final int dayOfWeek = date.getCalendar().get(Calendar.DAY_OF_WEEK);
        database = FirebaseDatabase.getInstance();
        mRootRef = database.getReference("No5tha/BookingDatabases/"+hotelsData.getHouseId()+"/"+2018+"/"+weekOfYear);
        mRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Calendar calendar = Calendar.getInstance();
                String weekdays = dataSnapshot.child("weekdays").getValue().toString();
                Log.d("weekdays",""+weekdays);
                if (weekdays.equals("true")){
                    switch (String.valueOf(dayOfWeek)){
                        case "1" :
                            if(selected){
                                prix = Double.parseDouble(dataSnapshot.child("1").child("price").getValue().toString());
                                price+=prix;
                            calendar.setTimeInMillis(date.getCalendar().getTimeInMillis()+24*60*60*1000);
                            prix = Double.parseDouble(dataSnapshot.child("2").child("price").getValue().toString());
                            price+=prix;
                            widget.setDateSelected(calendar,true);
                            calendar.setTimeInMillis(date.getCalendar().getTimeInMillis()+2*24*60*60*1000);
                            prix = Double.parseDouble(dataSnapshot.child("3").child("price").getValue().toString());
                            price+=prix;
                            widget.setDateSelected(calendar,true);
                            calendar.setTimeInMillis(date.getCalendar().getTimeInMillis()+3*24*60*60*1000);
                            prix =Double.parseDouble(dataSnapshot.child("4").child("price").getValue().toString());
                            price+=prix;
                            widget.setDateSelected(calendar,true);
                            }else{
                                widget.clearSelection();
                                price=0;
                            }
                            break;
                        case "2" :
                            if(selected){
                                prix = Double.parseDouble(dataSnapshot.child("2").child("price").getValue().toString());
                                price+=prix;
                            calendar.setTimeInMillis(date.getCalendar().getTimeInMillis()-24*60*60*1000);
                            if(calendar.after(Calendar.getInstance())){
                            widget.setDateSelected(calendar,true);
                                prix = Double.parseDouble(dataSnapshot.child("1").child("price").getValue().toString());
                                price+=prix;}
                            calendar.setTimeInMillis(date.getCalendar().getTimeInMillis()+24*60*60*1000);
                            widget.setDateSelected(calendar,true);
                                prix = Double.parseDouble(dataSnapshot.child("3").child("price").getValue().toString());
                                price+=prix;
                            calendar.setTimeInMillis(date.getCalendar().getTimeInMillis()+2*24*60*60*1000);
                                prix = Double.parseDouble(dataSnapshot.child("4").child("price").getValue().toString());
                                price+=prix;
                            widget.setDateSelected(calendar,true);
                            }else{
                                widget.clearSelection();
                                price=0;
                            }
                            break;
                        case "3" :
                            if(selected){
                                prix = Double.parseDouble(dataSnapshot.child("3").child("price").getValue().toString());
                                price+=prix;
                            calendar.setTimeInMillis(date.getCalendar().getTimeInMillis()-2*24*60*60*1000);
                            if(calendar.after(Calendar.getInstance())){
                            widget.setDateSelected(calendar,true);
                                prix = Double.parseDouble(dataSnapshot.child("1").child("price").getValue().toString());
                                price+=prix;}
                            calendar.setTimeInMillis(date.getCalendar().getTimeInMillis()-24*60*60*1000);
                            if(calendar.after(Calendar.getInstance())){
                            widget.setDateSelected(calendar,true);
                                prix = Double.parseDouble(dataSnapshot.child("2").child("price").getValue().toString());
                                price+=prix;}
                            calendar.setTimeInMillis(date.getCalendar().getTimeInMillis()+24*60*60*1000);
                                prix = Double.parseDouble(dataSnapshot.child("4").child("price").getValue().toString());
                                price+=prix;
                            widget.setDateSelected(calendar,true);
                            }else{
                                widget.clearSelection();
                                price=0;
                            }
                            break;
                        case "4" :
                            if(selected){
                                //prix=Double.parseDouble("22.5");
                                prix = Double.parseDouble(dataSnapshot.child("4").child("price").getValue().toString());
                                price= price+prix;
                            calendar.setTimeInMillis(date.getCalendar().getTimeInMillis()-3*24*60*60*1000);
                            if(calendar.after(Calendar.getInstance())){
                            widget.setDateSelected(calendar,true);
                                prix = Double.parseDouble(dataSnapshot.child("1").child("price").getValue().toString());
                                price= price+prix;}
                            calendar.setTimeInMillis(date.getCalendar().getTimeInMillis()-2*24*60*60*1000);
                                if(calendar.after(Calendar.getInstance())){
                            widget.setDateSelected(calendar,true);
                                    prix = Double.parseDouble(dataSnapshot.child("2").child("price").getValue().toString());
                                    price= price+prix;}
                            calendar.setTimeInMillis(date.getCalendar().getTimeInMillis()-24*60*60*1000);
                            if(calendar.after(Calendar.getInstance())){
                            widget.setDateSelected(calendar,true);
                                prix = Double.parseDouble(dataSnapshot.child("3").child("price").getValue().toString());
                                price= price+prix;}
                            }else{
                                widget.clearSelection();
                                price=0;
                            }
                            break;
                    }
                }else{
                    switch (String.valueOf(dayOfWeek)){
                        case "1" :
                            if(selected){
                                prix = Double.parseDouble(dataSnapshot.child("1").child("price").getValue().toString());
                                price+=prix;
                            }else{
                                prix = Double.parseDouble(dataSnapshot.child("1").child("price").getValue().toString());
                                price=price-prix;
                            }
                            break;
                        case "2" :
                            if(selected){
                                prix = Double.parseDouble(dataSnapshot.child("2").child("price").getValue().toString());
                                price+=prix;

                            }else{
                                prix = Double.parseDouble(dataSnapshot.child("2").child("price").getValue().toString());
                                price=price-prix;
                            }
                            break;
                        case "3" :
                            if(selected){
                                prix = Double.parseDouble(dataSnapshot.child("3").child("price").getValue().toString());
                                price+=prix;
                            }else{
                                prix = Double.parseDouble(dataSnapshot.child("3").child("price").getValue().toString());
                                price=price-prix;
                            }
                            break;
                        case "4" :
                            if(selected){
                                prix = Double.parseDouble(dataSnapshot.child("4").child("price").getValue().toString());
                                price+=prix;
                            }else{
                                prix = Double.parseDouble(dataSnapshot.child("4").child("price").getValue().toString());
                                price=price-prix;
                            }
                            break;
                        default:
                            break;
                    }
                }
                String weekends = dataSnapshot.child("weekends").getValue().toString();
                Log.d("weekends",""+weekends);
                if (weekends.equals("true")){
                    switch (String.valueOf(dayOfWeek)){
                        case "5" :
                            if(selected){
                                prix = Double.parseDouble(dataSnapshot.child("5").child("price").getValue().toString());
                                price+=prix;
                            calendar.setTimeInMillis(date.getCalendar().getTimeInMillis()+24*60*60*1000);
                            widget.setDateSelected(calendar,true);
                                prix = Double.parseDouble(dataSnapshot.child("6").child("price").getValue().toString());
                                price+=prix;
                            calendar.setTimeInMillis(date.getCalendar().getTimeInMillis()+2*24*60*60*1000);
                            widget.setDateSelected(calendar,true);
                                prix = Double.parseDouble(dataSnapshot.child("7").child("price").getValue().toString());
                                price+=prix;
                            }else{
                                widget.clearSelection();
                                price=0;
                            }
                            break;
                        case "6" :
                            if(selected){
                                prix = Double.parseDouble(dataSnapshot.child("6").child("price").getValue().toString());
                                price+=prix;
                            calendar.setTimeInMillis(date.getCalendar().getTimeInMillis()-24*60*60*1000);
                            if(calendar.after(Calendar.getInstance())){
                            widget.setDateSelected(calendar,true);
                                prix = Double.parseDouble(dataSnapshot.child("5").child("price").getValue().toString());
                                price+=prix;}
                            calendar.setTimeInMillis(date.getCalendar().getTimeInMillis()+24*60*60*1000);
                            widget.setDateSelected(calendar,true);
                                prix = Double.parseDouble(dataSnapshot.child("2").child("price").getValue().toString());
                                price+=prix;
                            }else{
                                widget.clearSelection();
                                price=0;
                            }
                            break;
                        case "7" :
                            if(selected){
                                prix = Double.parseDouble(dataSnapshot.child("7").child("price").getValue().toString());
                                price+=prix;
                            calendar.setTimeInMillis(date.getCalendar().getTimeInMillis()-2*24*60*60*1000);
                            if(calendar.after(Calendar.getInstance())){
                            widget.setDateSelected(calendar,true);
                                prix = Double.parseDouble(dataSnapshot.child("5").child("price").getValue().toString());
                                price+=prix;}
                            calendar.setTimeInMillis(date.getCalendar().getTimeInMillis()-24*60*60*1000);
                            if(calendar.after(Calendar.getInstance())){
                            widget.setDateSelected(calendar,true);
                                prix = Double.parseDouble(dataSnapshot.child("6").child("price").getValue().toString());
                                price+=prix;}
                            }else{
                                widget.clearSelection();
                                price=0;
                            }
                            break;
                        default:
                            break;
                    }
                }else{
                    switch (String.valueOf(dayOfWeek)){
                        case "5" :
                            if(selected){
                                prix = Double.parseDouble(dataSnapshot.child("5").child("price").getValue().toString());
                                price+=prix;
                            }else{
                                prix = Double.parseDouble(dataSnapshot.child("5").child("price").getValue().toString());
                                price=price-prix;
                            }
                            break;
                        case "6" :
                            if(selected){
                                prix = Double.parseDouble(dataSnapshot.child("6").child("price").getValue().toString());
                                price+=prix;

                            }else{
                                prix = Double.parseDouble(dataSnapshot.child("6").child("price").getValue().toString());
                                price=price-prix;
                            }
                            break;
                        case "7" :
                            if(selected){
                                prix = Double.parseDouble(dataSnapshot.child("7").child("price").getValue().toString());
                                price+=prix;
                            }else{
                                prix = Double.parseDouble(dataSnapshot.child("7").child("price").getValue().toString());
                                price=price-prix;
                            }
                            break;
                        default:
                            break;
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    private Boolean verifydate() {
        Calendar calendar = Calendar.getInstance();
        List<CalendarDay> selected= widget.getSelectedDates();
        firstDay=selected.get(0);
        lastDay= selected.get(0);
        for (int i=1;i<selected.size();i++){
            if(selected.get(i).isBefore(firstDay))
                firstDay=selected.get(i);
             if(selected.get(i).isAfter(lastDay))
                 lastDay=selected.get(i);
        }
        int j=1;
        while (j<selected.size()){
            calendar.setTimeInMillis(firstDay.getCalendar().getTimeInMillis()+24*60*60*1000*j);
            if (!selected.contains(CalendarDay.from(calendar)))
                return false;
            j++;
        }
        return true;
    }

    private String getSelectedDatesString() {
        String ppp = "";
        CalendarDay date = widget.getSelectedDate();
        calendarDays = widget.getSelectedDates();

        String my_str=null;
        StringBuilder bldr=new StringBuilder();
        for (int i = 0; i < calendarDays.size(); i++)
            bldr.append(FORMATTER.format(calendarDays.get(i).getDate()).toString()).append(",,");
        my_str=bldr.toString();
        ppp = my_str;
        Log.d("KEY_STOCKID",""+ppp);

        if (date == null) {
            return "No Selection";
        }
        //return FORMATTER.format(date.getDate());
        return ppp;
    }


  public void getcheckeddates(){


      database = FirebaseDatabase.getInstance();
      mRootRef = database.getReference("/No5tha/BookingDatabases/"+hotelsData.getHouseId()+"/2018/");
      mchild = mRootRef.child("public/");
      //lstItems.setAdapter(new HotelsItemsListAdapter(SplashActivity.hotelsDatas,getActivity()));

      //Retriving data from Firebase Path
     // hotelsDatas.clear();

      mRootRef.addChildEventListener(new ChildEventListener() {
          @Override
          public void onChildAdded(DataSnapshot dataSnapshot, String s) {
              String book = dataSnapshot.child(String.valueOf(1)).child("booked").getValue().toString();
              for(int i=1;i<8;i++){
                  if(dataSnapshot.child(String.valueOf(i)).child("booked").getValue()==null){
                      String child=dataSnapshot.getKey();
                     mRootRef.child(child).child(String.valueOf(i)).child("booked").setValue("false");
                  }else {
                      String booked = dataSnapshot.child(String.valueOf(i)).child("booked").getValue().toString();
                      Log.d("booked", "" + booked + " " + dataSnapshot.getKey() + " " + i);
                      if (booked.equals("true")) {
                          int week = Integer.parseInt(dataSnapshot.getKey().toString());
                          long wmelli = (week-1)* 7L * 24L * 60L * 60L * 1000L;
                          long dmelli = (i - 1) * 24L * 60L * 60L * 1000L;
                          long melli = wmelli + dmelli + 1514761200000L;
                          Calendar calendar = Calendar.getInstance();
                          calendar.setTimeInMillis(melli);
                          dates.add(CalendarDay.from(calendar));
                      }

                  }
              }
              //dates.add(CalendarDay.from(2018,4,17));
              widget.addDecorator(new EventDecorator(dates));


              /*for (DataSnapshot ds : dataSnapshot.getChildren()) {
                  //String booked = dataSnapshot.child("booked").getValue().toString();
                  //Log.d("booked",""+booked);

              }*/
          }
          @Override
          public void onChildChanged(DataSnapshot dataSnapshot, String s) {

          }

          @Override
          public void onChildRemoved(DataSnapshot dataSnapshot) {

          }

          @Override
          public void onChildMoved(DataSnapshot dataSnapshot, String s) {

          }

          @Override
          public void onCancelled(DatabaseError databaseError) {

          }
      });

  }
  public String dateTostring(CalendarDay date){
      String day =String.valueOf(date.getDay());
      String mois = String.valueOf(date.getMonth());
      String year =String.valueOf(date.getYear());
      String month = "";
      switch (mois){
          case "0": month="January";
              break;
          case "1": month="February";
              break;
          case "2": month="March";
              break;
          case "3": month="April";
              break;
          case "4": month="Mai";
              break;
          case "5": month="June";
              break;
          case "6": month="July";
              break;
          case "7": month="August";
              break;
          case "8": month="September";
              break;
          case "9": month="October";
              break;
          case "10": month="November";
              break;
          case "11": month="December";
              break;
          default: month = "Invalid month";
              break;
      }
      return day +" "+month+" "+year;
  }

    @Override
    public void onBackPressed() {
        /*Intent intent = new Intent(Check_date.this, MainActivity.class);
        startActivity(intent);
        finish();*/
        super.onBackPressed();
    }

   //@Override
    /*public void onRangeSelected(@NonNull MaterialCalendarView widget, @NonNull List<CalendarDay> dates) {
        String day =String.valueOf(dates.get(0).getDay());
        String mois = String.valueOf(dates.get(0).getMonth());
        String year =String.valueOf(dates.get(0).getYear());
        String month = "";
        switch (mois){
           case "0": month="January";
               break;
           case "1": month="February";
               break;
           case "2": month="March";
               break;
           case "3": month="April";
               break;
           case "4": month="Mai";
               break;
           case "5": month="June";
               break;
           case "6": month="July";
               break;
           case "7": month="August";
               break;
           case "8": month="September";
               break;
           case "9": month="October";
               break;
           case "10": month="November";
               break;
           case "11": month="December";
               break;
           default: month = "Invalid month";
                break;
        }
         firstDay = day +"  "+month+"   "+year;
        fromdate.setText(firstDay);
         day =String.valueOf(dates.get(dates.size() - 1).getDay());
         mois = String.valueOf(dates.get(dates.size() - 1).getMonth());
         year =String.valueOf(dates.get(dates.size() - 1).getYear());
         month = "";
        switch (mois){
            case "0": month="January";
                break;
            case "1": month="February";
                break;
            case "2": month="March";
                break;
            case "3": month="April";
                break;
            case "4": month="Mai";
                break;
            case "5": month="June";
                break;
            case "6": month="July";
                break;
            case "7": month="August";
                break;
            case "8": month="September";
                break;
            case "9": month="October";
                break;
            case "10": month="November";
                break;
            case "11": month="December";
                break;
            default: month = "Invalid month";
                break;
        }
        lastDay = day +"  "+month+"   "+year;
        todate.setText(lastDay);
    }*/

}
class EventDecorator implements DayViewDecorator {

    private final ArrayList<CalendarDay> dates;

    public EventDecorator(ArrayList<CalendarDay> dates) {
        this.dates = dates;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
         return dates.contains(day);
        //return true;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void decorate(DayViewFacade view) {
        view.setDaysDisabled(true);
        view.addSpan(new ForegroundColorSpan(Color.RED));
        view.addSpan(new DotSpan(5, Color.RED));


    }
}
