package com.postnov.android.summerschoolapp.ui.fragments;

import android.app.ActivityOptions;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.postnov.android.summerschoolapp.R;
import com.postnov.android.summerschoolapp.db.DBUtils;
import com.postnov.android.summerschoolapp.service.LoaderService;
import com.postnov.android.summerschoolapp.ui.activity.DetailsActivity;
import com.postnov.android.summerschoolapp.ui.adapter.ArtistsAdapter;
import com.postnov.android.summerschoolapp.utils.Utils;

import static com.postnov.android.summerschoolapp.provider.ArtistsContract.Artist;

public class ArtistsFragment extends SwipeRefreshListFragment implements LoaderManager.LoaderCallbacks<Cursor>
{

    private ArtistsAdapter mAdapter;

    private static final String[] PROJECTION = new String[]
            {
                Artist._ID,
                Artist.COLUMN_ARTIST_ID,
                Artist.COLUMN_ARTIST_NAME,
                Artist.COLUMN_GENRES,
                Artist.COLUMN_TRACKS,
                Artist.COLUMN_ALBUMS,
                Artist.COLUMN_DESC,
                Artist.COLUMN_COVER_SMALL,
                Artist.COLUMN_COVER_BIG
            };


    public static final int COLUMN_NAME = 2;
    public static final int COLUMN_GENRES = 3;
    public static final int COLUMN_TRACKS = 4;
    public static final int COLUMN_ALBUMS = 5;
    public static final int COLUMN_DESC = 6;
    public static final int COLUMN_COVER_SMALL = 7;
    public static final int COLUMN_COVER_BIG = 8;

    public ArtistsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if (!DBUtils.cacheIsExist(getActivity()))
        {
            runService();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new ArtistsAdapter(getActivity(), null, 0);
        setListAdapter(mAdapter);
        initLoader();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                if (Utils.checkNetworkConnection(getActivity()))
                {
                    DBUtils.deleteCache(getActivity());
                    runService();
                }
                else
                {
                    setRefreshing(false);
                    Toast.makeText(getActivity(), getText(R.string.no_network_connection),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args)
    {
        return new CursorLoader(getActivity(),
                Artist.CONTENT_URI,                 // URI
                PROJECTION,                         // Projection
                null,                               // Selection
                null,                               // Selection args
                null);                              // Sort
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data)
    {
        if (!data.moveToFirst())
        {
            mAdapter.changeCursor(null);
            if (!Utils.checkNetworkConnection(getActivity()))
            {
                setEmptyText(getText(R.string.list_empty));
            }
            else
            {
                setEmptyText(getText(R.string.download_list));
            }
        }
        else
        {
            mAdapter.changeCursor(data);
            setRefreshing(false);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader)
    {
        mAdapter.changeCursor(null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);

        Cursor c = (Cursor) mAdapter.getItem(position);
        String artistName = c.getString(COLUMN_NAME);
        String genres = c.getString(COLUMN_GENRES);
        String tracks = c.getString(COLUMN_TRACKS);
        String albums = c.getString(COLUMN_ALBUMS);
        String desc = c.getString(COLUMN_DESC);
        String coverLink = c.getString(COLUMN_COVER_BIG);

        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(Artist.COLUMN_ARTIST_NAME, artistName);
        intent.putExtra(Artist.COLUMN_GENRES, genres);
        intent.putExtra(Artist.COLUMN_TRACKS, tracks);
        intent.putExtra(Artist.COLUMN_ALBUMS, albums);
        intent.putExtra(Artist.COLUMN_DESC, desc);
        intent.putExtra(Artist.COLUMN_COVER_BIG, coverLink);

        View sharedView = v.findViewById(R.id.item_artist_cover);
        String transitionName = getString(R.string.shared_element);

        ActivityOptions transitionAO;
        transitionAO = ActivityOptions.makeSceneTransitionAnimation(getActivity(), sharedView, transitionName);
        startActivity(intent, transitionAO.toBundle());
    }

    private void runService()
    {
        Intent intent = new Intent(getActivity(), LoaderService.class);
        getActivity().startService(intent);
    }

    private void initLoader()
    {
        getLoaderManager().initLoader(0, null, this);
    }

}
