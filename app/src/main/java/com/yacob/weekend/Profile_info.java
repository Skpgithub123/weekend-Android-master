package com.yacob.weekend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yacob.weekend.adapter.Provider_item_adapter;

import java.util.ArrayList;

public class Profile_info extends AppCompatActivity {
    EditText name,email;
    Toolbar mToolbar;
    Button save ;
    String TAG="name";
    ListView list;
    ArrayList<String> list_provider;
    FirebaseDatabase database;
    DatabaseReference mRootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);
        Intent intent = getIntent();
        name= findViewById(R.id.name_edit);
        email=findViewById(R.id.email_edit);
        list=findViewById(R.id.list_provider);
        save =findViewById(R.id.saveprofile);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Profile");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        name.setText(intent.getStringExtra("name"), TextView.BufferType.EDITABLE);
        email.setText(intent.getStringExtra("email"), TextView.BufferType.EDITABLE);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user !=null) {
            list_provider = new ArrayList<String>();
            for (UserInfo profile : user.getProviderData()) {
                Log.d(TAG, "Provider: " + profile.getProviderId());
                list_provider.add(profile.getProviderId().toString());
            }
            list.setAdapter(new Provider_item_adapter(list_provider, Profile_info.this));
            name.setText(user.getDisplayName());
            email.setText(user.getEmail());

        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FirebaseAuth.getInstance().getCurrentUser() !=null){
                    database = FirebaseDatabase.getInstance();
                    mRootRef = database.getReference("/No5tha/userProfile");
                    mRootRef.child(user.getUid()).child("name").setValue(name.getText().toString());
                    mRootRef.child(user.getUid()).child("email").setValue(email.getText().toString());
                }

            }
        });
    }
}
