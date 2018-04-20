package com.yacob.weekend;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.google.firebase.messaging.FirebaseMessaging;
import com.lemonade.widgets.slidesidemenu.SlideSideMenuTransitionLayout;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.special.ResideMenu.ResideMenu;
import com.yacob.weekend.structure.Home_data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// Begin Work Benzarti mejdi 25/02/2018
public class MainActivity extends AppCompatActivity{
    private FragmentTabHost mTabHost;
    private FirebaseAuth auth;
    ResideMenu resideMenu;
    Toolbar mToolbar;
    private SlideSideMenuTransitionLayout mSlideSideMenu;
    TextView lblMenuFavourite,lblMenuShare,lblMenuSignUp,lblMenuSignOut;
    FirebaseDatabase database;
    DatabaseReference mRootRef,mchild,mchild1;
    String Bool,HouseNumber,Latitude,Longitude,basement,descruption,floors,houseId,houseName,houseType,location,masterRooms,
            priority,privateSwimmingPool,rentrules,rooms,salon,toilet,typeOfPeopleAllowedToRent,whichLineOnSea;
    private String features;
    private String photoGalleries;
    private String[] arrayFeatures;
    private String[] arraylink;
    private String[] arrayPhotoGalleries;
    private Home_data home_data;
    private ImageView nav;
    private FirebaseAnalytics analytics;
    ImageView itemIcon,itemIcon2,itemIcon3,itemIcon4;
    SubActionButton button1,button2,button3,button4;
     float dX;
    float dY;
    int lastAction;
    private FloatingActionMenu actionMenu;
    FloatingActionButton actionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        mRootRef = database.getReference("/No5tha/housesData");
        mchild = mRootRef.child("public/");

        //  tab host

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(),R.id.realtabcontent);
        // add our navigation menu
        Bundle b = new Bundle();
        b.putString("key", "home");
        mTabHost.addTab(mTabHost.newTabSpec("home").setIndicator("",getResources().getDrawable(R.drawable.logo)), com.yacob.weekend.Fragment_home.class, b);

        b = new Bundle();
        b.putString("key", "favorite");
        mTabHost.addTab(mTabHost.newTabSpec("favorite").setIndicator("",getResources().getDrawable(R.drawable.favourate_preview)), com.yacob.weekend.Fragment_favorite.class, b);

        b = new Bundle();
        b.putString("key", "map");
        mTabHost.addTab(mTabHost.newTabSpec("map").setIndicator("",getResources().getDrawable(R.drawable.geography_preview)), com.yacob.weekend.Fragment_map.class, b);
        b = new Bundle();
        b.putString("key", "profile");
        mTabHost.addTab(mTabHost.newTabSpec("profile").setIndicator("",getResources().getDrawable(R.drawable.businessman_preview)), com.yacob.weekend.Fragment_profile.class, b);
       // b = new Bundle();
        //b.putString("key", "menu");
        //mTabHost.addTab(mTabHost.newTabSpec("menu").setIndicator("",getResources().getDrawable(R.drawable.menu5)), com.yacob.weekend.Fragment_test.class, b);
        //////
        // Grab the widget
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mSlideSideMenu = (SlideSideMenuTransitionLayout)findViewById(R.id.slide_side_menu);
        // get dynamic link
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        if (pendingDynamicLinkData != null) {
                            //init analytics if you want to get analytics from your dynamic links
                            analytics = FirebaseAnalytics.getInstance(MainActivity.this);
                            final Boolean[] testRefer = {false};
                            Uri deepLink = pendingDynamicLinkData.getLink();
                            String referID = deepLink.getQueryParameter("refer");
                            String refererUUID = deepLink.getQueryParameter("UUID");
                            String android_id = Settings.Secure.getString(MainActivity.this.getContentResolver(),
                                    Settings.Secure.ANDROID_ID);
                            if (FirebaseAuth.getInstance().getCurrentUser() !=null){
                                mchild = database.getReference("/No5tha/userProfile");
                                mchild.child(auth.getCurrentUser().getUid().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.hasChild("referrer")){
                                            testRefer[0] = true;
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }

                            //Toast.makeText(MainActivity.this, referrerUid, Toast.LENGTH_SHORT).show();
                            Log.d("deeplink","\nonSuccess called " + deepLink.toString());
                         if(referID.equals(auth.getCurrentUser().getUid().toString()) || refererUUID.equals(android_id)){
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setMessage("تم استخدام هذا الحساب او الجهاز بدعوة سابقاً او لارسال الدعوة، يرجى مراجعة الشروط و الاحكام")
                                        .setCancelable(false)
                                        .setPositiveButton("تم", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //do things
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }else{
                                if (testRefer[0]){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                    builder.setMessage("تم قبول الدعوة سابقاً")
                                            .setCancelable(false)
                                            .setPositiveButton("تم", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    //do things
                                                }
                                            });
                                    AlertDialog alert = builder.create();
                                    alert.show();

                                }else {
                                    mchild1 = database.getReference("No5tha/referralOrder/AutoChildID");
                                    Map<String, Object> child = new HashMap<String, Object>();
                                    child.put("type", "ST.checkInvitation.st");
                                    child.put("referID", referID);
                                    child.put("refererUUID", refererUUID);
                                    child.put("userUID", auth.getCurrentUser().getUid());
                                    child.put("userUUID", android_id);
                                    child.put("done", "true");
                                    mchild1.push().updateChildren(child);
                                    //Toast.makeText(MainActivity.this, referID, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this,ReciveInvitation.class);
                            intent.putExtra("refer",referID);
                            intent.putExtra("UUID",refererUUID);
                            startActivity(intent);
                            finish();


                                }

                            }



                           /* arraylink = deepLink.toString().split("/");

                            Intent intent = new Intent(MainActivity.this,
                                    Detail_home.class);
                            home_data =gethousedata(arraylink[arraylink.length-1]);
                            Detail_home.hotelsData = gethousedata(arraylink[arraylink.length-1]);
                            intent.putExtra("KEY_FROM","fromMain");
                            startActivity(intent);*/


                            //logic here, redeem code or whatever
                            /*FirebaseAppInvite invite = FirebaseAppInvite.getInvitation(pendingDynamicLinkData);
                            if (invite != null) {
                                String invitationId = invite.getInvitationId();
                                if (!TextUtils.isEmpty(invitationId))
                                    Log.d("invite","invitation Id " + invitationId);
                            }*/
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("invite","fail");
                    }
                });

        mToolbar.setNavigationIcon(R.drawable.menu);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideSideMenu.toggle();

            }
        });
        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageDrawable( getResources().getDrawable(R.drawable.button) );
        actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .setPosition(3)
                .build();
          /* actionButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = v.getX() - event.getRawX();
                        dY = v.getY() - event.getRawY();
                        lastAction = MotionEvent.ACTION_DOWN;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        v.setY(event.getRawY() + dY);
                        v.setX(event.getRawX() + dX);
                        lastAction = MotionEvent.ACTION_MOVE;
                        break;
                    case MotionEvent.ACTION_UP:
                        if (lastAction == MotionEvent.ACTION_DOWN)
                            Toast.makeText(MainActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });*/
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatingActionButton.LayoutParams params=new FloatingActionButton.LayoutParams(200,200);

                // search
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(MainActivity.this);
        itemIcon = new ImageView(MainActivity.this);
        itemIcon.setImageDrawable(getResources().getDrawable(R.drawable.search));
        button1 = itemBuilder.setContentView(itemIcon).setLayoutParams(params).build();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionMenu.close(true);
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.search);
                dialog.setTitle("search");
                WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
                lp.dimAmount=0.0f;
                dialog.getWindow().setAttributes(lp);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(240,51,51,153)));
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                final String[] t = {"","","","","0"};
                final ImageView img_closedialog = (ImageView)dialog.findViewById(R.id.img_closedialog);
                final ImageView family = (ImageView) dialog.findViewById(R.id.family);
                //final ImageView mixed = (ImageView) dialog.findViewById(R.id.mixed);
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
                final Drawable normal = getResources().getDrawable( R.drawable.rounded_corner_back);


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
                        freind.setBackground(normal);
                        //mixed.setBackground(normal);
                        type.setText("Family");
                        t[0] ="family";
                    }
                });
                /*mixed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Drawable highlight = getResources().getDrawable( R.drawable.highlight);
                        mixed.setBackground(highlight);
                        freind.setBackground(normal);
                        family.setBackground(normal);
                        type.setText("Mixed");
                        t[0] ="mix";
                    }
                });*/
                freind.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Drawable highlight = getResources().getDrawable( R.drawable.highlight);
                        freind.setBackground(highlight);
                        family.setBackground(normal);
                       // mixed.setBackground(normal);
                        type.setText("Freinds");
                        t[0] ="freind";
                    }
                });
                house.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Drawable highlight = getResources().getDrawable( R.drawable.highlight);
                        house.setBackground(highlight);
                        building.setBackground(normal);
                        bysea.setBackground(normal);
                        housetype.setText("apartment");
                        t[1] ="blocks";
                    }
                });
                building.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Drawable highlight = getResources().getDrawable( R.drawable.highlight);
                        building.setBackground(highlight);
                        house.setBackground(normal);
                        bysea.setBackground(normal);
                        housetype.setText("blocks");
                        t[1] ="blocks";
                    }
                });
                bysea.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Drawable highlight = getResources().getDrawable( R.drawable.highlight);
                        bysea.setBackground(highlight);
                        house.setBackground(normal);
                        building.setBackground(normal);
                        housetype.setText("Private House");
                        t[1] ="privateHouse";
                    }
                });
                line1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Drawable highlight = getResources().getDrawable( R.drawable.highlight);
                        line1.setBackground(highlight);
                        line2.setBackground(normal);
                        line3.setBackground(normal);
                        whichline.setText("1st Line");
                        t[2] ="firstLine";
                    }
                });
                line2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Drawable highlight = getResources().getDrawable( R.drawable.highlight);
                        line2.setBackground(highlight);
                        line1.setBackground(normal);
                        line3.setBackground(normal);
                        whichline.setText("2nd Line");
                        t[2] ="secondLine";
                    }
                });
                line3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Drawable highlight = getResources().getDrawable( R.drawable.highlight);
                        line3.setBackground(highlight);
                        line2.setBackground(normal);
                        line1.setBackground(normal);
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
                        Intent intent = new Intent(MainActivity.this,Search.class);
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
        });
        // horizantal home page
         itemIcon2 = new ImageView(MainActivity.this);
        itemIcon2.setImageDrawable(getResources().getDrawable(R.drawable.map));
        button2 = itemBuilder.setContentView(itemIcon2).setLayoutParams(params).build();
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionMenu.close(true);
                Intent intent = new Intent(MainActivity.this,HorizontalListHome.class);
                startActivity(intent);
                finish();

            }
        });
        itemIcon3 = new ImageView(MainActivity.this);
        itemIcon3.setImageDrawable(getResources().getDrawable(R.drawable.chat));
         button3 = itemBuilder.setContentView(itemIcon3).setLayoutParams(params).build();
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionMenu.close(true);
                Intent intent = new Intent(MainActivity.this,Chat.class);
                startActivity(intent);
                finish();

            }
        });
        itemIcon4 = new ImageView(MainActivity.this);
        itemIcon4.setImageDrawable(getResources().getDrawable(R.drawable.signin));
        button4 = itemBuilder.setContentView(itemIcon4).setLayoutParams(params).build();
        button4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(actionMenu.isOpen())
                            actionMenu.close(true);
                        if(lblMenuSignUp.getText().equals("تسجيل الدخول")){
                            lblMenuSignUp.setText("الخروج");
                            startActivityForResult(
                                    AuthUI.getInstance()
                                            .createSignInIntentBuilder()
                                            .setLogo(R.drawable.logo)
                                            .setTheme(R.style.LoginTheme)
                                            .setAvailableProviders(
                                                    Arrays.asList(
                                                            new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build(),
                                                            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                                            new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(),
                                                            new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build()))
                                            .build(),
                                    0);
                        }
                        else {
                            AuthUI.getInstance().signOut(MainActivity.this).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    //finish();
                                    lblMenuSignUp.setText("تسجيل الدخول");
                                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(getApplicationContext(), "تم تسجيل الخروج", Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                    }
                });

         actionMenu = new FloatingActionMenu.Builder(MainActivity.this)
                .addSubActionView(button1)
                .addSubActionView(button2)
                .addSubActionView(button3)
                 .addSubActionView(button4)
                .attachTo(actionButton)
                .build();

            }
        });
        lblMenuFavourite = (TextView)findViewById(R.id.lblFavourite);
        lblMenuShare = (TextView)findViewById(R.id.lblShare);
        lblMenuSignUp = (TextView)findViewById(R.id.lblMenuSignUp);
        lblMenuSignOut=findViewById(R.id.lblMenuSignOut);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            // already signed in
            String cc = auth.getCurrentUser().getDisplayName();
           // Log.d("auth", auth.getCurrentUser().getEmail());
            lblMenuSignUp.setText("الخروج");
        } else {
            // not signed in
            lblMenuSignUp.setText("تسجيل الدخول");
        }
        lblMenuFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSlideSideMenu.closeSideMenu();

            }
        });
        lblMenuSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,Contactus.class);
                startActivity(intent);
                finish();
            }
        });
        lblMenuShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareLongDynamicLink(view);

                //Intent intent = new Intent(MainActivity.this,SharingPageActivity.class);
                //SharingPageActivity.hotelsData = ItemListFragment.hotelsDatas.get(0);
                //startActivity(intent);
                //overridePendingTransition(R.anim.pull_in_right,R.anim.push_out_left);
            }
        });
        lblMenuSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(actionMenu!=null){
                if(actionMenu.isOpen())
                    actionMenu.close(true);}
                if(lblMenuSignUp.getText().equals("تسجيل الدخول")){
                    //lblMenuSignUp.setText("الخروج");
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setLogo(R.drawable.logo)
                                .setTheme(R.style.LoginTheme)
                                .setAvailableProviders(
                                        Arrays.asList(
                                                new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build(),
                                                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                                new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(),
                                                new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build()))
                                .build(),
                        0);
                }
                else {
                    AuthUI.getInstance().signOut(MainActivity.this).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //finish();
                            lblMenuSignUp.setText("تسجيل الدخول");
                            Toast.makeText(getApplicationContext(), "تم تسجيل الخروج", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });


        FirebaseMessaging.getInstance().subscribeToTopic("allandroid");
        Bundle extras = getIntent().getExtras();
        if (extras!=null) {
           //String houseid = extras.getString("houseid");
            //Toast.makeText(getApplicationContext(), "intent " + content, Toast.LENGTH_LONG).show();
           //Intent intent = new Intent(MainActivity.this,Detail_home.class);
            //Detail_home.hotelsData=gethousedata(houseid);
            //intent.putExtra("KEY_FROM","fromMain");
            //startActivity(intent);
        }

    }

    private Home_data gethousedata(final String houseid) {

        //lstItems.setAdapter(new HotelsItemsListAdapter(SplashActivity.hotelsDatas,getActivity()));


        //Retriving data from Firebase Path
        mchild.child(houseid).addValueEventListener(new ValueEventListener() {
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
                     if (houseId.equals(houseid)){
                     home_data = new Home_data(Bool,HouseNumber,Latitude,Longitude,basement,descruption,
                            floors,houseId,houseName,houseType,location,masterRooms,priority,privateSwimmingPool,
                            rentrules,rooms,salon,toilet,typeOfPeopleAllowedToRent,whichLineOnSea,arrayFeatures,arrayPhotoGalleries,arrayPhotoGalleries[0],photoGalleries,features,1);
                    }

                }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return home_data;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Log.d("auth", "+success");
            lblMenuSignUp.setText("الخروج");
            Log.d("signin", "onActivityResult: "+ auth.getCurrentUser().getUid());
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else {
            Log.d("auth", "error auth");
        }
    }
    @Override
    public void onBackPressed() {
        if (mSlideSideMenu != null && mSlideSideMenu.closeSideMenu()) {
            // Closed the side menu, override the default back pressed behavior
            return;
        }
        else finish();
        super.onBackPressed();
    }
    public void shareLongDynamicLink(View view) {
        //Intent intent = new Intent();
        //String msg =  buildDynamicLink();
        //intent.setAction(Intent.ACTION_SEND);
        //intent.putExtra(Intent.EXTRA_TEXT, msg);
        //intent.setType("text/plain");
        //startActivity(Intent.createChooser(intent,"share"));
        shareShortDynamicLink(view);
    }

    private String buildDynamicLink(/*String link, String description, String titleSocial, String source*/) {
         String android_id = Settings.Secure.getString(MainActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        //more info at https://firebase.google.com/docs/dynamic-links/create-manually
       // Uri uri= Uri.parse("https://firebasestorage.googleapis.com/v0/b/no5tha-17084.appspot.com/o/No5tha%2Flogo-83.5%402x.png?alt=media&token=25bfc985-1dda-46aa-933b-397c7eb66683");
        String path = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setDynamicLinkDomain("yr2ks.app.goo.gl")
                .setLink(Uri.parse("https://www.weekendq8.com/?refer="+auth.getCurrentUser().getUid()+"&UUID="+android_id))
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                .setIosParameters(
                        new DynamicLink.IosParameters.Builder("com.yacob.weekend")
                                .setAppStoreId("1249814392")
                                .setMinimumVersion("1.0.1")
                                .build())
                //.setSocialMetaTagParameters(new DynamicLink.SocialMetaTagParameters.Builder().setTitle("Share "+hotelsData.getHouseName()).setDescription(hotelsData.getDescruption()).build())
                //.setGoogleAnalyticsParameters(new DynamicLink.GoogleAnalyticsParameters.Builder().setSource("AndroidApp").build())
                .buildDynamicLink().getUri().toString();
        return path;


        /*return "https://yr2ks.app.goo.gl/?"+
                "link=" +
                "https://www.weekendq8.com/?refer="+auth.getCurrentUser().getUid()+"&UUID="+android_id+
                "&apn=com.yacob.weekend" +
                "&st=" + //titleSocial*
                "Weekend" +
                "&sd=" + //description
                " renting luxurious houses." +
                "&si=https://firebasestorage.googleapis.com/v0/b/no5tha-17084.appspot.com/o/No5tha%2Flogo-83.5%402x.png?alt=media&token=25bfc985-1dda-46aa-933b-397c7eb66683"+
                "&isi=1249814392"+
                "&ibi=com.yacob.weekend";*/
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
                            String msg = shortLink.toString();
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
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(actionMenu!=null) {
            if (actionMenu.isOpen()) {
//set close homemenu when toutch outside it
                Rect circlesiconhitBox = new Rect();
                actionButton.getGlobalVisibleRect(circlesiconhitBox);
                Rect eventsIconhitBox = new Rect();
                button1.getGlobalVisibleRect(eventsIconhitBox);
                Rect neighborIconshitBox = new Rect();
                button2.getGlobalVisibleRect(neighborIconshitBox);
                Rect neighborIconshitBox2 = new Rect();
                button3.getGlobalVisibleRect(neighborIconshitBox2);
                Rect neighborIconshitBox3 = new Rect();
                button4.getGlobalVisibleRect(neighborIconshitBox3);
                if (!(neighborIconshitBox.contains((int) ev.getX(), (int) ev.getY())
                        || neighborIconshitBox2.contains((int) ev.getX(), (int) ev.getY())
                        || neighborIconshitBox3.contains((int) ev.getX(), (int) ev.getY())
                        || eventsIconhitBox.contains((int) ev.getX(), (int) ev.getY())
                        || circlesiconhitBox.contains((int) ev.getX(), (int) ev.getY()))) {
                    actionMenu.close(true);
                    return true;
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    
}

// logout
/*logout = findViewById(R.id.button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance().signOut(MainActivity.this).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        finish();
                    }
                });

            }
        });*/
