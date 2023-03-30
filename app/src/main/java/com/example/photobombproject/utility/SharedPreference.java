package com.example.photobombproject.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class SharedPreference {
    private static SharedPreference pref;
    private Context mCtx;
    private SharedPreferences sharedPreference;
    private SharedPreferences.Editor editor;
    private static final String SHARED_PREF_NAME = "sign_shared_preference_owner";



    public SharedPreference(Context mCtx) {
        if(mCtx!=null) {
            this.sharedPreference = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            this.editor = sharedPreference.edit();
            this.mCtx = mCtx;
        }
    }

    public SharedPreference() {
        this.sharedPreference = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        this.editor = sharedPreference.edit();
    }


    /* private SharedPreference() {
         sharedPreference = mCtx.getSharedPreferences(MY_PREFERENCES, MODE);
         editor = sharedPreference.edit();
     }
 */
    public static SharedPreference getInstance(Context mCtx) {
        if (pref == null) {
            pref = new SharedPreference(mCtx);
        }
        return pref;
    }

    public static SharedPreference getInstance() {
        if (pref == null) {
            pref = new SharedPreference();
        }
        return pref;
    }

    public String getString(String key) {
        return sharedPreference.getString(key, "");
    }

    public void putString(String key, String value) {
        editor.putString(key, value).commit();
    }

    public int getInt(String key) {
        return sharedPreference.getInt(key, 0);
    }

    public void putInt(String key, int value) {
        editor.putInt(key, value).commit();
    }


    public long getLong(String key) {
        return sharedPreference.getLong(key, 0l);
    }

    public void putLong(String key, long value) {
        editor.putLong(key, value).commit();
    }

    public float getFloat(String key) {
        return sharedPreference.getFloat(key, 0.5f);
    }

    public void putFloat(String key, float value) {
        editor.putFloat(key, value).commit();
    }

    public boolean getBoolean(String key) {
        //    editor.putBoolean(key, value).commit();
        return sharedPreference.getBoolean(key, false);
    }

    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value).commit();
    }





}