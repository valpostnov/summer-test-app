package com.postnov.android.summerschoolapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by platon on 23.07.2016.
 */
public class PreferencesManager
{
    public static final String HEADSET_FEATURE_STATE = "settings.feature.headset";
    public static final String YA_SERVICE_RUNNING_STATE = "setting.service";
    private static final String SHARED_PREF_NAME = "com.postnov.artists.pref";

    private SharedPreferences mSharedPreferences;

    public PreferencesManager(Context context, String name)
    {
        mSharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public PreferencesManager(Context context)
    {
        this(context, SHARED_PREF_NAME);
    }

    public SharedPreferences getSharedPref()
    {
        return mSharedPreferences;
    }

    public Boolean getBoolean(String key)
    {
        return mSharedPreferences.getBoolean(key, false);
    }

    public void setBoolean(String key, boolean value)
    {
        mSharedPreferences.edit().putBoolean(key, value).apply();
    }
}
