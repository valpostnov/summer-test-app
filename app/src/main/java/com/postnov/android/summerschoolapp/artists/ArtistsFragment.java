package com.postnov.android.summerschoolapp.artists;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.postnov.android.summerschoolapp.R;
import com.postnov.android.summerschoolapp.artists.interfaces.ArtistsPresenter;
import com.postnov.android.summerschoolapp.artists.interfaces.ArtistsView;
import com.postnov.android.summerschoolapp.artists.interfaces.FragmentTransactionManager;
import com.postnov.android.summerschoolapp.artists.interfaces.ToolbarProvider;
import com.postnov.android.summerschoolapp.base.BaseFragment;
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

public class ArtistsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        ArtistsAdapter.OnItemClickListener, ArtistsAdapter.OnEndlessListener, ArtistsView
{
    private static final int TO = 20;
    private static final int FROM = 0;
    private static int sCachedLoadedArtists = 20;

    @BindView(R.id.swipe_view) SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyclerview_artist) RecyclerView rv;
    @BindView(R.id.artist_emptyview) View emptyView;

    private ArtistsAdapter artistsAdapter;
    private ArtistsPresenter presenter;

    public static ArtistsFragment newInstance()
    {
        return new ArtistsFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        ToolbarProvider toolbarProvider = getToolbarProvider();
        if (toolbarProvider != null) toolbarProvider.updateToolbar(getString(R.string.app_name));

        File cacheDir = getActivity().getCacheDir();
        ICache<Artist> cache = new CacheImpl(cacheDir);
        IDataSource dataSource = new RemoteDataSource();

        presenter = new ArtistsPresenterImpl(new Repository(cache, dataSource));
    }

    @Override
    protected int getLayout()
    {
        return R.layout.fragment_artists;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        artistsAdapter = new ArtistsAdapter(getActivity());
        artistsAdapter.setOnItemClickListener(this);
        artistsAdapter.setOnEndlessListener(this);
        refreshLayout.setOnRefreshListener(this);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(artistsAdapter);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        presenter.bind(this);
        fetchData(false, FROM, sCachedLoadedArtists);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        sCachedLoadedArtists = artistsAdapter.getItemCount();
        presenter.unsubscribe();
        presenter.unbind();
    }

    @Override
    public void onRefresh()
    {
        artistsAdapter.clear();
        fetchData(true, FROM, TO);
    }

    @Override
    public void onItemClick(View view, int position)
    {
        Artist artist = artistsAdapter.getList().get(position);
        FragmentTransactionManager fragmentTransactionManager = getFragmentTransactionManager();
        if (fragmentTransactionManager != null)
            fragmentTransactionManager.showFragment(DetailsFragment.newInstance(artist), true);
    }

    @Override
    public void showArtists(List<Artist> artists)
    {
        artistsAdapter.changeList(artists);
        emptyView.setVisibility(artistsAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showProgressView(final boolean show)
    {
        refreshLayout.setRefreshing(show);
    }

    @Override
    public void showError(Throwable error)
    {
        Utils.showToast(getActivity(), error.getMessage());
    }

    @Override
    public void loadMore(int loadedCount)
    {
        fetchData(false, loadedCount, loadedCount + TO);
    }

    private void fetchData(boolean forceLoad, int from, int to)
    {
        int[] range = {from, to};
        presenter.fetchArtists(forceLoad, range);
    }
}
