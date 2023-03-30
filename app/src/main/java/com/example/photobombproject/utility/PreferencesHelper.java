package com.example.photobombproject.utility;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.photobombproject.R;
import com.google.gson.Gson;
import com.google.gson.internal.Primitives;


public class PreferencesHelper {
    private static PreferencesHelper instance;
    private final SharedPreferences prefs;
    int Count = 0;

    private PreferencesHelper(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);

    }

    private PreferencesHelper(Context context, String sharePreferencesName) {
        prefs = context.getApplicationContext().getSharedPreferences(sharePreferencesName, Context.MODE_PRIVATE);
    }

    public static PreferencesHelper getInstance(Context ctxt) {
        if (instance == null)
            instance = new PreferencesHelper(ctxt);
        return instance;
    }

    public static PreferencesHelper initHelper(Context context) {
        if (instance == null)
            instance = new PreferencesHelper(context);
        return instance;
    }

    public static PreferencesHelper initHelper(Context context, String sharePreferencesName) {
        if (instance == null)
            instance = new PreferencesHelper(context, sharePreferencesName);
        return instance;
    }

    public void setBoolean(String KEY, boolean value) {
        prefs.edit().putBoolean(KEY, value).apply();
    }

    public boolean getBooleanValue(String KEY) {
        return prefs.getBoolean(KEY, false);
    }

    public void setValue(String KEY, String value) {
        prefs.edit().putString(KEY, value).apply();
    }

    public void setValue(String KEY, Object value) {
        prefs.edit().putString(KEY, new Gson().toJson(value)).apply();
    }


    public String getStringValue(String KEY, String defvalue) {
        return prefs.getString(KEY, defvalue);
    }

    public <T> T getObjectValue(String KEY, Class<T> mModelClass) {
        Object object = null;
        try {
            object = new Gson().fromJson(prefs.getString(KEY, ""), mModelClass);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Primitives.wrap(mModelClass).cast(object);
    }

    public int getIntValue(String KEY, int defValue) {
        return prefs.getInt(KEY, defValue);
    }


    public void setCount() {
        Count = 1;
    }

    public boolean getCount() {
        if (Count == 1)
            return true;
        else
            return false;
    }

}
