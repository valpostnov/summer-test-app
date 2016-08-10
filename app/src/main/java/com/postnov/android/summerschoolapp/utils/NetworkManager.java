package com.postnov.android.summerschoolapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by platon on 11.05.2016.
 */
public class NetworkManager implements INetworkManager
{
    private Context context;

    public NetworkManager(Context context)
    {
        this.context = context;
    }

    @Override
    public boolean networkIsAvailable()
    {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        return  (activeInfo != null && activeInfo.isConnected());
    }
}
