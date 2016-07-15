package com.postnov.android.summerschoolapp.artists;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.postnov.android.summerschoolapp.R;
import com.postnov.android.summerschoolapp.data.entity.Artist;

/**
 * Created by postnov on 25.03.2016.
 */
public class DetailsFragment extends Fragment {

    public static final String ARTIST_OBJECT = "com.postnov.summer.artist";
    public DetailsFragment() {}

    public static DetailsFragment newInstance(Artist artist)
    {
        DetailsFragment fragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARTIST_OBJECT, artist);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View v)
    {
        Artist artist = (Artist) getArguments().getSerializable(ARTIST_OBJECT);

        ((ArtistsActivity) getActivity()).setupActionBar(artist.getName(), true);
        ImageView coverView = (ImageView) v.findViewById(R.id.detail_cover);
        TextView genresView = (TextView) v.findViewById(R.id.detail_genres);
        TextView albumsTracksView = (TextView) v.findViewById(R.id.detail_albums_songs);
        TextView descriptionView = (TextView) v.findViewById(R.id.detail_desc);

        genresView.setText(artist.getGenres());
        albumsTracksView.setText(artist.getAlbumsAndTracks());
        descriptionView.setText(artist.getDesc());
        Glide.with(this).load(artist.getCover().getCoverBig()).fitCenter().into(coverView);
    }
}
