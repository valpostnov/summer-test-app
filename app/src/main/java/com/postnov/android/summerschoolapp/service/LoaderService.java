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

import static com.postnov.android.summerschoolapp.utils.Const.*;

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
        int loadedCount = intent.getIntExtra(LOADED_ARTISTS_COUNT, 0);

        try
        {
            AService aService = AService.retrofit.create(AService.class);

            Call<List<ArtistModel>> call = aService.listArtists();

            Response<List<ArtistModel>> response = call.execute();

            if (response.code() == STATE_ACTION_200)
            {
                List<ArtistModel> artists = load(response.body(), loadedCount);

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
            sendStatus(STATE_ACTION_UNKNOWN);
            Log.e(TAG, e.getMessage());
        }
    }

    private void sendStatus(int status) {
        Intent localIntent = new Intent(BROADCAST_ACTION);
        localIntent.putExtra(EXTENDED_DATA_STATUS, status);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }

    /*
        allEntries - количество всех артистов на сервере
        loadedEntries - количество артистов ранее загруженных
     */
    private List<ArtistModel> load(List<ArtistModel> list, int loadedEntries) {

        int allEntries = list.size();

        // если записи еще не загружены, загружаем первые 20
        if (loadedEntries == 0)
        {
            return list.subList(0, 20);
        }

        // если загруженных записей меньше чем всех записей
        if (loadedEntries < allEntries)
        {
            // если записей, которых осталось загрузить  <= 20
            if (allEntries - loadedEntries <= 20)
            {
                // то грузим оставшиеся
                return list.subList(loadedEntries, loadedEntries + (allEntries - loadedEntries));
            }
            else
            {
                // иначе грузим следующие 20 записей
                return list.subList(loadedEntries, loadedEntries + 20);
            }
        }
        // в случае, когда все записи загружены, шлем статус-сообщение о данном факте
        sendStatus(STATE_ACTION_ALL_DOWNLOADED);
        return list.subList(0, 0);
    }
}
