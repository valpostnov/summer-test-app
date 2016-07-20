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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ArtistsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        ArtistsAdapter.OnItemClickListener, ArtistsAdapter.OnEndlessListener, ArtistsView
{
    private static final String TAG = "ArtistsFragment";
    private static final int TO = 20;
    private static final int FROM = 0;
    private static int sCachedLoadedArtists = 20;

    @BindView(R.id.swipe_view) SwipeRefreshLayout mSwipeLayout;
    @BindView(R.id.recyclerview_artist) RecyclerView mRecyclerView;
    @BindView(R.id.artist_emptyview) View mEmptyView;

    private ArtistsAdapter mArtistsAdapter;
    private ArtistsPresenter mPresenter;
    private Unbinder unbinder;

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
        View view = inflater.inflate(R.layout.fragment_artists, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mArtistsAdapter = new ArtistsAdapter(getActivity(), mEmptyView);
        mArtistsAdapter.setOnItemClickListener(this);
        mArtistsAdapter.setOnEndlessListener(this);
        mSwipeLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mArtistsAdapter);
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mPresenter.bind(this);
        fetchData(false, FROM, sCachedLoadedArtists);
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
        fetchData(true, FROM, TO);
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
        fetchData(false, loadedCount, loadedCount + TO);
    }

    private void fetchData(boolean forceLoad, int from, int to)
    {
        int[] range = {from, to};
        mPresenter.fetchArtists(forceLoad, range);
    }
}
