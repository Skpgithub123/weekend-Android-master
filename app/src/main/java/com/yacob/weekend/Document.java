package com.yacob.weekend;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.ByteArrayOutputStream;

/**
 * Created by Pravin on 14-11-2017.
 */

public class Document extends AppCompatActivity implements IPickResult
{
    Toolbar mToolbar;
    ImageView img;
    ImageButton add;
    StorageReference imgRef,storageRef;
    FirebaseDatabase database;
    DatabaseReference mRootRef;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Documents");
        //mToolbar.setLogo(R.drawable.icon);
        mToolbar.setNavigationOnClickListener
                (new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();

                    }
                });

        add = findViewById(R.id.imgAddDoc);
        img= findViewById(R.id.img_documents);
        // Create a storage reference from our app
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        storageRef = FirebaseStorage.getInstance().getReference();

         // Create a reference to "mountains.jpg"
         imgRef = storageRef.child(user.getUid()+"jpg");


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickSetup setup = new PickSetup();


                PickImageDialog.build(setup)
                        //.setOnClick(this)
                        .show(Document.this);

            }
        });


    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Document.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {
            //If you want the Uri.
            //Mandatory to refresh image from Uri.
            //getImageView().setImageURI(null);

            //Setting the real returned image.
            //getImageView().setImageURI(r.getUri());

            //If you want the Bitmap.
            img.setImageBitmap(r.getBitmap());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            r.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();


            UploadTask uploadTask = imgRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d("ici", "onFailure: ");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Log.d("ici", "onSuccess: ");
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if (FirebaseAuth.getInstance().getCurrentUser() !=null){
                        database = FirebaseDatabase.getInstance();
                        mRootRef = database.getReference("/No5tha/userProfile");
                        mRootRef.child(user.getUid()).child("photoURL").setValue(downloadUrl.toString());
                    }
                }
            });
        } else {
            //Handle possible errors
            //TODO: do what you have to do with r.getError();
            Toast.makeText(this, r.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}

