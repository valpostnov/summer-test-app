package com.postnov.android.summerschoolapp.artists;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.postnov.android.summerschoolapp.R;
import com.postnov.android.summerschoolapp.data.entity.Artist;
import com.postnov.android.summerschoolapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by postnov on 14.04.2016.
 */
public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ArtistsViewHolder>
{
    private List<Artist> artists;
    private OnAdapterListener onAdapterListener;
    private Context context;

    public interface OnAdapterListener
    {
        void onItemClick(View view, int position);
        void onLoadMore(int count);
    }

    public ArtistsAdapter(Context context)
    {
        this.context = context;
        artists = new ArrayList<>();
    }

    @Override
    public ArtistsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.item_artist, parent, false);
        return new ArtistsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ArtistsViewHolder holder, int position)
    {
        Artist artist = getList().get(position);
        holder.bind(artist);
    }

    @Override
    public int getItemCount()
    {
        if (null == artists) return 0;
        return artists.size();
    }

    public void changeList(List<Artist> newList)
    {
        artists.addAll(newList);
        notifyDataSetChanged();
    }

    public void clear()
    {
        artists.clear();
    }

    public List<Artist> getList()
    {
        return artists;
    }

    public class ArtistsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        @BindView(R.id.item_artist_cover) ImageView artistImage;
        @BindView(R.id.item_artist_name) TextView artistName;
        @BindView(R.id.item_artist_genres) TextView artistGenre;
        @BindView(R.id.item_artist_albums_songs) TextView albumsAndTracks;

        public ArtistsViewHolder(View view)
        {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            int adapterPosition = getAdapterPosition();
            onAdapterListener.onItemClick(v, adapterPosition);
        }

        public void bind(Artist artist)
        {
            String albums = context.getResources().getQuantityString(
                    R.plurals.numberOfAlbums,
                    artist.getAlbums(),
                    artist.getAlbums());

            String tracks = context.getResources().getQuantityString(
                    R.plurals.numberOfTracks,
                    artist.getTracks(),
                    artist.getTracks());

            String imageUrl = artist.getCover().getSmall();

            artistName.setText(artist.getName());
            artistGenre.setText(artist.getGenres());
            albumsAndTracks.setText(Utils.concatStrings(tracks, ", ", albums));

            Glide.with(context)
                    .load(imageUrl)
                    .override(100, 100)
                    .into(artistImage);

            if (getAdapterPosition() == getItemCount() - 1)
            { onAdapterListener.onLoadMore(getItemCount()); }
        }
    }

    public void setOnAdapterListener(OnAdapterListener listener)
    {
        onAdapterListener = listener;
    }
}
