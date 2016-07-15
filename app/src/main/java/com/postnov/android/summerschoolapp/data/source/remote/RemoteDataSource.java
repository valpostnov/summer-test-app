package com.postnov.android.summerschoolapp.data.source.remote;

import com.postnov.android.summerschoolapp.api.ArtistsApi;
import com.postnov.android.summerschoolapp.data.entity.Artist;
import com.postnov.android.summerschoolapp.data.source.IDataSource;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by platon on 15.07.2016.
 */
public class RemoteDataSource implements IDataSource
{
    private static final String ENDPOINT = "http://download.cdn.yandex.net/";
    private ArtistsApi api;

    public RemoteDataSource()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        api = retrofit.create(ArtistsApi.class);
    }

    @Override
    public Observable<List<Artist>> getList(final int loaded)
    {
        return api.listArtists().map(new Func1<List<Artist>, List<Artist>>()
        {
            @Override
            public List<Artist> call(List<Artist> artists)
            {
                return load(artists, loaded);
            }
        });
    }

    private List<Artist> load(List<Artist> list, int loadedEntries)
    {

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

        return list.subList(0, 0);
    }
}
