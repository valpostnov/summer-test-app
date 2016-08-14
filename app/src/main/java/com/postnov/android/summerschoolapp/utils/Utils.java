package com.postnov.android.summerschoolapp.utils;

import android.content.Context;
import android.widget.Toast;

import com.postnov.android.summerschoolapp.data.entity.Artist;

import java.util.List;

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

    public static List subList(int from, int to, List list)
    {
        final int size = list.size();
        if (size < to) return list.subList(from, size);
        return list.subList(from, to);
    }
}
