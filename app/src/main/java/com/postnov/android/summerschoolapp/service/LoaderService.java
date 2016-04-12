package com.postnov.android.summerschoolapp.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.postnov.android.summerschoolapp.api.AService;
import com.postnov.android.summerschoolapp.db.DBUtils;
import com.postnov.android.summerschoolapp.model.ArtistModel;
import com.postnov.android.summerschoolapp.utils.Const;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by postnov on 23.02.2016.
 */
public class LoaderService extends IntentService
{
    private static final String TAG = LoaderService.class.getSimpleName();

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
            AService aService = AService.retrofit.create(AService.class);

            Call<List<ArtistModel>> call = aService.listArtists();

            Response<List<ArtistModel>> response = call.execute();

            if (response.code() == Const.STATE_ACTION_200)
            {
                List<ArtistModel> artists = response.body();

                for (ArtistModel a : artists)
                {
                    cv = DBUtils.toCV(a);
                    DBUtils.insertArtist(this, cv);
                }
            }
            else
            {
                sendStatus(response.code());
            }
        }
        catch (IOException e)
        {
            sendStatus(Const.STATE_ACTION_UNKNOWN);
            Log.e(TAG, e.getMessage());
        }
    }

    private void sendStatus(int status) {
        Intent localIntent = new Intent(Const.BROADCAST_ACTION);
        localIntent.putExtra(Const.EXTENDED_DATA_STATUS, status);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }
}
