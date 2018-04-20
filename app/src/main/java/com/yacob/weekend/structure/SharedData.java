package com.yacob.weekend.structure;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Suruchi on 7/13/2016.
 */
public class SharedData {

    Context context;
    SharedPreferences prefs;

    public static String KEY_REG = "KEY_REG";
    public static String KEY_NAME = "KEY_NAME";
    public static String KEY_EMAIL = "KEY_EMAIL";
    public static String KEY_PHONE = "KEY_PHONE";
    public static String KEY_ADDRESS = "KEY_ADDRESS";
    public static String KEY_IMEI = "KEY_IMEI";
    public static String KEY_SERVER = "KEY_SERVER";
    public static String KEY_BATTERY = "KEY_BATTERY";

    public SharedData(Context context)
    {
        this.context = context;
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void addStringData(String key, String value)
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public void addIntData(String key, int value)
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key,value);
        editor.commit();
    }

    public void addBooleanData(String key, boolean value)
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }


    public String getStringData(String key)
    {
        return prefs.getString(key,"");
    }

    public int getIntData(String key)
    {
        return prefs.getInt(key,0);
    }


    public boolean getBooleanData(String key)
    {
        return prefs.getBoolean(key,false);
    }

}
