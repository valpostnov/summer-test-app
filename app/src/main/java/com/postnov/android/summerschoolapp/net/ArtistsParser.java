package com.postnov.android.summerschoolapp.net;

import android.content.ContentValues;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import static com.postnov.android.summerschoolapp.provider.ArtistsContract.Artist;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by postnov on 24.03.2016.
 */
public class ArtistsParser implements Parser<String> {

    private static final String LOG_TAG = ArtistsParser.class.getSimpleName();

    public List<ContentValues> parse(String artistsJsonStr)
    {
        final String TAG_ID = "id";
        final String TAG_NAME = "name";
        final String TAG_GENRES = "genres";
        final String TAG_TRACKS = "tracks";
        final String TAG_ALBUMS = "albums";
        final String TAG_DESC = "description";
        final String TAG_COVER = "cover";
        final String TAG_COVER_SMALL = "small";
        final String TAG_COVER_BIG = "big";

        List<ContentValues> artistsList = new ArrayList<>();

        try
        {
            JSONArray artistsArray = new JSONArray(artistsJsonStr);

            for (int i = 0; i < artistsArray.length(); i++)
            {
                JSONObject artistsJson = artistsArray.getJSONObject(i);
                JSONObject coverJson = artistsJson.getJSONObject(TAG_COVER);
                JSONArray genresArray = artistsJson.getJSONArray(TAG_GENRES);

                int artistId;
                int artistTracks;
                int artistAlbums;
                String artistDesc;
                String artistName;
                StringBuilder artistsGenres = new StringBuilder();

                artistId = artistsJson.getInt(TAG_ID);
                artistTracks = artistsJson.getInt(TAG_TRACKS);
                artistAlbums = artistsJson.getInt(TAG_ALBUMS);
                artistName = artistsJson.getString(TAG_NAME);
                artistDesc = artistsJson.getString(TAG_DESC);

                if (genresArray.length() > 1)
                {
                    artistsGenres.append(genresArray.getString(0));
                    for (int g = 1; g < genresArray.length(); g++)
                    {
                        artistsGenres.append(", ");
                        artistsGenres.append(genresArray.getString(g));
                    }
                }
                else if (genresArray.length() == 0)
                {
                    artistsGenres.append("none");
                }
                else
                {
                    artistsGenres.append(genresArray.getString(0));
                }


                String artistCoverSmall;
                String artistCoverBig;

                artistCoverSmall = coverJson.getString(TAG_COVER_SMALL);
                artistCoverBig = coverJson.getString(TAG_COVER_BIG);

                ContentValues cv = new ContentValues();
                cv.put(Artist.COLUMN_ARTIST_ID, artistId);
                cv.put(Artist.COLUMN_ARTIST_NAME, artistName);
                cv.put(Artist.COLUMN_TRACKS, artistTracks);
                cv.put(Artist.COLUMN_ALBUMS, artistAlbums);
                cv.put(Artist.COLUMN_DESC, artistDesc);
                cv.put(Artist.COLUMN_COVER_SMALL, artistCoverSmall);
                cv.put(Artist.COLUMN_COVER_BIG, artistCoverBig);
                cv.put(Artist.COLUMN_GENRES, artistsGenres.toString());

                artistsList.add(cv);
            }
        }
        catch (JSONException e)
        {
            Log.e(LOG_TAG, e.getMessage(), e);
        }

        return artistsList;
    }
}
