package com.postnov.android.summerschoolapp.artists.interfaces;

import com.postnov.android.summerschoolapp.data.entity.Artist;

import java.util.List;

/**
 * Created by platon on 15.07.2016.
 */
public interface ArtistsView
{
    void showArtists(List<Artist> artists);
    void showProgressView(boolean show);
    void showError(String error);
}
