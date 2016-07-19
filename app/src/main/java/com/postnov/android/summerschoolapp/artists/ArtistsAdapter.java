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

import java.util.List;

/**
 * Created by postnov on 14.04.2016.
 */
public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ArtistsViewHolder>
{
    private View mEmptyView;
    private List<Artist> mArtists;
    private OnItemClickListener mOnItemClickListener;
    private OnEndlessListener mOnEndlessListener;
    private Context mContext;

    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
    }

    public interface OnEndlessListener
    {
        void loadMore(int count);
    }

    public ArtistsAdapter(Context context, View emptyView)
    {
        this.mContext = context;
        mEmptyView = emptyView;
    }

    @Override
    public ArtistsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_artist, parent, false);
        return new ArtistsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ArtistsViewHolder holder, int position)
    {
        Artist artist = getList().get(position);

        Glide.with(mContext).load(artist.getCover().getCoverSmall())
                .override(100, 100)
                .into(holder.cover);

        holder.name.setText(artist.getName());
        holder.genres.setText(artist.getGenres());
        holder.albumsAndTracks.setText(artist.getAlbumsAndTracks());

        if (position == getItemCount() - 1)
        { mOnEndlessListener.loadMore(getItemCount()); }
    }

    @Override
    public int getItemCount()
    {
        if (null == mArtists) return 0;
        return mArtists.size();
    }

    public void changeList(List<Artist> newList)
    {
        mArtists = newList;
        notifyDataSetChanged();
        mEmptyView.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    public void addNext(List<Artist> nextItems)
    {
        mArtists.addAll(nextItems);
        notifyDataSetChanged();
        mEmptyView.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    public List<Artist> getList()
    {
        return mArtists;
    }

    public class ArtistsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public final ImageView cover;
        public final TextView name;
        public final TextView genres;
        public final TextView albumsAndTracks;

        public ArtistsViewHolder(View view)
        {
            super(view);
            cover = (ImageView) view.findViewById(R.id.item_artist_cover);
            name = (TextView) view.findViewById(R.id.item_artist_name);
            genres = (TextView) view.findViewById(R.id.item_artist_genres);
            albumsAndTracks = (TextView) view.findViewById(R.id.item_artist_albums_songs);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            int adapterPosition = getAdapterPosition();
            mOnItemClickListener.onItemClick(v, adapterPosition);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mOnItemClickListener = listener;
    }

    public void setOnEndlessListener(OnEndlessListener listener)
    {
        mOnEndlessListener = listener;
    }
}
