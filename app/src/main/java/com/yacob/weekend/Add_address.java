package com.yacob.weekend;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.yacob.weekend.structure.SharedData;

import java.io.IOException;
import java.util.List;
import java.util.Locale;



/**
 * Created by Pravin on 17-11-2017.
 */

public class Add_address extends AppCompatActivity implements OnMapReadyCallback
{
    Toolbar mToolbar;
    Button btnSaveAddress;
    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    LinearLayout layoutMap;
    LatLng point;
    Geocoder geocoder;
    private Marker marker;
    MarkerOptions markerOptions;
    String addressPincode = "";
    String countryName = "";
    String addressState = "";
    String addressLine0 = "";
    String addressLine1 = "";
    String addressCity = "";
    double latitude = 0.0,longitude = 0.0;
    EditText txtRegion,txtCountry,txtStreet,txtBlock,txtBuilding,txtAvenue,txtFloor,txtHouseNumber;
    String Region,Country,Street,Block,Building,Avenue,Floor,HouseNumber,uID;
    private DatabaseReference mDatabase;
    SharedData sharedData;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Address");
        //mToolbar.setLogo(R.drawable.icon);
        mToolbar.setNavigationOnClickListener
                (new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();

                    }
                });

        btnSaveAddress = (Button)findViewById(R.id.btnSaveAddress);
        layoutMap = (LinearLayout)findViewById(R.id.layoutMap);
        geocoder = new Geocoder(this, Locale.getDefault());
        //Initialization of Map
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapAddress);
        mapFragment.getMapAsync(this);

        txtRegion = (EditText)findViewById(R.id.txtRegion);
        txtCountry = (EditText)findViewById(R.id.txtCountry);
        txtStreet = (EditText)findViewById(R.id.txtStreet);
        txtBlock = (EditText)findViewById(R.id.txtBlock);
        txtBuilding = (EditText)findViewById(R.id.txtBuilding);
        txtAvenue = (EditText)findViewById(R.id.txtAvenue);
        txtFloor = (EditText)findViewById(R.id.txtFloor);
        txtHouseNumber = (EditText)findViewById(R.id.txtHouseNumber);

        sharedData = new SharedData(getApplicationContext());
        uID = sharedData.getStringData("KEY_UID");
       /* Log.d("KEY_UID",""+uID);
        mDatabase = FirebaseDatabase.getInstance().getReference("No5tha/userProfile/"+uID+"/");

        //String lat = getIntent().getStringExtra("KEY_LAT");
        String lat ="28.70899072135865";
        if (!lat.equals(""))
        {
            latitude = Double.valueOf(lat);
        }

        //String log = getIntent().getStringExtra("KEY_LONG");
        String log ="";
        if (!log.equals("48.35203170776367"))
        {
            longitude = Double.valueOf(log);
        }
        Building = getIntent().getStringExtra("KEY_APARTMENT");
        Log.d("KEY_APARTMENT",""+Building);
        Avenue = getIntent().getStringExtra("KEY_AVENUE");
        Log.d("KEY_AVENUE",""+Avenue);
        Block = getIntent().getStringExtra("KEY_BLOCK");
        Log.d("KEY_BLOCK",""+Block);
        Country = getIntent().getStringExtra("KEY_COUNTRY");
        Log.d("KEY_COUNTRY",""+Country);
        Floor = getIntent().getStringExtra("KEY_FLOOR");
        Log.d("KEY_FLOOR",""+Floor);
        HouseNumber = getIntent().getStringExtra("KEY_HOUSE_NUMBER");
        Log.d("KEY_HOUSE_NUMBER",""+HouseNumber);
        Street = getIntent().getStringExtra("KEY_STREET");
        Log.d("KEY_STREET",""+Street);
        Region = getIntent().getStringExtra("KEY_SUBRURB");
        Log.d("KEY_SUBRURB",""+Region);

        txtRegion.setText(Region);
        txtCountry.setText(Country);
        txtStreet.setText(Street);
        txtBlock.setText(Block);
        txtBuilding.setText(Building);
        txtAvenue.setText(Avenue);
        txtFloor.setText(Floor);
        txtHouseNumber.setText(HouseNumber);*/

        btnSaveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Region = txtRegion.getText().toString().trim();
                Country = txtCountry.getText().toString().trim();
                Street = txtStreet.getText().toString().trim();
                Block = txtBlock.getText().toString().trim();
                Building = txtBuilding.getText().toString().trim();
                Avenue = txtAvenue.getText().toString().trim();
                Floor = txtFloor.getText().toString().trim();
                HouseNumber = txtHouseNumber.getText().toString().trim();

                mDatabase.child("address").child("Latitude").setValue(latitude);
                mDatabase.child("address").child("Longitude").setValue(longitude);
                mDatabase.child("address").child("apartment").setValue(Building);
                mDatabase.child("address").child("avenue").setValue(Avenue);
                mDatabase.child("address").child("block").setValue(Block);
                mDatabase.child("address").child("country").setValue(Country);
                mDatabase.child("address").child("floor").setValue(Floor);
                mDatabase.child("address").child("houseNumber").setValue(HouseNumber);
                mDatabase.child("address").child("street").setValue(Street);
                mDatabase.child("address").child("surburb").setValue(Region);
            }
        });
    }

    //Displaying Marker on Map
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng sydney = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.getUiSettings().setZoomControlsEnabled(true);


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                /*ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();
                params.height = 900;
                mapFragment.getView().setLayoutParams(params);*/

                //save current location
                point = latLng;
                Log.d("KEY_LATLNG",""+point);

                // Clears the previously touched position
                mMap.clear();

                // Animating to the touched position
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Creating a marker
                markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Placing a marker on the touched position
                mMap.addMarker(markerOptions);

                // Adding Marker on the touched location with address
                new ReverseGeocodingTask(getBaseContext()).execute(latLng);

            }
        });
    }

    private class ReverseGeocodingTask extends AsyncTask<LatLng, Void, String> {
        Context mContext;

        public ReverseGeocodingTask(Context context) {
            super();
            mContext = context;
        }

        // Finding address using reverse geocoding
        @Override
        protected String doInBackground(LatLng... params) {
            Geocoder geocoder = new Geocoder(mContext);
            latitude = params[0].latitude;
            longitude = params[0].longitude;

            List<Address> addresses = null;
            String addressText = "";


            try {

                addresses = geocoder.getFromLocation(latitude, longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);

                addressLine0 = address.getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                addressLine1 = address.getAddressLine(1);
                addressCity = address.getLocality();
                addressState = address.getAdminArea();
                countryName = address.getCountryName();
                String pin = address.getPostalCode();

                if (pin != null)
                {
                    addressPincode = pin;
                }

                Log.d("addressText", "address1 " + addressLine0 + addressLine1);
                Log.d("addressText","city "+addressCity);
                Log.d("addressText", "state " + addressState);
                Log.d("addressText", "postalCode " + addressPincode);
                Log.d("addressText", "country " + countryName);
                Log.d("addressText","SubAdminArea "+address.getSubAdminArea());
            }

            return addressText;
        }

        @Override
        protected void onPostExecute(String addressText) {
            // Setting the title for the marker.
            // This will be displayed on taping the marker
            markerOptions.title(addressText);

            txtRegion.setText(addressState);
            txtCountry.setText(countryName);
            txtStreet.setText("");
            txtBlock.setText("");
            txtBuilding.setText("");
            txtAvenue.setText("");
            txtFloor.setText("");
            txtHouseNumber.setText("");


            // Placing a marker on the touched position
            mMap.addMarker(markerOptions);

        }
    }

}

