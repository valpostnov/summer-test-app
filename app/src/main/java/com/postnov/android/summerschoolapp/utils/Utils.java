package com.postnov.android.summerschoolapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by postnov on 25.03.2016.
 */
public class Utils
{
    public static void showToast(Context context, String message)
    {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static String concatStrings(String ... strings)
    {
        StringBuilder builder = new StringBuilder();

        if (strings != null) {

            for (String s : strings)
            {
                builder.append(s);
            }
        }

        return builder.toString();
    }
}
