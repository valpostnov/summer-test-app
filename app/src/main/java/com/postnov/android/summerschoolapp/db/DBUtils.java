package com.postnov.android.summerschoolapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.postnov.android.summerschoolapp.utils.Utils;

import static com.postnov.android.summerschoolapp.provider.ArtistsContract.Artist;

/**
 * Created by postnov on 25.03.2016.
 */
public class DBUtils {

    /*
        Очищаем некий "кэш" с исполнителями, в том случае,
        если есть подключение к сети
    */
    public static boolean deleteCache(Context context)
    {
        if (Utils.checkNetworkConnection(context)) {
            context.getContentResolver().delete(Artist.CONTENT_URI, null, null);
            return true;
        }
        return false;
    }
    /*
        Добавляем нового исполнителя
    */
    public static boolean insertArtist(Context context, ContentValues artist)
    {
        context.getContentResolver().insert(Artist.CONTENT_URI, artist);
        return true;
    }

    /*
        Проверяем, есть ли записи в кеше
    */
    public static boolean cacheIsEmpty(Context context) {
        boolean hasRows = false;
        Cursor cursor;
        cursor = context.getContentResolver().query(Artist.CONTENT_URI, null, null, null,null);

        if (cursor != null) {
            hasRows = cursor.moveToFirst();
            cursor.close();
        }
        return hasRows;
    }
}
