package com.postnov.android.summerschoolapp.artists;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.postnov.android.summerschoolapp.R;
import com.postnov.android.summerschoolapp.base.BaseFragment;
import com.postnov.android.summerschoolapp.data.entity.Artist;
import com.postnov.android.summerschoolapp.utils.Utils;

import butterknife.BindView;

/**
 * Created by postnov on 25.03.2016.
 */
public class DetailsFragment extends BaseFragment
{
    public static final String ARTIST_OBJECT = "com.postnov.summer.artist";

    @BindView(R.id.detail_cover) ImageView coverImageView;
    @BindView(R.id.detail_genres) TextView genresTextView;
    @BindView(R.id.detail_albums_songs) TextView albumsTracksTextView;
    @BindView(R.id.detail_desc) TextView descTextView;

    public static DetailsFragment newInstance(Artist artist)
    {
        DetailsFragment fragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARTIST_OBJECT, artist);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected int getLayout()
    {
        return R.layout.fragment_details;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        Resources res = getResources();
        Artist artist = (Artist) getArguments().getSerializable(ARTIST_OBJECT);

        String albums = res.getQuantityString(
                R.plurals.numberOfAlbums,
                artist.getAlbums(),
                artist.getAlbums());

        String tracks = res.getQuantityString(
                R.plurals.numberOfTracks,
                artist.getTracks(),
                artist.getTracks());

        getToolbarProvider().updateToolbar(artist.getName(), true);

        genresTextView.setText(artist.getGenres());
        albumsTracksTextView.setText(Utils.concatStrings(tracks, ", " , albums));
        descTextView.setText(artist.getDesc());

        Glide.with(this).load(artist.getCover().getCoverBig()).fitCenter().into(coverImageView);
    }
}
