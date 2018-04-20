package com.yacob.weekend;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;



public class Feedback extends AppCompatActivity
{
    Toolbar mToolbar;
    Button btnSendSuggestion;
    SmileRating smileRating;
    String TAG = Feedback.class.getName();
    FirebaseDatabase database;
    DatabaseReference mRootRef;
    String rate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Feedback");
        //mToolbar.setLogo(R.drawable.icon);
        mToolbar.setNavigationOnClickListener
                (new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();

                    }
                });

        smileRating = (SmileRating) findViewById(R.id.smile_rating);
        btnSendSuggestion = (Button)findViewById(R.id.btnSendSuggestion);

        smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(@BaseRating.Smiley int smiley, boolean reselected) {
                // reselected is false when user selects different smiley that previously selected one
                // true when the same smiley is selected.
                // Except if it first time, then the value will be false.
                switch (smiley) {
                    case SmileRating.BAD:
                        Log.d(TAG, "Bad");
                        rate="Sad";
                        break;
                    case SmileRating.GOOD:
                        Log.d(TAG, "Good");
                        rate="Happy";
                        CallToReview();
                        break;
                    case SmileRating.GREAT:
                        Log.d(TAG, "Great");
                        rate="Happy";
                        CallToReview();
                        break;
                    case SmileRating.OKAY:
                        Log.d(TAG, "Okay");
                        rate="Neutral";
                        CallToReview();
                        break;
                    case SmileRating.TERRIBLE:
                        Log.d(TAG, "Terrible");
                        rate="Sad";
                        break;
                }
            }
        });

        btnSendSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (FirebaseAuth.getInstance().getCurrentUser() !=null){
                    database = FirebaseDatabase.getInstance();
                    mRootRef = database.getReference("/No5tha/userProfile");
                    mRootRef.child(user.getUid()).child("feedback").setValue(rate);
                    Log.d(TAG, "Terrible");
                }

            }
        });
    }

    public void CallToReview()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Feedback.this);
        //builder.setIcon(R.drawable.icon);
        builder.setTitle("Give Review");
        builder.setMessage("Do you want to write review in App Store?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Uri uri = Uri.parse("market://details?id=" + Feedback.this.getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + Feedback.this.getPackageName())));
                }

            }
        });
        builder.setNegativeButton("Later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Uri uri = Uri.parse("market://details?id=" + Feedback.this.getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + Feedback.this.getPackageName())));
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}
