package com.postnov.android.summerschoolapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by postnov on 25.03.2016.
 */
public class Utils {

    public static boolean checkNetworkConnection(Context context)
    {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        return  (activeInfo != null && activeInfo.isConnected());
    }

    public static String getCorrectTrackString(String num)
    {
        char lastNumber = num.charAt(num.length() - 1);

        if (Integer.valueOf(num) > 9 && Integer.valueOf(num) < 20)
        {
            return num + " песен";
        }

        switch (lastNumber)
        {
            case '1':
                return num + " песня";
            case '2':
                return num + " песни";
            case '3':
                return num + " песни";
            case '4':
                return num + " песни";
            default:
                return num + " песен";
        }
    }

    public static String getCorrectAlbumString(String num)
    {
        char lastNumber = num.charAt(num.length() - 1);

        if (Integer.valueOf(num) > 9 && Integer.valueOf(num) < 20)
        {
            return num + " альбомов";
        }

        switch (lastNumber)
        {
            case '1':
                return num + " альбом";
            case '2':
                return num + " альбома";
            case '3':
                return num + " альбома";
            case '4':
                return num + " альбома";
            default:
                return num + " альбомов";
        }
    }
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
