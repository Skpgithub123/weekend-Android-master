package com.yacob.weekend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yacob.weekend.adapter.HotelsItemsListAdapter;
import com.yacob.weekend.structure.Home_data;

import java.util.ArrayList;

public class Search extends AppCompatActivity {
    ListView lstItems;
    public static ArrayList<Home_data> hotelsDatas = new ArrayList<>();
    ListView refreshLayoutHotels;
    FirebaseDatabase database;
    DatabaseReference mRootRef,mchild;
    String Bool,HouseNumber,Latitude,Longitude,basement,descruption,floors,houseId,houseName,houseType,location,masterRooms,
            priority,privateSwimmingPool,rentrules,rooms,salon,toilet,typeOfPeopleAllowedToRent,whichLineOnSea;
    private String features;
    private String photoGalleries;;
    private String[] arrayFeatures;
    private String[] arrayPhotoGalleries;
    private String type="",htype="",pool="",line="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        // get extra from search
        Intent intent = getIntent();
        final String[] tab = intent.getStringArrayExtra("tab");


        lstItems = (ListView) findViewById(R.id.lstItems);
        refreshLayoutHotels =  findViewById(R.id.lstItems);
        //Initializing Firebase Database
        database = FirebaseDatabase.getInstance();
        mRootRef = database.getReference("/No5tha/housesData");
        mchild = mRootRef.child("public");
        //lstItems.setAdapter(new HotelsItemsListAdapter(SplashActivity.hotelsDatas,getActivity()));

        //Retriving data from Firebase Path
        hotelsDatas.clear();
        mchild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Bool = (ds.child("Bool").getValue().toString());
                    Log.d("Bool"," "+ds.child("Bool").getValue().toString());
                    HouseNumber = (ds.child("HouseNumber").getValue().toString());
                    Log.d("HouseNumber"," "+ds.child("HouseNumber").getValue().toString());
                    Latitude = (ds.child("Latitude").getValue().toString());
                    Log.d("Latitude"," "+ds.child("Latitude").getValue().toString());
                    Longitude = (ds.child("Longitude").getValue().toString());
                    Log.d("Longitude"," "+ds.child("Longitude").getValue().toString());
                    basement = (ds.child("basement").getValue().toString());
                    Log.d("basement"," "+ds.child("basement").getValue().toString());
                    descruption = (ds.child("descruption").getValue().toString());
                    Log.d("descruption"," "+ds.child("descruption").getValue().toString());
                    floors = (ds.child("floors").getValue().toString());
                    Log.d("floors"," "+ds.child("floors").getValue().toString());
                    houseId = (ds.child("houseId").getValue().toString());
                    Log.d("houseId"," "+ds.child("houseId").getValue().toString());
                    location = (ds.child("location").getValue().toString());
                    Log.d("location"," "+ds.child("location").getValue().toString());
                    houseType = (ds.child("houseType").getValue().toString());
                    Log.d("houseType"," "+ds.child("houseType").getValue().toString());
                    houseName = (ds.child("houseName").getValue().toString());
                    Log.d("houseName"," "+ds.child("houseName").getValue().toString());
                    masterRooms = (ds.child("masterRooms").getValue().toString());
                    Log.d("masterRooms"," "+ds.child("masterRooms").getValue().toString());
                    priority = (ds.child("priority").getValue().toString());
                    Log.d("priority"," "+ds.child("priority").getValue().toString());
                    privateSwimmingPool = (ds.child("privateSwimmingPool").getValue().toString());
                    Log.d("privateSwimmingPool"," "+ds.child("privateSwimmingPool").getValue().toString());
                    rentrules = (ds.child("rentrules").getValue().toString());
                    Log.d("Rentrules"," "+ds.child("rentrules").getValue().toString());
                    rooms = (ds.child("rooms").getValue().toString());
                    Log.d("rooms"," "+ds.child("rooms").getValue().toString());
                    salon = (ds.child("salon").getValue().toString());
                    Log.d("Salon"," "+ds.child("salon").getValue().toString());
                    toilet = (ds.child("toilets").getValue().toString());
                    Log.d("toilets"," "+ds.child("toilets").getValue().toString());
                    typeOfPeopleAllowedToRent = (ds.child("typeOfPeopleAllowedToRent").getValue().toString());
                    Log.d("TypeOfPeopleAllo"," "+ds.child("typeOfPeopleAllowedToRent").getValue().toString());
                    whichLineOnSea = (ds.child("whichLine").getValue().toString());
                    Log.d("whichLine"," "+ds.child("whichLine").getValue().toString());

                    String feat = ds.child("featuers").getValue().toString();
                    Log.d("featuers"," "+feat);
                    features = feat.substring(1,feat.length()-1);
                    Log.d("featuers"," "+features);
                    arrayFeatures = features.split(",");
                    for (int i = 0; i < arrayFeatures.length; i++) {
                        String arr1 = arrayFeatures[i];
                        Log.d("features"," "+i+" "+arr1);
                    }

                    String photo = ds.child("photosGalaryURLs").getValue().toString();
                    Log.d("photo"," "+photo);
                    photoGalleries = photo.substring(1,photo.length()-1);
                    Log.d("photoGalleries"," "+photoGalleries);
                    arrayPhotoGalleries = photoGalleries.split(",");

                    for (int i = 0; i < arrayPhotoGalleries.length; i++) {
                        String arr1 = arrayPhotoGalleries[i];
                        Log.d("photoGalleries"," "+i+" "+arr1);
                    }
                    boolean test = true;
                    if(!tab[1].equals("")){
                        if (!tab[1].equals(houseType))
                            test = false;
                    }
                    if(!tab[0].equals("")){
                        if (!tab[0].equals(typeOfPeopleAllowedToRent))
                             test = false;
                    }
                    if(!tab[2].equals("")){
                        if (!tab[2].equals(whichLineOnSea))
                            test = false;
                    }
                    if(!tab[3].equals("")){
                        if (!tab[3].equals(privateSwimmingPool))
                            test = false;
                    }
                    if (!tab[4].equals("0")){
                        if(Integer.parseInt(rooms)<Integer.parseInt(tab[4]))
                            test=false;
                    }
                    if (test) {
                        hotelsDatas.add(new Home_data(Bool, HouseNumber, Latitude, Longitude, basement, descruption,
                                floors, houseId, houseName, houseType, location, masterRooms, priority, privateSwimmingPool,
                                rentrules, rooms, salon, toilet, typeOfPeopleAllowedToRent, whichLineOnSea, arrayFeatures, arrayPhotoGalleries, arrayPhotoGalleries[0], photoGalleries, features, 1));
                    }

                }

                lstItems.setAdapter(new HotelsItemsListAdapter(hotelsDatas,Search.this));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        //Click Listner Event of ListView
        lstItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Search.this,
                        Detail_home.class);
                Detail_home.hotelsData = hotelsDatas.get(i);
                intent.putExtra("KEY_FROM","fromMain");
                startActivity(intent);
            }
        });

    }

}
