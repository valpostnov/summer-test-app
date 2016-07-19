package com.postnov.android.summerschoolapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by platon on 11.05.2016.
 */
public class NetworkManager
{
    private Context mContext;

    public NetworkManager(Context context)
    {
        mContext = context;
    }

    public boolean networkIsAvailable()
    {
        ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        return  (activeInfo != null && activeInfo.isConnected());
    }
}
