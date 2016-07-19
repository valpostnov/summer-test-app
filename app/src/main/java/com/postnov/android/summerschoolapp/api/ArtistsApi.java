package com.postnov.android.summerschoolapp.api;

import com.postnov.android.summerschoolapp.data.entity.Artist;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by postnov on 12.04.2016.
 */
public interface ArtistsApi
{
    @GET("mobilization-2016/artists.json")
    Observable<List<Artist>> listArtists();
}
