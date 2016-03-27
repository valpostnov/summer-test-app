package com.postnov.android.summerschoolapp.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.postnov.android.summerschoolapp.R;
import com.postnov.android.summerschoolapp.ui.fragments.ArtistsFragment;
import com.postnov.android.summerschoolapp.utils.Utils;

/**
 * Created by postnov on 24.03.2016.
 */
public class ArtistsAdapter extends CursorAdapter
{

    public ArtistsAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        int layoutId = R.layout.item_artist;

        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        Glide.with(context)
                .load(cursor.getString(ArtistsFragment.COLUMN_COVER_SMALL))
                .asBitmap()
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .thumbnail(0.1f)
                .into(viewHolder.cover);

        String artistName = cursor.getString(ArtistsFragment.COLUMN_NAME);
        String genres = cursor.getString(ArtistsFragment.COLUMN_GENRES);
        String albums = cursor.getString(ArtistsFragment.COLUMN_ALBUMS);
        String tracks = cursor.getString(ArtistsFragment.COLUMN_TRACKS);

        StringBuilder albumsAndTracks = new StringBuilder();
        albumsAndTracks.append(Utils.getCorrectAlbumString(albums));
        albumsAndTracks.append(", ");
        albumsAndTracks.append(Utils.getCorrectTrackString(tracks));

        viewHolder.name.setText(artistName);
        viewHolder.genres.setText(genres);
        viewHolder.albumsAndTracks.setText(albumsAndTracks);
    }

    public static class ViewHolder
    {
        public final ImageView cover;
        public final TextView name;
        public final TextView genres;
        public final TextView albumsAndTracks;

        public ViewHolder(View view)
        {
            cover = (ImageView) view.findViewById(R.id.item_artist_cover);
            name = (TextView) view.findViewById(R.id.item_artist_name);
            genres = (TextView) view.findViewById(R.id.item_artist_genres);
            albumsAndTracks = (TextView) view.findViewById(R.id.item_artist_albums_songs);
        }
    }
}
