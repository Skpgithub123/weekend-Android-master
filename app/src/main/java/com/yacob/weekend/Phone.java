package com.yacob.weekend;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class Phone extends AppCompatActivity {
    private static final String TAG = "PhoneAuth";
    private Toolbar mToolbar;
    private EditText phoneText;
    private EditText codeText;
    private Button verifyButton;
    private Button sendButton;
    private String phoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;
    FirebaseDatabase database;
    DatabaseReference mRootRef;
    private FirebaseAuth fbAuth;
    String refer,UUID,from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        phoneText = (EditText) findViewById(R.id.editText);
        codeText = (EditText) findViewById(R.id.editText2);
        verifyButton = (Button) findViewById(R.id.button3);
        sendButton = (Button) findViewById(R.id.button2);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("أضف رقم الهاتف");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        verifyButton.setVisibility(View.GONE);
        codeText.setVisibility(View.GONE);

        //verifyButton.setEnabled(false);
        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        if (from.equals("invit")){
        refer=intent.getStringExtra("refer");
        UUID= intent.getStringExtra("UUID");
        }else{
        phoneText.setText(intent.getStringExtra("phonenumber"), TextView.BufferType.EDITABLE);
        }
        fbAuth = FirebaseAuth.getInstance();
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sendButton.getText().equals("إعادة إرسال الكود")) {
                    resendCode(v);
                }else{
                    sendCode(v);
                }
            }
        });
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCode(v);
            }
        });


    }
    public void sendCode(View view) {

        String phoneNumber = phoneText.getText().toString();
        setUpVerificatonCallbacks();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                verificationCallbacks);

    }

    private void setUpVerificatonCallbacks() {

        verificationCallbacks =
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(
                            PhoneAuthCredential credential) {

                        verifyButton.setVisibility(View.GONE);
                        codeText.setVisibility(View.GONE);
                        signInWithPhoneAuthCredential(credential);
                        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user !=null) {
                            Log.d(TAG, "onVerificationCompleted: "+user.getUid());
                            database = FirebaseDatabase.getInstance();
                            mRootRef = database.getReference("/No5tha/userProfile");
                            mRootRef.child(user.getUid()).child("phoneNumber").setValue(phoneText.getText().toString());
                        }
                        Toast.makeText(Phone.this,"تم التثبت من الكود بنجاح", Toast.LENGTH_SHORT).show();
                        if (from.equals("invit")){
                            Intent intent=new Intent(Phone.this,Insert_info.class);
                            intent.putExtra("refer",refer);
                            intent.putExtra("UUID",UUID);
                            startActivity(intent);
                            finish();
                        }else {
                            Intent intent = new Intent(Phone.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {

                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            // Invalid request
                            Log.d(TAG, "Invalid credential: "
                                    + e.getLocalizedMessage());
                            Toast.makeText(Phone.this,"الرجاء التثبت من رقم الهاتف", Toast.LENGTH_SHORT).show();
                        } else if (e instanceof FirebaseTooManyRequestsException) {
                            // SMS quota exceeded
                            Log.d(TAG, "SMS Quota exceeded.");
                        }
                    }

                    @Override
                    public void onCodeSent(String verificationId,
                                           PhoneAuthProvider.ForceResendingToken token) {

                        phoneVerificationId = verificationId;
                        resendToken = token;

                        verifyButton.setVisibility(View.VISIBLE);
                        codeText.setVisibility(View.VISIBLE);
                        sendButton.setText("20");
                        new CountDownTimer(20000, 1000) {

                            public void onTick(long millisUntilFinished) {
                                sendButton.setText( String.valueOf( millisUntilFinished / 1000));
                            }

                            public void onFinish() {
                                sendButton.setEnabled(true);
                                sendButton.setText("إعادة إرسال الكود");
                            }
                        }.start();
                        sendButton.setEnabled(false);
                    }
                };
    }

    public void verifyCode(View view) {

        String code = codeText.getText().toString();

        PhoneAuthCredential credential =
                PhoneAuthProvider.getCredential(phoneVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        fbAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            codeText.setText("");
                            verifyButton.setEnabled(false);
                            FirebaseUser user = task.getResult().getUser();

                        } else {
                            if (task.getException() instanceof
                                    FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(Phone.this,"الرجاء التثبت من الكود", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                });
    }

    public void resendCode(View view) {

        String phoneNumber = phoneText.getText().toString();

        setUpVerificatonCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                verificationCallbacks,
                resendToken);
    }
}
