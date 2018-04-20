package com.yacob.weekend;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.yacob.weekend.data.DBHelper;
import com.yacob.weekend.structure.Home_data;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;


public class Detail_home extends AppCompatActivity implements OnMapReadyCallback
{
    SliderLayout mDemoSlider;
    Button lbl_categoryDetail_views;
    HashMap<String, String> url_maps = new HashMap<String, String>();
    ArrayList<String> arr_images = new ArrayList<String>();
    ArrayList<String> arr_titles = new ArrayList<String>();
    String title = "",str  = "";
    CollapsingToolbarLayout collapsingToolbar;
    private GoogleMap mMap;
    public static Home_data hotelsData;
    TextView lblDetailHouseName,lblDetailLocationName,lblDetailDesc,lbl_categoryDetail_fav,from,to;
    ImageView imgShare,imgFav;
    String arr,fromdate,todate,price;
    int num;
    String[] imgData;
    private FirebaseAnalytics analytics;
    FirebaseDatabase database;
    DatabaseReference mRootRef,mchild;
    String Bool,HouseNumber,Latitude,Longitude,basement,descruption,floors,houseId,houseName,houseType,location,masterRooms,
            priority,privateSwimmingPool,rentrules,rooms,salon,toilet,typeOfPeopleAllowedToRent,whichLineOnSea;
    private String features;
    private String photoGalleries;;
    private String[] arrayFeatures;
    private String[] arrayPhotoGalleries;
    DBHelper dbHelper;
    ImageView d_img1,d_img2,d_img3;
    RelativeLayout layoutAvailabilityCalender,layoutSafetyInstructions,layoutTermsAndConditions;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_home);

        //Initialization of Map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Initialization of Toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.arrow_left_big);
        upArrow.setColorFilter(getResources().getColor(R.color.wallet_highlighted_text_holo_light), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imgShare = (ImageView)findViewById(R.id.imgShare);
        imgFav = (ImageView)findViewById(R.id.imgFav);
        lblDetailHouseName = (TextView)findViewById(R.id.lblDetailHouseName);
        lblDetailLocationName = (TextView)findViewById(R.id.lblDetailLocationName);
        lblDetailDesc = (TextView)findViewById(R.id.lblDetailDesc);
        mDemoSlider = (SliderLayout) findViewById(R.id.sliderheader);
        lbl_categoryDetail_views = findViewById(R.id.lbl_categoryDetail_views);
        lbl_categoryDetail_fav = (TextView)findViewById(R.id.lbl_categoryDetail_fav);
        from = (TextView)findViewById(R.id.lblCheckOutTime);
        to = (TextView)findViewById(R.id.lblCheckInTime);
        d_img1 = findViewById(R.id.d_img1);
        d_img2 = findViewById(R.id.d_img2);
        d_img3 = findViewById(R.id.d_img3);
        layoutAvailabilityCalender = findViewById(R.id.layoutAvailabilityCalender);
        layoutTermsAndConditions= findViewById(R.id.layoutTermsAndConditions);
        layoutSafetyInstructions= findViewById(R.id.layoutSafetyInstructions);
        //d_img4 = findViewById(R.id.d_img4);
        //d_img5 = findViewById(R.id.d_img5);
      //  d_img6 = findViewById(R.id.d_img6);

        // initializing CollapsingToolbar
        initCollapsingToolbar();

        //checking from where it is coming, either from home screen or favourite screen
        String fromWhere = getIntent().getStringExtra("KEY_FROM");
        Log.d("KEY_FROM",""+fromWhere);


        if (fromWhere.equals("fromFavourite"))
        {

            String str = hotelsData.getStrPhotoGalleries();
            imgData = str.split(",");
        }else if(fromWhere.equals("fromCheck")) {
            // check reservation
            Intent intent = getIntent();
            fromdate = intent.getStringExtra("fromdate");
            todate = intent.getStringExtra("todate");
            from.setText(fromdate);
            to.setText(todate);
            if (intent.getStringExtra("fromdate") != "") {
                lbl_categoryDetail_views.setText("احجز الان");
                lbl_categoryDetail_views.setBackgroundResource(R.drawable.button_blue);
                num = intent.getIntExtra("num", 0);
                price = intent.getStringExtra("price");
                Double prix= Double.valueOf(price);
                lbl_categoryDetail_fav.setText("لقد حجزت " + String.valueOf(num-1) + " يوم بثمن\n" + new DecimalFormat("###.##").format(prix) + "د.ك");


            }
            String str = hotelsData.getStrPhotoGalleries();
            imgData = str.split(",");

        }else if(fromWhere.equals("fromNotify")){
            gethousedata(getIntent().getStringExtra("houseid"));
            String str = hotelsData.getStrPhotoGalleries();
            imgData = str.split(",");

        }
        else {
            String str = hotelsData.getStrPhotoGalleries();
            imgData = str.split(",");
        }


        //Getting Multiple Photo Images Data for Image Slider

        for (int k = 0; k < imgData.length ; k++)
        {
            arr_images.add(imgData[k]);
            Log.d("ARR_IAMGES",""+imgData[k]);
        }
        String aaa= " ";
        for (int i = 0; i < imgData.length; i++)
        {
            aaa += " ";
            arr_titles.add(aaa);
        }

        Log.d("SLIDER_IMAGES_SIZE",""+imgData.length);
        for (int i = 0; i < imgData.length; i++)
        {

            str = arr_images.get(i);
            title =  arr_titles.get(i);


            url_maps.put(title, str);
        }

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
       /* mDemoSlider
                .setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);*/
        // mDemoSlider.setCustomIndicator(PagerIndicator.Custom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(6000);
        /*for (String name : url_maps.keySet()) {
            // DefaultSliderView defaultSliderView = new
            // DefaultSliderView(this);
            // defaultSliderView.image(url_maps.get(name))
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView.description(name).image(url_maps.get(name));
                    // .setScaleType(BaseSliderView.ScaleType.CenterInside)
            // add your extra information
             //textSliderView.getBundle().putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }*/
        for (final String name : url_maps.keySet()) {
            //TextSliderView textSliderView = new TextSliderView(this);
            DefaultSliderView textSliderView = new DefaultSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView baseSliderView) {

                            Dialog dialog = new Dialog(Detail_home.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.dialog_image);
                            Window dialogWindow = dialog.getWindow();
                            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                            lp.width = 1000; // Width
                            lp.height = 1000; // Height
                            dialogWindow.setAttributes(lp);

                            /*PhotoView image =  dialog.findViewById(R.id.photo_view);

                            Picasso.with(Detail_home.this)
                                    .load(url_maps.get(name))
                                    .into(image);*/
                            // dialog img slider
                            SliderLayout dialogSlider =dialog.findViewById(R.id.sliderimg);
                            dialogSlider.stopAutoCycle();
                            dialogSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
                            dialogSlider.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
                            dialogSlider.setCustomAnimation(new DescriptionAnimation());
                            for (final String name : url_maps.keySet()) {
                                //TextSliderView textSliderView = new TextSliderView(this);
                                DefaultSliderView SliderView = new DefaultSliderView(getBaseContext());
                                // initialize a SliderLayout
                                SliderView
                                        .description(name)
                                        .image(url_maps.get(name))
                                        .setScaleType(BaseSliderView.ScaleType.Fit);
                                SliderView.bundle(new Bundle());
                                SliderView.getBundle()
                                        .putString("extra", name);

                                dialogSlider.addSlider(SliderView);
                            }
                            dialogSlider.setCurrentPosition(mDemoSlider.getCurrentPosition());
                            dialog.getWindow().setBackgroundDrawable(null);
                            dialog.show();

                        }
                    });

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mDemoSlider.addSlider(textSliderView);

        }
        //Displaying HouseName,Location and Description
        dbHelper =  new DBHelper(this);
        lblDetailHouseName.setText(hotelsData.getHouseName());
        lblDetailLocationName.setText(hotelsData.getLocation());
        lblDetailDesc.setText(hotelsData.getDescruption());
        layoutAvailabilityCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lbl_categoryDetail_views.getText()=="احجز الان"){
                    Intent intent = new Intent(Detail_home.this, Reservation.class);
                    intent.putExtra("fromdate",fromdate);
                    intent.putExtra("todate",todate);
                    intent.putExtra("num",num);
                    intent.putExtra("price",price);
                    Reservation.hotelsData=hotelsData;
                    startActivity(intent);


                }else {
                    Intent intent = new Intent(Detail_home.this, Check_date.class);
                    Check_date.hotelsData = hotelsData;
                    startActivity(intent);

                }
            }
        });
        layoutSafetyInstructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detail_home.this, Conditions.class);
                intent.putExtra("from","safety");
                startActivity(intent);
            }
        });
        layoutTermsAndConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detail_home.this, Conditions.class);
                intent.putExtra("from","conditions");
                startActivity(intent);
            }
        });
        long value = dbHelper.insertFavHotelsData(hotelsData);
        if (value == -1) {
            //Toast.makeText(getContext(), "Already Saved To Favourite", Toast.LENGTH_SHORT).show();
            Glide.with(Detail_home.this).load(R.drawable.heartr).into(imgFav);

        } else {
            Glide.with(Detail_home.this).load(R.drawable.heartg).into(imgFav);
            boolean testdelete= dbHelper.deleteSingleFavHotelData(hotelsData);
        }
        // img type and feature
        switch (hotelsData.getTypeOfPeopleAllowedToRent().toString()){
            case "family":
                Glide.with(Detail_home.this).load(R.drawable.family_b).into(d_img1);
                d_img1.setVisibility(View.VISIBLE);
                break;
            case "mix":
                Glide.with(Detail_home.this).load(R.drawable.mix_b).into(d_img1);
                d_img1.setVisibility(View.VISIBLE);
                break;
            case "freind":
                Glide.with(Detail_home.this).load(R.drawable.freinds_b).into(d_img1);
                d_img1.setVisibility(View.VISIBLE);
                break;
        }
        switch (hotelsData.getHouseType().toString()){
            case "blocks":
                Glide.with(Detail_home.this).load(R.drawable.bloc).into(d_img2);
                d_img2.setVisibility(View.VISIBLE);
                break;
            case "privateHouse":
                Glide.with(Detail_home.this).load(R.drawable.private_b).into(d_img2);
                d_img2.setVisibility(View.VISIBLE);
                break;
            case "apartment":
                Glide.with(Detail_home.this).load(R.drawable.apartment_b).into(d_img2);
                d_img2.setVisibility(View.VISIBLE);
                break;
        }
        switch (hotelsData.getWhichLine().toString()){
            case "firstLine":
                Glide.with(Detail_home.this).load(R.drawable.first_40).into(d_img3);
                d_img3.setVisibility(View.VISIBLE);
                break;
            case "secondLine":
                Glide.with(Detail_home.this).load(R.drawable.second_40).into(d_img3);
                d_img3.setVisibility(View.VISIBLE);
                break;
            case "onSea":
                Glide.with(Detail_home.this).load(R.drawable.sea_40).into(d_img3);
                d_img3.setVisibility(View.VISIBLE);
                break;
        }
        LinearLayout layout_feature =  findViewById(R.id.layout_features);
        /*ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.baby);
        ImageView imageView2 = new ImageView(this);
        imageView2.setImageResource(R.drawable.mar);
        ImageView imageView3 = new ImageView(this);
        imageView3.setImageResource(R.drawable.mar);
        ImageView imageView4 = new ImageView(this);
        imageView4.setImageResource(R.drawable.mar);*/
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(150,200
               // RelativeLayout.LayoutParams.WRAP_CONTENT,
              //  RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        /*layout_feature.addView(imageView, layoutParams);
        layout_feature.addView(imageView1, layoutParams);
        layout_feature.addView(imageView2, layoutParams);
        layout_feature.addView(imageView3, layoutParams);
        layout_feature.addView(imageView4, layoutParams);*/
       for (int i=0;i<hotelsData.getFeatures().length;i++){
           char test =hotelsData.getFeatures()[i].charAt(0);
           switch (hotelsData.getFeatures()[i]){
               case "tv":
                   ImageView imageView = new ImageView(this);
                   imageView.setImageResource(R.drawable.tv);
                   layout_feature.addView(imageView, layoutParams);
                   break;
               case " babyFoot":
                   ImageView imageView1 = new ImageView(this);
                   imageView1.setImageResource(R.drawable.baby);
                   layout_feature.addView(imageView1, layoutParams);
                   break;
               case " videoGames":
                   ImageView imageView2 = new ImageView(this);
                   imageView2.setImageResource(R.drawable.mar);
                   layout_feature.addView(imageView2, layoutParams);
                   break;
               case " swing":
                   ImageView imageView3 = new ImageView(this);
                   imageView3.setImageResource(R.drawable.swing);
                   layout_feature.addView(imageView3, layoutParams);
                   break;
               case "swing":
                   ImageView imageView9 = new ImageView(this);
                   imageView9.setImageResource(R.drawable.swing);
                   layout_feature.addView(imageView9, layoutParams);
                   break;
               case " privateSwimingPool":
                   ImageView imageView4 = new ImageView(this);
                   imageView4.setImageResource(R.drawable.swimming);
                   layout_feature.addView(imageView4, layoutParams);
                   break;
               case " kayak":
                   ImageView imageView5 = new ImageView(this);
                   imageView5.setImageResource(R.drawable.canoe);
                   layout_feature.addView(imageView5, layoutParams);
                   break;
               case " volleyball":
                   ImageView imageView6 = new ImageView(this);
                   imageView6.setImageResource(R.drawable.volleyball);
                   layout_feature.addView(imageView6, layoutParams);
                   break;
               case " Trampolim":
                   ImageView imageView7 = new ImageView(this);
                   imageView7.setImageResource(R.drawable.trampoline);
                   layout_feature.addView(imageView7, layoutParams);
                   break;
               case "playGround":
                   ImageView imageView8 = new ImageView(this);
                   imageView8.setImageResource(R.drawable.playground);
                   layout_feature.addView(imageView8, layoutParams);
                   break;
           }

       }


        //Share Image Click Listner
        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent2 = new Intent();
                intent2.setAction(Intent.ACTION_SEND);
                intent2.setType("text/plain");
                intent2.putExtra(Intent.EXTRA_TEXT, "Weekend App\n"+"HouseName "+hotelsData.getHouseName()+"Location "+hotelsData.getLocation());
                startActivity(Intent.createChooser(intent2, "Share via"));

                Intent intent = new Intent(ItemDetailsActivity.this,SharingPageActivity.class);
                SharingPageActivity.hotelsData = hotelsData;
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right,R.anim.push_out_left);*/
                shareShortDynamicLink(view);
            }
        });
        //Favourite Image Click Listner
        imgFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long value = dbHelper.insertFavHotelsData(hotelsData);
                if (value == -1) {
                    Glide.with(Detail_home.this).load(R.drawable.heartg).into(imgFav);
                    boolean testdelete= dbHelper.deleteSingleFavHotelData(hotelsData);
                    Toast.makeText(Detail_home.this, "تم فسخها", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Detail_home.this, "تم حفظها", Toast.LENGTH_SHORT).show();
                    Glide.with(Detail_home.this).load(R.drawable.heartr).into(imgFav);
                }

            }
        });
        //Check Dates Button Click Listner
        lbl_categoryDetail_views.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lbl_categoryDetail_views.getText()=="احجز الان"){
                    Intent intent = new Intent(Detail_home.this, Reservation.class);
                    intent.putExtra("fromdate",fromdate);
                    intent.putExtra("todate",todate);
                    intent.putExtra("num",num);
                    intent.putExtra("price",price);
                    Reservation.hotelsData=hotelsData;
                    startActivity(intent);


                }else {
                    Intent intent = new Intent(Detail_home.this, Check_date.class);
                    Check_date.hotelsData = hotelsData;
                    startActivity(intent);

                }

            }
        });

    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the txtPostTitle when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle("");
                    final Drawable upArrow = getResources().getDrawable(R.drawable.arrow_left_big);
                    upArrow.setColorFilter(getResources().getColor(R.color.wallet_highlighted_text_holo_light), PorterDuff.Mode.SRC_ATOP);
                    getSupportActionBar().setHomeAsUpIndicator(upArrow);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle("");

                    final Drawable upArrow = getResources().getDrawable(R.drawable.arrow_left_big);
                    upArrow.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
                    getSupportActionBar().setHomeAsUpIndicator(upArrow);
                    isShow = false;
                }
            }
        });

    }
    @Override
    public void onBackPressed() {
       /* Intent intent = new Intent(Detail_home.this, MainActivity.class);
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
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    public void shareLongDynamicLink(View view) {
        /*Intent intent = new Intent();
        String msg =  buildDynamicLink();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent,"share"));*/
        shareShortDynamicLink(view);
    }

    private String buildDynamicLink(/*String link, String description, String titleSocial, String source*/) {
        //more info at https://firebase.google.com/docs/dynamic-links/create-manually
        /*Uri uri= Uri.parse("https://firebasestorage.googleapis.com/v0/b/no5tha-17084.appspot.com/o/No5tha%2Flogo-83.5%402x.png?alt=media&token=25bfc985-1dda-46aa-933b-397c7eb66683");
        String path = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setDynamicLinkDomain("yr2ks.app.goo.gl")
                .setLink(Uri.parse("https://facebook.com"))
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build()) //com.melardev.tutorialsfirebase
                .setSocialMetaTagParameters(new DynamicLink.SocialMetaTagParameters.Builder().setTitle("Share "+hotelsData.getHouseName()).setDescription(hotelsData.getDescruption()).build())
                .setGoogleAnalyticsParameters(new DynamicLink.GoogleAnalyticsParameters.Builder().setSource("AndroidApp").build())
                .buildDynamicLink().getUri().toString();
        return path;*/

        return "https://yr2ks.app.goo.gl/?"+
                "link=" +
                "https://www.weekendq8.com/house/"+hotelsData.getHouseId()+
                "&apn=com.yacob.weekend" +
                "&st=" + /*titleSocial*/
                "Weekend" +
                "&sd=" + /*description*/
                " renting luxurious houses." +
                "&si=https://firebasestorage.googleapis.com/v0/b/no5tha-17084.appspot.com/o/No5tha%2Flogo-83.5%402x.png?alt=media&token=25bfc985-1dda-46aa-933b-397c7eb66683"+
                "&isi=1249814392"+
                "&ibi=com.yacob.weekend";
    }
    public void shareShortDynamicLink(View view) {
        Task<ShortDynamicLink> createLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLongLink(Uri.parse(buildDynamicLink()))
                .buildShortDynamicLink()
                .addOnCompleteListener(this, new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            // Short link created
                            Uri shortLink = task.getResult().getShortLink();
                            Uri flowchartLink = task.getResult().getPreviewLink(); //flowchart link is a debugging URL

                            Log.d("here", shortLink.toString());
                            Log.d("here", flowchartLink.toString());
                            Intent intent = new Intent();
                            String msg = "weekend " + shortLink.toString();
                            intent.setAction(Intent.ACTION_SEND);
                            intent.putExtra(Intent.EXTRA_TEXT, msg);
                            intent.setType("text/plain");
                            startActivity(Intent.createChooser(intent,"share"));

                        } else {
                            // Error
                            Log.d("here","error");
                        }
                    }
                });
    }
    private void gethousedata(String houseid) {
        database = FirebaseDatabase.getInstance();
        mRootRef = database.getReference("/No5tha/housesData/public/HouseL9PG7KP3Y7i");
        //mchild = mRootRef.child(houseid+"/");
        //lstItems.setAdapter(new HotelsItemsListAdapter(SplashActivity.hotelsDatas,getActivity()));

        //Retriving data from Firebase Path
        mRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot ds) {


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

                hotelsData = new Home_data(Bool,HouseNumber,Latitude,Longitude,basement,descruption,
                        floors,houseId,houseName,houseType,location,masterRooms,priority,privateSwimmingPool,
                        rentrules,rooms,salon,toilet,typeOfPeopleAllowedToRent,whichLineOnSea,arrayFeatures,arrayPhotoGalleries,arrayPhotoGalleries[0],photoGalleries,features,1);


            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
