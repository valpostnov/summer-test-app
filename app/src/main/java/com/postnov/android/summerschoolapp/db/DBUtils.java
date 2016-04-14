package com.postnov.android.summerschoolapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.postnov.android.summerschoolapp.model.ArtistModel;

import static com.postnov.android.summerschoolapp.provider.ArtistsContract.Artist;

/**
 * Created by postnov on 25.03.2016.
 */
public class DBUtils {

    /*
        Очищаем некий "кэш" с исполнителями
    */
    public static int deleteCache(Context context, boolean delete)
    {
        if (delete) return context.getContentResolver().delete(Artist.CONTENT_URI, null, null);
        return -1;
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
    public static boolean cacheIsExist(Context context) {
        boolean hasRows = false;
        Cursor cursor;
        cursor = context.getContentResolver().query(Artist.CONTENT_URI, null, null, null,null);

        if (cursor != null) {
            hasRows = cursor.moveToFirst();
            cursor.close();
        }
        return hasRows;
    }

    /*
        Конвертим ArtistModel в ContentValues
     */
    public static ContentValues toCV(ArtistModel artist) {
        ContentValues cv = new ContentValues();
        cv.put(Artist.COLUMN_ARTIST_ID, artist.getId());
        cv.put(Artist.COLUMN_ARTIST_NAME, artist.getName());
        cv.put(Artist.COLUMN_TRACKS, artist.getTracks());
        cv.put(Artist.COLUMN_ALBUMS, artist.getAlbums());
        cv.put(Artist.COLUMN_DESC, artist.getDesc());
        cv.put(Artist.COLUMN_COVER_SMALL, artist.getCover().getCoverSmall());
        cv.put(Artist.COLUMN_COVER_BIG, artist.getCover().getCoverBig());
        cv.put(Artist.COLUMN_GENRES, artist.getGenres());
        return cv;
    }
}
