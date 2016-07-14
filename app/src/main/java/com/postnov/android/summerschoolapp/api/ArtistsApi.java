package com.postnov.android.summerschoolapp.api;

import com.postnov.android.summerschoolapp.model.ArtistModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by postnov on 12.04.2016.
 */
public interface ArtistsApi {

    String ENDPOINT = "http://download.cdn.yandex.net/";

    @GET("mobilization-2016/artists.json")
    Observable<List<ArtistModel>> listArtists();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();
}
