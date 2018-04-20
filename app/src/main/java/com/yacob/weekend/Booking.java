package com.yacob.weekend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yacob.weekend.adapter.MyBookingListAdapter;
import com.yacob.weekend.structure.MyBookingData;

import java.util.ArrayList;


public class Booking extends AppCompatActivity
{
    Toolbar mToolbar;
    ListView lstBooking,lstRequest;
    TextView numb,numr;
    ArrayList<MyBookingData> myBookingDatas = new ArrayList<>();
    ArrayList<MyBookingData> myRequestDatas = new ArrayList<>();
    FirebaseDatabase database,database2;
    DatabaseReference mRootRef,mchild,mRootRef2,mchild2;
    int i,j;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        lstBooking = (ListView)findViewById(R.id.lstBooking);
        lstRequest = (ListView)findViewById(R.id.lstBookingRequest);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        numb = findViewById(R.id.num_booking);
        numr = findViewById(R.id.num_request);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Booking List");
        //mToolbar.setLogo(R.drawable.icon);

        database = FirebaseDatabase.getInstance();
        mRootRef = database.getReference("/No5tha");
        mchild = mRootRef.child("BookingData/");
        //lstItems.setAdapter(new HotelsItemsListAdapter(SplashActivity.hotelsDatas,getActivity()));

        //Retriving data from Firebase Path
        i=0;
        myBookingDatas.clear();
        mchild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.child("houseName").getValue()!=null) {
                        String name = (ds.child("houseName").getValue().toString());
                        String fromdate = (ds.child("from").getValue().toString());
                        String todate = (ds.child("to").getValue().toString());
                        String price = (ds.child("price").getValue().toString());
                        String id = (ds.child("houseID").getValue().toString());
                        //String photo = (ds.child("Bool").getValue().toString());
                        myBookingDatas.add(new MyBookingData(name, fromdate, todate, price, id, R.drawable.menu2));
                        i++;
                    }
                }
                lstBooking.setAdapter(new MyBookingListAdapter(myBookingDatas,getApplicationContext()));
                numb.setText((String.valueOf(i)));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //ruquest booking
        database2 = FirebaseDatabase.getInstance();
        mRootRef2 = database2.getReference("/No5tha");
        mchild2= mRootRef2.child("BookingRequest/");
        //lstItems.setAdapter(new HotelsItemsListAdapter(SplashActivity.hotelsDatas,getActivity()));

        //Retriving data from Firebase Path
        myRequestDatas.clear();
        j=0;
        mchild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.child("houseName").getValue()!=null) {
                        String name = (ds.child("houseName").getValue().toString());
                        String fromdate = (ds.child("from").getValue().toString());
                        String todate = (ds.child("to").getValue().toString());
                        String price = (ds.child("price").getValue().toString());
                        String id = (ds.child("houseID").getValue().toString());
                        //String photo = (ds.child("Bool").getValue().toString());
                        myRequestDatas.add(new MyBookingData(name, fromdate, todate, price, id, R.drawable.menu2));
                        j++;
                    }
                }
                lstRequest.setAdapter(new MyBookingListAdapter(myRequestDatas,getApplicationContext()));
                numr.setText(String.valueOf(j));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}

