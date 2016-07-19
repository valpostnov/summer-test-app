package com.postnov.android.summerschoolapp.data.source.remote;

import com.postnov.android.summerschoolapp.api.ArtistsApi;
import com.postnov.android.summerschoolapp.data.entity.Artist;
import com.postnov.android.summerschoolapp.data.source.IDataSource;

import java.util.ArrayList;
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
    public Observable<List<Artist>> getList(int loaded)
    {
        return api.listArtists();
    }

    @Override
    public void delete() {}
}
