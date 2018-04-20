package com.yacob.weekend;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yacob.weekend.structure.Home_data;

import java.util.ArrayList;


public class Fragment_map extends Fragment implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener {
    View view;
    private ListView lstItems ;
    private ArrayList<Home_data> hotelsDatas =new ArrayList<Home_data>();
    FirebaseDatabase database;
    DatabaseReference mRootRef,mchild;
    String Bool,HouseNumber,Latitude,Longitude,basement,descruption,floors,houseId,houseName,houseType,location,masterRooms,
            priority,privateSwimmingPool,rentrules,rooms,salon,toilet,typeOfPeopleAllowedToRent,whichLineOnSea;
    private String features;
    private String photoGalleries;;
    private String[] arrayFeatures;
    private String[] arrayPhotoGalleries;
    private GoogleMap mgoogleMap;
    private MapView mapView;
   // BitmapDescriptor icon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_map, container, false);
        //Decleration and Initialization of map
        mapView = view.findViewById(R.id.map);
        if (mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
        //Initializing Firebase Database
        database = FirebaseDatabase.getInstance();
        mRootRef = database.getReference("/No5tha/housesData");
        mchild = mRootRef.child("public");
        //lstItems.setAdapter(new HotelsItemsListAdapter(SplashActivity.hotelsDatas,getActivity()));

        //Retriving data from Firebase Path
        hotelsDatas.clear();
        mRootRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

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

                    hotelsDatas.add(new Home_data(Bool,HouseNumber,Latitude,Longitude,basement,descruption,
                            floors,houseId,houseName,houseType,location,masterRooms,priority,privateSwimmingPool,
                            rentrules,rooms,salon,toilet,typeOfPeopleAllowedToRent,whichLineOnSea,arrayFeatures,arrayPhotoGalleries,arrayPhotoGalleries[0],photoGalleries,features,1));

                    Double lat = Double.parseDouble(Latitude);
                    Double lon= Double.parseDouble(Longitude);
                    BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marker);
                    mgoogleMap.addMarker(new MarkerOptions().icon(icon).position(new LatLng(lat,lon)).title(houseName));
                    mgoogleMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) Fragment_map.this);

                }
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
        return view;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(view.getContext());
        mgoogleMap=googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        float zoomLevel = 12.5f; //This goes up to 21
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.64359115373705,48.36895912885666), zoomLevel));

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        //String title=hotelsDatas.get(1).getHouseName();
       /* Intent intent = new Intent(getContext(), Detail_home.class);
        int i=0;

        //get home details
        while (!hotelsDatas.get(i).getHouseName().equals(marker.getTitle())){
            i++;
        }
        Home_data dataModel = hotelsDatas.get(i);
        //get home feature
        String feature ="" ;
        String[] tt = dataModel.getFeatures();
        int j = tt.length;

        for (int k =0;k<j;k++){
            feature = feature +", " +tt[k];
        }
        // get images
        final ArrayList<String> ar = new ArrayList<String>();
        for (int l=0;l<dataModel.getPhotos().length;l++){
            ar.add(dataModel.getPhotos()[l]);

        }
        final String[] tab_detail ={dataModel.getLatitude(),dataModel.getLongitude(),dataModel.getBasement(),dataModel.getDescruption()
                ,feature,dataModel.getFloors(),dataModel.getHouseName(),dataModel.getHouseType(),dataModel.getLocation(),dataModel.getMasterRooms(),
                dataModel.getPrivateSwimmingPool(),dataModel.getRentrules(),dataModel.getRooms(),dataModel.getSalon(),dataModel.getToilets(),
                dataModel.getTypeOfPeopleAllowedToRent(),dataModel.getWhichLine()};
        intent.putExtra("images", ar);
        intent.putExtra("tab",tab_detail);
        getContext().startActivity(intent);*/
        return false;
    }
}

