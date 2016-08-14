package com.postnov.android.summerschoolapp.artists;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.postnov.android.summerschoolapp.App;
import com.postnov.android.summerschoolapp.R;
import com.postnov.android.summerschoolapp.artists.interfaces.ArtistsPresenter;
import com.postnov.android.summerschoolapp.artists.interfaces.ArtistsView;
import com.postnov.android.summerschoolapp.base.BaseFragment;
import com.postnov.android.summerschoolapp.data.entity.Artist;
import com.postnov.android.summerschoolapp.other.RecyclerScrollListener;
import com.postnov.android.summerschoolapp.other.ScrollHelperAdapter;
import com.postnov.android.summerschoolapp.utils.Utils;

import java.util.List;

import butterknife.BindView;

public class ArtistsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        ArtistsAdapter.OnItemClickListener, ArtistsView, ScrollHelperAdapter
{
    private static final int LIMIT = 20;
    private static final int OFFSET = 0;
    private static int sCachedCountArtists = 20;

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
    protected int getLayout()
    {
        return R.layout.fragment_artists;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        getToolbarProvider().updateToolbar(getString(R.string.app_name));
        presenter = new ArtistsPresenterImpl(App.from(getActivity()).getArtistRepository());

        artistsAdapter = new ArtistsAdapter(getActivity());
        artistsAdapter.setOnItemClickListener(this);
        refreshLayout.setOnRefreshListener(this);

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.addOnScrollListener(new RecyclerScrollListener(this));
        rv.setAdapter(artistsAdapter);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        presenter.bind(this);
        presenter.fetchArtists(false, OFFSET, sCachedCountArtists);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        sCachedCountArtists = artistsAdapter.getItemCount();
        presenter.unbind();
    }

    @Override
    public void onRefresh()
    {
        artistsAdapter.clear();
        presenter.fetchArtists(true, OFFSET, LIMIT);
    }

    @Override
    public void onItemClick(View view, int position)
    {
        Artist artist = artistsAdapter.getList().get(position);
        getFragmentTransactionManager().replaceFragment(DetailsFragment.newInstance(artist));
    }

    @Override
    public void onLoadMore(int count)
    {
        presenter.fetchArtists(false, count, count + LIMIT);
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
        refreshLayout.post(() -> refreshLayout.setRefreshing(show));
    }

    @Override
    public void showError(Throwable error)
    {
        Utils.showToast(getActivity(), error.getMessage());
    }
}
