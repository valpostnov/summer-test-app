package com.postnov.android.summerschoolapp.artists.interfaces;

/**
 * Created by platon on 15.07.2016.
 */
public interface ArtistsPresenter
{
    void fetchArtists(boolean forceLoad, int[] range);
    void bind(ArtistsView view);
    void unbind();
    void unsubscribe();
}
