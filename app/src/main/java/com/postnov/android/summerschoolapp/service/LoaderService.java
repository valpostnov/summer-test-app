package com.postnov.android.summerschoolapp.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;

import com.postnov.android.summerschoolapp.db.DBUtils;
import com.postnov.android.summerschoolapp.model.ArtistModel;
import com.postnov.android.summerschoolapp.api.SSAService;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

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
        ContentValues cv;

        try
        {
            SSAService yaService = SSAService.retrofit.create(SSAService.class);
            Call<List<ArtistModel>> call = yaService.listArtists();
            List<ArtistModel> artists = call.execute().body();

            for (ArtistModel a : artists)
            {
                cv = DBUtils.toCV(a);
                DBUtils.insertArtist(this, cv);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
