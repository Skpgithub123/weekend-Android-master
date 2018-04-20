package com.yacob.weekend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ReciveInvitation extends AppCompatActivity {
    TextView info;
    Button signin,cancel;
    ImageView photo;
    String refer,UUID,name;
    FirebaseDatabase database;
    DatabaseReference mRootRef, mchild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recive_invitation);
        info=findViewById(R.id.info);
        signin=findViewById(R.id.signin);
        cancel=findViewById(R.id.cancel);
        photo=findViewById(R.id.photo);
        Intent intent = getIntent();
        refer=intent.getStringExtra("refer");
        UUID= intent.getStringExtra("UUID");
        database = FirebaseDatabase.getInstance();
        mRootRef = database.getReference("/No5tha");
        mchild = mRootRef.child("userProfile/"+refer);
        Query query = mRootRef.child("userProfile/").orderByChild("name").equalTo(refer);
        //lstItems.setAdapter(new HotelsItemsListAdapter(SplashActivity.hotelsDatas,getActivity()));

        //Retriving data from Firebase Path
        mchild.addValueEventListener(new ValueEventListener() {
                                                 @Override
                                                 public void onDataChange(com.google.firebase.database.DataSnapshot  ds) {
                                                     if (ds.child("photourl").getValue() != null) {
                                                         Glide.with(ReciveInvitation.this).load(ds.child("photourl").getValue().toString()).into(photo);
                                                     }
                                                     if (ds.child("name").getValue() != null){
                                                         name = ds.child("name").getValue().toString();
                                                         info.setText(name+",\n دعاك الى هذا البرنامج\n اقبل الدعوة بتسجيل الدخول\nو اربح 30 نقطة فورا.");
                                                         Log.d("ici", "onDataChange: "+name);}

                                                 }

                                                 @Override
                                                 public void onCancelled(DatabaseError databaseError) {

                                                 }
                                             });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ReciveInvitation.this,Phone.class);
                intent.putExtra("from","invit");
                intent.putExtra("refer",refer);
                intent.putExtra("UUID",UUID);
                startActivity(intent);
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ReciveInvitation.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
