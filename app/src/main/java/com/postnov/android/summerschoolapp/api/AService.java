package com.postnov.android.summerschoolapp.api;

import com.postnov.android.summerschoolapp.model.ArtistModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by postnov on 12.04.2016.
 */
public interface AService {

    String ENDPOINT = "http://download.cdn.yandex.net/";

    @GET("mobilization-2016/artists.json")
    Call<List<ArtistModel>> listArtists();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
