package com.postnov.android.summerschoolapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.postnov.android.summerschoolapp.provider.ArtistsContract.Artist;

/**
 * Created by postnov on 25.03.2016.
 */
public class ArtistsDatabase extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "feed.db";

    private static final String TYPE_TEXT = " TEXT";
    private static final String TYPE_INTEGER = " INTEGER";
    private static final String COMMA_SEP = ",";

    /** SQL выражение для создания "artist" таблицы. */

    private static final String SQL_CREATE_ARTISTS =
            "CREATE TABLE " + Artist.TABLE_NAME + " (" +
                    Artist._ID + " INTEGER PRIMARY KEY," +
                    Artist.COLUMN_ARTIST_ID + TYPE_INTEGER + COMMA_SEP +
                    Artist.COLUMN_ARTIST_NAME + TYPE_TEXT + COMMA_SEP +
                    Artist.COLUMN_GENRES + TYPE_TEXT + COMMA_SEP +
                    Artist.COLUMN_TRACKS + TYPE_INTEGER + COMMA_SEP +
                    Artist.COLUMN_ALBUMS + TYPE_INTEGER + COMMA_SEP +
                    Artist.COLUMN_DESC + TYPE_TEXT + COMMA_SEP +
                    Artist.COLUMN_COVER_SMALL + TYPE_TEXT + COMMA_SEP +
                    Artist.COLUMN_COVER_BIG + TYPE_INTEGER + ")";


    /** SQL выражение для удаления "artist" таблицы. */
    private static final String SQL_DELETE_ARTISTS =
            "DROP TABLE IF EXISTS " + Artist.TABLE_NAME;

    public ArtistsDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ARTISTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ARTISTS);
        onCreate(db);
    }
}
