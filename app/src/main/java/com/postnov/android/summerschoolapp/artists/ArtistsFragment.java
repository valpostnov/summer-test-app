package com.postnov.android.summerschoolapp.artists;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.postnov.android.summerschoolapp.R;
import com.postnov.android.summerschoolapp.artists.interfaces.ArtistsPresenter;
import com.postnov.android.summerschoolapp.artists.interfaces.ArtistsView;
import com.postnov.android.summerschoolapp.data.entity.Artist;
import com.postnov.android.summerschoolapp.data.source.Repository;
import com.postnov.android.summerschoolapp.data.source.local.LocalDataSource;
import com.postnov.android.summerschoolapp.data.source.remote.RemoteDataSource;
import com.postnov.android.summerschoolapp.utils.Utils;

import java.util.List;

public class ArtistsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        ArtistsAdapter.OnItemClickListener, ArtistsAdapter.OnEndlessListener, ArtistsView
{
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
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mPresenter = new ArtistsPresenterImpl(new Repository(
                new LocalDataSource(getActivity().getCacheDir()), new RemoteDataSource()));
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
        mPresenter.fetchArtists(0);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mPresenter.unsubscribe();
        mPresenter.unbind();
    }

    @Override
    public void onRefresh()
    {
        mPresenter.fetchArtists(0);
    }

    @Override
    public void onItemClick(View view, int position)
    {
        Artist artist = mArtistsAdapter.getList().get(position);
        ((ArtistsActivity) getActivity()).addFragment(DetailsFragment.newInstance(artist), true);
    }

    @Override
    public void showArtists(List<Artist> artists, boolean isFistLoad)
    {
        if (isFistLoad)
        {
            mArtistsAdapter.changeList(artists);
            return;
        }
        mArtistsAdapter.addNext(artists);
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
        t.printStackTrace();
    }

    @Override
    public void loadMore(int count)
    {
        mPresenter.fetchArtists(count);
    }

    private void initViews(View view)
    {
        mEmptyView = view.findViewById(R.id.artist_emptyview);
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_view);
        mSwipeLayout.setOnRefreshListener(this);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_artist);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
    }
}
