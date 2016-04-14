package com.postnov.android.summerschoolapp.ui.fragments;

import android.app.ActivityOptions;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.postnov.android.summerschoolapp.R;
import com.postnov.android.summerschoolapp.db.DBUtils;
import com.postnov.android.summerschoolapp.service.LoaderService;
import com.postnov.android.summerschoolapp.ui.activity.DetailsActivity;
import com.postnov.android.summerschoolapp.ui.adapter.ArtistsAdapter;
import com.postnov.android.summerschoolapp.utils.Const;
import com.postnov.android.summerschoolapp.utils.Utils;

import static com.postnov.android.summerschoolapp.provider.ArtistsContract.Artist;
import static com.postnov.android.summerschoolapp.utils.Const.LOADED_ARTISTS_COUNT;

public class ArtistsFragment extends SwipeRefreshListFragment implements LoaderManager.LoaderCallbacks<Cursor>
{
    private ArtistsAdapter mAdapter;
    private View mFooter;

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
            runService(false, 0);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new ArtistsAdapter(getActivity(), null, 0);
        mFooter = getActivity().getLayoutInflater().inflate(R.layout.item_footer, null);

        Button buttonLoadMore = (Button) mFooter.findViewById(R.id.loadMoreBtn);
        buttonLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runService(false, getListAdapter().getCount());
            }
        });

        setListAdapter(mAdapter);
        getListView().addFooterView(mFooter);
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
                runService(true, 0);
            }
        });
    }

    @Override
    public void onDestroyView() {
        getListView().removeFooterView(mFooter);
        super.onDestroyView();
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
            if (!Utils.isNetworkAvailable(getActivity()))
            {
                setEmptyText(getText(R.string.list_empty));
            }
            else
            {
                mAdapter.swapCursor(data);
                setEmptyText(getText(R.string.download_list));
                getSwipeRefreshLayout().post(new Runnable() {
                    @Override public void run() {
                        setRefreshing(true);
                    }
                });
            }
        }
        else
        {
            mAdapter.swapCursor(data);
            setRefreshing(false);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader)
    {
        mAdapter.swapCursor(null);
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

    private void runService(boolean wipeCache, int adapterItemCount)
    {
        if (Utils.isNetworkAvailable(getActivity()))
        {
            DBUtils.wipeCache(getActivity(), wipeCache);
            Intent intent = new Intent(getActivity(), LoaderService.class);
            intent.putExtra(LOADED_ARTISTS_COUNT, adapterItemCount);
            getActivity().startService(intent);
        }
        else
        {
            if (getSwipeRefreshLayout() != null && isRefreshing()) setRefreshing(false);
            Utils.showToast(getActivity(), getString(R.string.no_network_connection));
        }
    }

    private void initLoader()
    {
        getLoaderManager().initLoader(0, null, this);
    }
}
