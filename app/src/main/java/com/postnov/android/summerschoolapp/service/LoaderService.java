package com.postnov.android.summerschoolapp.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;

import com.postnov.android.summerschoolapp.db.DBUtils;
import com.postnov.android.summerschoolapp.net.ArtistsParser;
import com.postnov.android.summerschoolapp.net.NetworkUtils;
import com.postnov.android.summerschoolapp.net.Parser;
import com.postnov.android.summerschoolapp.ui.fragments.ArtistsFragment;

import java.io.IOException;
import java.util.List;

/**
 * Created by postnov on 23.02.2016.
 */
public class LoaderService extends IntentService
{
    private static final String LOG_TAG = LoaderService.class.getSimpleName();

    public LoaderService()
    {
        super("LoaderService");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        final String url = intent.getStringExtra(ArtistsFragment.URL_EXTRA);
        final Parser<String> parser = new ArtistsParser();

        try
        {
            //получаем json
            String json = NetworkUtils.getSourceString(url);
            //парсим в лист
            List<ContentValues> artists = parser.parse(json);

            for (ContentValues cv: artists)
            {
                //добавляем в базу
                DBUtils.insertArtist(this, cv);
            }
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
    }
}
