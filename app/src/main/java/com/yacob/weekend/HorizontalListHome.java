package com.yacob.weekend;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yacob.weekend.adapter.HorizontalListAdapter;
import com.yacob.weekend.adapter.HorizontalListView;
import com.yacob.weekend.structure.Home_data;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Pravin on 27-10-2017.
 */

public class HorizontalListHome extends AppCompatActivity implements OnMapReadyCallback
{
    Toolbar mToolbar;
    HorizontalListView lstItems;
    ArrayList<Home_data> hotelsDatas = new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference mRootRef,mchild;
    String Bool,HouseNumber,Latitude,Longitude,basement,descruption,floors,houseId,houseName,houseType,location,masterRooms,
            priority,privateSwimmingPool,rentrules,rooms,salon,toilet,typeOfPeopleAllowedToRent,whichLineOnSea;
    private String features;
    private String photoGalleries;;
    private String[] arrayFeatures;
    private String[] arrayPhotoGalleries;

    private GoogleMap mMap;
    LatLngBounds.Builder builder;
    CameraUpdate cu;
    SupportMapFragment mapFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_list_home);
        freeMemory();

        //Initialization of Map
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.horizontalMap);

        lstItems = (HorizontalListView)findViewById(R.id.lstHotelsHoriz);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Hotels");
        //mToolbar.setLogo(R.drawable.icon);
        mToolbar.setNavigationOnClickListener
                (new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();

                    }
                });

        //Initializing Firebase Database
        database = FirebaseDatabase.getInstance();
        mRootRef = database.getReference("/No5tha/housesData");
        mchild = mRootRef.child("public");

        //Retriving data from Firebase Path
            hotelsDatas.clear();
            mchild.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                    for (com.google.firebase.database.DataSnapshot ds : dataSnapshot.getChildren()) {

                        /*HotelsData hotelsData = ds.getValue(HotelsData.class);
                        Log.d("Firebase Database", "Bool: " + hotelsData.getBool() + ", HouseNumber " + hotelsData.getHouseNumber());
                        *///hotelsDatas.add(hotelsData);
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

                        hotelsDatas.add(new Home_data(Bool,HouseNumber,Latitude,Longitude,basement,descruption,
                                floors,houseId,houseName,houseType,location,masterRooms,priority,privateSwimmingPool,
                                rentrules,rooms,salon,toilet,typeOfPeopleAllowedToRent,whichLineOnSea,arrayFeatures,arrayPhotoGalleries,arrayPhotoGalleries[0],photoGalleries,features,0));

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });




        lstItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(HorizontalListHome.this,
                        Detail_home.class);
                Detail_home.hotelsData = hotelsDatas.get(i);
                intent.putExtra("KEY_FROM","fromMain");
                startActivity(intent);
            }
        });

        lstItems.setAdapter(new HorizontalListAdapter(hotelsDatas,getApplicationContext()));
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(HorizontalListHome.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        List<Marker> markersList = new ArrayList<Marker>();

        for (int i = 0; i < hotelsDatas.size(); i++) {
            double lat = Double.valueOf(hotelsDatas.get(i).getLatitude());
            double longi = Double.valueOf(hotelsDatas.get(i).getLongitude());
            // Add a marker in Sydney and move the camera
            LatLng sydney = new LatLng(lat, longi);
            mMap.addMarker(new MarkerOptions().position(sydney).title(hotelsDatas.get(i).getHouseName()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            /**Put all the markers into arraylist*/
            markersList.add(mMap.addMarker(new MarkerOptions().position(sydney).title(hotelsDatas.get(i).getHouseName())));
        }

        if (hotelsDatas.size() != 0)
        {
            /**create for loop for get the latLngbuilder from the marker list*/
            builder = new LatLngBounds.Builder();
            for (Marker m : markersList) {
                builder.include(m.getPosition());
            }
            /**initialize the padding for map boundary*/
            int padding = 50;
            /**create the bounds from latlngBuilder to set into map camera*/
            LatLngBounds bounds = builder.build();
            /**create the camera with bounds and padding to set into map*/
            cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            /**call the map call back to know map is loaded or not*/
            mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    /**set animated zoom camera into map*/
                    mMap.animateCamera(cu);

                }
            });
        }

    }

    public void onPressSearch(View view)
    {
        final Dialog dialog = new Dialog(HorizontalListHome.this);
        dialog.setContentView(R.layout.search);
        dialog.setTitle("search");
        final String[] t = {"","","","","0"};
        final ImageView img_closedialog = (ImageView)dialog.findViewById(R.id.img_closedialog);
        final ImageView family = (ImageView) dialog.findViewById(R.id.family);
       // final ImageView mixed = (ImageView) dialog.findViewById(R.id.mixed);
        final ImageView freind = (ImageView) dialog.findViewById(R.id.freind);
        final ImageView house = (ImageView) dialog.findViewById(R.id.house);
        final ImageView building = (ImageView) dialog.findViewById(R.id.building);
        final ImageView bysea = (ImageView) dialog.findViewById(R.id.bysea);
        final ImageView pool = (ImageView) dialog.findViewById(R.id.pool);
        final ImageView line1 = (ImageView) dialog.findViewById(R.id.line1);
        final ImageView line2 = (ImageView) dialog.findViewById(R.id.line2);
        final ImageView line3 = (ImageView) dialog.findViewById(R.id.line3);
       // final ImageView moins = (ImageView) dialog.findViewById(R.id.moins);
        final ImageView plus = (ImageView) dialog.findViewById(R.id.plus);

        final TextView type = (TextView) dialog.findViewById(R.id.type);
        final TextView housetype = (TextView) dialog.findViewById(R.id.housetype);
        final TextView whichline = (TextView) dialog.findViewById(R.id.whichline);
        final TextView nbroom = (TextView) dialog.findViewById(R.id.nbroom);
        final Button search = dialog.findViewById(R.id.search);
        final Button cancel = dialog.findViewById(R.id.cancel);


        img_closedialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable highlight = getResources().getDrawable( R.drawable.highlight);
                family.setBackground(highlight);
                freind.setBackground(null);
               // mixed.setBackground(null);
                type.setText("Family");
                t[0] ="family";
            }
        });
       /* mixed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable highlight = getResources().getDrawable( R.drawable.highlight);
                mixed.setBackground(highlight);
                freind.setBackground(null);
                family.setBackground(null);
                type.setText("Mixed");
                t[0] ="mix";
            }
        });*/
        freind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable highlight = getResources().getDrawable( R.drawable.highlight);
                freind.setBackground(highlight);
                family.setBackground(null);
                //mixed.setBackground(null);
                type.setText("Freinds");
                t[0] ="freind";
            }
        });
        house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable highlight = getResources().getDrawable( R.drawable.highlight);
                house.setBackground(highlight);
                building.setBackground(null);
                bysea.setBackground(null);
                housetype.setText("apartment");
                t[1] ="blocks";
            }
        });
        building.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable highlight = getResources().getDrawable( R.drawable.highlight);
                building.setBackground(highlight);
                house.setBackground(null);
                bysea.setBackground(null);
                housetype.setText("blocks");
                t[1] ="blocks";
            }
        });
        bysea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable highlight = getResources().getDrawable( R.drawable.highlight);
                bysea.setBackground(highlight);
                house.setBackground(null);
                building.setBackground(null);
                housetype.setText("Private House");
                t[1] ="privateHouse";
            }
        });
        line1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable highlight = getResources().getDrawable( R.drawable.highlight);
                line1.setBackground(highlight);
                line2.setBackground(null);
                line3.setBackground(null);
                whichline.setText("1st Line");
                t[2] ="firstLine";
            }
        });
        line2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable highlight = getResources().getDrawable( R.drawable.highlight);
                line2.setBackground(highlight);
                line1.setBackground(null);
                line3.setBackground(null);
                whichline.setText("2nd Line");
                t[2] ="secondLine";
            }
        });
        line3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable highlight = getResources().getDrawable( R.drawable.highlight);
                line3.setBackground(highlight);
                line2.setBackground(null);
                line1.setBackground(null);
                whichline.setText("On sea");
                t[2] ="onSea";
            }
        });
        pool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable highlight = getResources().getDrawable( R.drawable.highlight);
                pool.setBackground(highlight);
                t[3] ="true";
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t[4]=String.valueOf(Integer.parseInt(t[4])+1);
                nbroom.setText(t[4]);
            }
        });
        /*moins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!t[4].equals("0")) {
                    t[4]=String.valueOf(Integer.parseInt(t[4])-1);
                    nbroom.setText(t[4]);                        }
            }
        });*/
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HorizontalListHome.this,Search.class);
                intent.putExtra("tab",t);
                startActivity(intent);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    public void onPressMenuList(View view)
    {
        Intent intent = new Intent(HorizontalListHome.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void freeMemory(){
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }
}

