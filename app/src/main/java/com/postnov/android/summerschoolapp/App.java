package com.postnov.android.summerschoolapp;

import android.app.Application;

import com.postnov.android.summerschoolapp.utils.NetworkManager;
import com.postnov.android.summerschoolapp.utils.PreferencesManager;

/**
 * Created by platon on 09.08.2016.
 */
public class App extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        NetworkManager.init(getApplicationContext());
        PreferencesManager.init(getApplicationContext());
    }
}
