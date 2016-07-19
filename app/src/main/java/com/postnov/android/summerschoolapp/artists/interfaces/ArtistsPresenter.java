package com.postnov.android.summerschoolapp.artists.interfaces;

/**
 * Created by platon on 15.07.2016.
 */
public interface ArtistsPresenter<V>
{
    void fetchArtists(boolean forceLoad, int[] range);
    void bind(V view);
    void unbind();
    void unsubscribe();
}
