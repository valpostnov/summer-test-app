package com.postnov.android.summerschoolapp.artists;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.postnov.android.summerschoolapp.R;
import com.postnov.android.summerschoolapp.artists.interfaces.ArtistsPresenter;
import com.postnov.android.summerschoolapp.artists.interfaces.ArtistsView;
import com.postnov.android.summerschoolapp.data.entity.Artist;
import com.postnov.android.summerschoolapp.data.source.IDataSource;
import com.postnov.android.summerschoolapp.data.source.Repository;
import com.postnov.android.summerschoolapp.data.source.local.CacheImpl;
import com.postnov.android.summerschoolapp.data.source.local.ICache;
import com.postnov.android.summerschoolapp.data.source.remote.RemoteDataSource;
import com.postnov.android.summerschoolapp.utils.Utils;

import java.io.File;
import java.util.List;

public class ArtistsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        ArtistsAdapter.OnItemClickListener, ArtistsAdapter.OnEndlessListener, ArtistsView
{
    private static final String TAG = "ArtistsFragment";
    private static int sCachedLoadedArtists = 20;

    private ArtistsAdapter mArtistsAdapter;
    private SwipeRefreshLayout mSwipeLayout;
    private ArtistsPresenter mPresenter;
    private RecyclerView mRecyclerView;
    private View mEmptyView;

    public static ArtistsFragment newInstance()
    {
        return new ArtistsFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        ((ArtistsActivity) getActivity()).setupActionBar(getString(R.string.app_name), false);

        File cacheDir = getActivity().getCacheDir();
        ICache<Artist> cache = new CacheImpl(cacheDir);
        IDataSource dataSource = new RemoteDataSource();

        mPresenter = new ArtistsPresenterImpl(Repository.getInstance(cache, dataSource));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_artists, container, false);
        initViews(rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        mArtistsAdapter = new ArtistsAdapter(getActivity(), mEmptyView);
        mArtistsAdapter.setOnItemClickListener(this);
        mArtistsAdapter.setOnEndlessListener(this);
        mRecyclerView.setAdapter(mArtistsAdapter);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mPresenter.bind(this);
        fetchData(false, 0, sCachedLoadedArtists);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        sCachedLoadedArtists = mArtistsAdapter.getItemCount();
        mPresenter.unsubscribe();
        mPresenter.unbind();
    }

    @Override
    public void onRefresh()
    {
        mArtistsAdapter.clear();
        fetchData(true, 0, 20);
    }

    @Override
    public void onItemClick(View view, int position)
    {
        Artist artist = mArtistsAdapter.getList().get(position);
        ((ArtistsActivity) getActivity()).addFragment(DetailsFragment.newInstance(artist), true);
    }

    @Override
    public void showArtists(List<Artist> artists)
    {
        mArtistsAdapter.changeList(artists);
    }

    @Override
    public void showProgressView(final boolean show)
    {
        mSwipeLayout.post(new Runnable()
        {
            @Override
            public void run()
            {
                mSwipeLayout.setRefreshing(show);
            }
        });
    }

    @Override
    public void showError(Throwable t)
    {
        Utils.showToast(getActivity(), t.getMessage());
        Log.e(TAG, t.getMessage());
    }

    @Override
    public void loadMore(int loadedCount)
    {
        fetchData(false, loadedCount, loadedCount + 20);
    }

    private void initViews(View view)
    {
        mEmptyView = view.findViewById(R.id.artist_emptyview);
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_view);
        mSwipeLayout.setOnRefreshListener(this);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_artist);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void fetchData(boolean forceLoad, int from, int to)
    {
        int[] range = {from, to};
        mPresenter.fetchArtists(forceLoad, range);
    }
}
