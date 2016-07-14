package com.postnov.android.summerschoolapp.artists.interfaces;

import com.postnov.android.summerschoolapp.model.ArtistModel;

import java.util.List;

/**
 * Created by platon on 15.07.2016.
 */
public interface ArtistsView
{
    void showArtists(List<ArtistModel> artists);
    void showProgressView(boolean show);
    void showError(Throwable t);
}
