package com.yacob.weekend;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Insert_info extends AppCompatActivity {
    EditText name,email;
    Button save,quit;
    FirebaseDatabase database;
    DatabaseReference mRootRef,mchild;
    private FirebaseAuth auth;
    String refer,UUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_info);
        name = findViewById(R.id.rname);
        email= findViewById(R.id.remail);
        save = findViewById(R.id.save);
        quit= findViewById(R.id.quit);
        Intent intent =getIntent();
        refer=intent.getStringExtra("refer");
        UUID= intent.getStringExtra("UUID");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( name.getText().toString().matches("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Insert_info.this);
                    builder.setMessage("الرجاء ادخال الاسم")
                            .setCancelable(false)
                            .setPositiveButton("خروج", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();

                }else{
                    String android_id = Settings.Secure.getString(Insert_info.this.getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                    auth = FirebaseAuth.getInstance();
                    database = FirebaseDatabase.getInstance();
                    mRootRef = database.getReference("/No5tha/housesData");
                    mchild = database.getReference("No5tha/referralOrder/AutoChildID");
                    Map<String, Object> child = new HashMap<String, Object>();
                    child.put("type", "newReferee");
                    child.put("referID", refer);
                    child.put("refererUUID", UUID);
                    child.put("userUID", auth.getCurrentUser().getUid().toString());
                    child.put("userUUID", android_id);
                    child.put("name", name.getText().toString());
                    mchild.push().updateChildren(child);
                    Toast.makeText(Insert_info.this, "لقد ربحت 30 نقطة", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Insert_info.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                }
            }
        });

    }
}
