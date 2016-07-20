package com.postnov.android.summerschoolapp.artists;

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
import com.postnov.android.summerschoolapp.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by postnov on 25.03.2016.
 */
public class DetailsFragment extends Fragment
{
    public static final String ARTIST_OBJECT = "com.postnov.summer.artist";

    @BindView(R.id.detail_cover) ImageView mCoverImageView;
    @BindView(R.id.detail_genres) TextView mGenresTextView;
    @BindView(R.id.detail_albums_songs) TextView mAlbumsTracksTextView;
    @BindView(R.id.detail_desc) TextView mDescTextView;

    private Unbinder unbinder;

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
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        Artist artist = (Artist) getArguments().getSerializable(ARTIST_OBJECT);
        String albums = getResources().getQuantityString(R.plurals.numberOfAlbums, artist.getAlbums());
        String tracks = getResources().getQuantityString(R.plurals.numberOfTracks, artist.getTracks());

        ((ArtistsActivity) getActivity()).setupActionBar(artist.getName(), true);

        mGenresTextView.setText(artist.getGenres());
        mAlbumsTracksTextView.setText(Utils.concatStrings(tracks,", " , albums));
        mDescTextView.setText(artist.getDesc());

        Glide.with(this).load(artist.getCover().getCoverBig()).fitCenter().into(mCoverImageView);
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }
}
