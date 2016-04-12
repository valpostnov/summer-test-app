package com.postnov.android.summerschoolapp.ui.fragments;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.postnov.android.summerschoolapp.R;
import com.postnov.android.summerschoolapp.utils.Utils;

import static com.postnov.android.summerschoolapp.provider.ArtistsContract.Artist;

/**
 * Created by postnov on 25.03.2016.
 */
public class DetailsArtistFragment extends Fragment {

    public DetailsArtistFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View v) {

        Intent intent = getActivity().getIntent();

        ActionBar actionBar = getActivity().getActionBar();
        ImageView coverView = (ImageView) v.findViewById(R.id.detail_cover);
        TextView genresView = (TextView) v.findViewById(R.id.detail_genres);
        TextView albumsTracksView = (TextView) v.findViewById(R.id.detail_albums_songs);
        TextView descriptionView = (TextView) v.findViewById(R.id.detail_desc);

        String artistsName;
        String coverLink;
        String artistsGenres;
        String artistsTracks;
        String artistsAlbum;
        String artistsDesc;
        StringBuilder albumsAndTracks = new StringBuilder();


        artistsName = intent.getStringExtra(Artist.COLUMN_ARTIST_NAME);
        coverLink = intent.getStringExtra(Artist.COLUMN_COVER_BIG);
        artistsGenres = intent.getStringExtra(Artist.COLUMN_GENRES);
        artistsAlbum = intent.getStringExtra(Artist.COLUMN_ALBUMS);
        artistsTracks = intent.getStringExtra(Artist.COLUMN_TRACKS);
        artistsDesc = intent.getStringExtra(Artist.COLUMN_DESC);
        albumsAndTracks.append(Utils.getCorrectTrackString(artistsTracks));
        albumsAndTracks.append(", ");
        albumsAndTracks.append(Utils.getCorrectAlbumString(artistsAlbum));


        actionBar.setTitle(artistsName);
        Glide.with(this)
                .load(coverLink)
                .dontAnimate()
                .thumbnail(0.2f)
                .fitCenter()
                .into(coverView);

        genresView.setText(artistsGenres);
        albumsTracksView.setText(albumsAndTracks);
        descriptionView.setText(artistsDesc);
    }
}
