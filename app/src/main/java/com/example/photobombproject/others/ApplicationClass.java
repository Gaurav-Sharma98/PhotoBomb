package com.example.photobombproject.others;

import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;


public class ApplicationClass extends MultiDexApplication {
    private static Context appContext;
    String userAgent;
    private static ApplicationClass mInstance;

    public static Context getAppContext() {
        return appContext;
    }

    public static synchronized ApplicationClass getInstance() {
        return mInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        appContext = this;



        MultiDex.install(this);
        /// PreferencesHelper.initHelper(this);
    }



}
