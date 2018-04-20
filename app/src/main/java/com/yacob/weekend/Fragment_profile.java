package com.yacob.weekend;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_profile extends Fragment {
    View view;
    ImageView imgprofile;
    TextView nameprofile;
    ImageButton img1,img2,img3,img4,img5,img6,img7,img8,img9,img10;
    RelativeLayout r1, r2, r3, r4, r5, r6, r7, r8, r9, r10;
    FirebaseDatabase database;
    DatabaseReference mRootRef, mchild;
    private String email, phoneNumber, name, photoUrl;
    private FirebaseAuth auth;
    Button signin;


    @Override
    public  View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        //Decleration and Initialization of widgets
        imgprofile = view.findViewById(R.id.imgp);
///Updated upstream
        nameprofile = view.findViewById(R.id.text1);
        r1 = view.findViewById(R.id.r1);
        r2 = view.findViewById(R.id.r2);
        r5 = view.findViewById(R.id.r5);
        r6 = view.findViewById(R.id.r6);
        r7 = view.findViewById(R.id.r7);
        r8 = view.findViewById(R.id.r8);
        r9 = view.findViewById(R.id.r9);
        r10 = view.findViewById(R.id.r10);
        //Initializing Firebase Database

            // already signed in
            name = auth.getCurrentUser().getDisplayName();
            //Log.d("auth", auth.getCurrentUser().getEmail());

            database = FirebaseDatabase.getInstance();
            mRootRef = database.getReference("/No5tha");
            mchild = mRootRef.child("userProfile/");
            Query query = mRootRef.child("userProfile/").orderByChild("name").equalTo(name);
            //lstItems.setAdapter(new HotelsItemsListAdapter(SplashActivity.hotelsDatas,getActivity()));

            //Retriving data from Firebase Path
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            if (ds.child("photourl").getValue() != null) {
                                Glide.with(getContext()).load(ds.child("photourl").getValue().toString()).into(imgprofile);
                            }
                            if (ds.child("name").getValue() != null)
                            nameprofile.setText(ds.child("name").getValue().toString());
                            if (ds.child("email").getValue() != null)
                            email = ds.child("email").getValue().toString();
                            if (ds.child("phoneNumber").getValue() != null)
                            phoneNumber = ds.child("phoneNumber").getValue().toString();
                            // do with your result
                        }}}
                        @Override
                        public void   onCancelled(DatabaseError databaseError) {
                        System.out.println("DATABASE ERROR");
                    }});





                        //Initializing Firebase Database

                        r1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(getContext(), Profile_info.class);
                                intent.putExtra("name", name);
                                intent.putExtra("email", email);
                                intent.putExtra("phonenumber", phoneNumber);
                                startActivity(intent);

                            }
                        });
                        r2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(), Phone.class);
                                intent.putExtra("from", "profil");
                                intent.putExtra("name", name);
                                intent.putExtra("email", email);
                                intent.putExtra("phonenumber", phoneNumber);
                                startActivity(intent);

                            }
                        });
                        r5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(), Account.class);
                                intent.putExtra("name", name);
                                intent.putExtra("email", email);
                                intent.putExtra("phonenumber", phoneNumber);
                                startActivity(intent);

                            }
                        });
                        r6.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(), Booking.class);
                                startActivity(intent);

                            }
                        });
                        r7.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(), Add_address.class);
                                startActivity(intent);

                            }
                        });
                        r8.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(), Document.class);
                                startActivity(intent);

                            }
                        });
                        r9.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(), Help.class);
                                startActivity(intent);

                            }
                        });
                        r10.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(), Feedback.class);
                                startActivity(intent);

                            }
                        });


                        //Click Listner Event of ListView
       /* lstItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(),
                        Detail_home.class);
                Detail_home.home_detail= hotelsDatas.get(i);
                intent.putExtra("KEY_FROM","fromMain");
                startActivity(intent);
            }
        });*/


                        //     return view;

        } else {
            view = inflater.inflate(R.layout.fragment_profile2, container, false);
            signin= view.findViewById(R.id.signin);
            signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setLogo(R.drawable.logo)
                                    .setTheme(R.style.LoginTheme)
                                    .setAvailableProviders(
                                            Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                                    new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build(),
                                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                                    new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(),
                                                    new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build()))
                                    .build(),
                            0);
                }
            });

                        //   return view;
                    }
    return view;
    }

}