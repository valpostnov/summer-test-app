package com.postnov.android.summerschoolapp.artists;

import android.app.Fragment;
import android.os.Bundle;

import com.postnov.android.summerschoolapp.artists.interfaces.ArtistsView;
import com.postnov.android.summerschoolapp.model.ArtistModel;

import java.util.List;

public class ArtistsFragment extends Fragment implements ArtistsView
{
    public ArtistsFragment() {}

    public static ArtistsFragment newInstance()
    {
        return new ArtistsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showArtists(List<ArtistModel> artists) {

    }

    @Override
    public void showProgressView(boolean show) {

    }

    @Override
    public void showError(Throwable t) {

    }
}
