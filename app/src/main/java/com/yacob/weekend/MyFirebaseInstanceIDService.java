package com.yacob.weekend;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Dhahri on 01/02/2018.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        //now we will have the token
        String token = FirebaseInstanceId.getInstance().getToken();

        //for now we are displaying the token in the log
        //copy it as this method is called only when the new token is generated
        //and usually new token is only generated when the app is reinstalled or the data is cleared
        Log.d("MyRefreshedToken", token);
    }
}
