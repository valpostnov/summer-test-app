package com.postnov.android.summerschoolapp.provider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by postnov on 25.03.2016.
 */
public class ArtistsContract {


    public static final String CONTENT_AUTHORITY = "com.postnov.android.summerschoolapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String PATH_ARTISTS = "artists";

    public static class Artist implements BaseColumns {

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.summerschoolapp.artists";

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.summerschoolapp.artist";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ARTISTS).build();


        public static final String TABLE_NAME = "artist";

        public static final String COLUMN_ARTIST_ID = "artist_id";

        public static final String COLUMN_ARTIST_NAME = "name";

        public static final String COLUMN_GENRES = "genres";

        public static final String COLUMN_TRACKS = "tracks";

        public static final String COLUMN_ALBUMS = "albums";

        public static final String COLUMN_DESC = "description";

        public static final String COLUMN_COVER_SMALL = "cover_small";

        public static final String COLUMN_COVER_BIG = "cover_big";
    }
}
