package com.postnov.android.summerschoolapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by platon on 23.07.2016.
 */
public class PreferencesManager implements IPreferencesManager
{
    public static final String HEADSET_FEATURE_STATE = "settings.feature.headset";
    public static final String YA_SERVICE_RUNNING_STATE = "setting.service";
    private static final String SHARED_PREF_NAME = "com.postnov.artists.pref";

    private static PreferencesManager sPreferencesManager;
    private SharedPreferences sharedPreferences;

    public static void init(Context context)
    {
        sPreferencesManager = new PreferencesManager(context);
    }

    public static void init(Context context, String name)
    {
        sPreferencesManager = new PreferencesManager(context);
    }

    public static PreferencesManager getManager()
    {
        return sPreferencesManager;
    }

    private PreferencesManager(Context context, String name)
    {
        sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    private PreferencesManager(Context context)
    {
        this(context, SHARED_PREF_NAME);
    }

    @Override
    public boolean getBoolean(String key)
    {
        return sharedPreferences.getBoolean(key, false);
    }

    @Override
    public void setBoolean(String key, boolean value)
    {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }
}
