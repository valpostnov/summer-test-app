package com.postnov.android.summerschoolapp;

import android.app.Application;
import android.content.Context;

import com.postnov.android.summerschoolapp.data.entity.Artist;
import com.postnov.android.summerschoolapp.data.source.IDataSource;
import com.postnov.android.summerschoolapp.data.source.Repository;
import com.postnov.android.summerschoolapp.data.source.local.CacheImpl;
import com.postnov.android.summerschoolapp.data.source.local.ICache;
import com.postnov.android.summerschoolapp.data.source.remote.RemoteDataSource;
import com.postnov.android.summerschoolapp.utils.INetworkManager;
import com.postnov.android.summerschoolapp.utils.IPreferencesManager;
import com.postnov.android.summerschoolapp.utils.NetworkManager;
import com.postnov.android.summerschoolapp.utils.PreferencesManager;

import java.io.File;

/**
 * Created by platon on 09.08.2016.
 */
public class App extends Application
{
    private INetworkManager networkManager;
    private IPreferencesManager preferencesManager;
    private IDataSource artistRepository;

    public static App from(Context context)
    {
        return (App) context.getApplicationContext();
    }

    public INetworkManager getNetworkManager()
    {
        return networkManager;
    }

    public IPreferencesManager getPreferencesManager()
    {
        return preferencesManager;
    }

    public IDataSource getArtistRepository()
    {
        return artistRepository;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        networkManager = new NetworkManager(getApplicationContext());
        preferencesManager = new PreferencesManager(getApplicationContext());
        artistRepository = new Repository(new CacheImpl(getCacheDir()), new RemoteDataSource());
    }
}
