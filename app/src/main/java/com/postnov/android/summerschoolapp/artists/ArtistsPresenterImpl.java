package com.postnov.android.summerschoolapp.artists;

import android.accounts.NetworkErrorException;

import com.postnov.android.summerschoolapp.artists.interfaces.ArtistsPresenter;
import com.postnov.android.summerschoolapp.artists.interfaces.ArtistsView;
import com.postnov.android.summerschoolapp.data.entity.Artist;
import com.postnov.android.summerschoolapp.data.source.IDataSource;
import com.postnov.android.summerschoolapp.utils.INetworkManager;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by platon on 15.07.2016.
 */
public class ArtistsPresenterImpl implements ArtistsPresenter
{
    private ArtistsView artistsView;
    private CompositeSubscription subscriptions;
    private IDataSource repository;
    private INetworkManager networkManager;

    public ArtistsPresenterImpl(IDataSource repository, INetworkManager networkManager)
    {
        this.repository = repository;
        this.networkManager = networkManager;
        subscriptions = new CompositeSubscription();
    }

    @Override
    public void fetchArtists(boolean forceLoad, int offset, int limit)
    {
        if (networkManager.networkIsAvailable())
        {
            artistsView.showProgressView(true);
            subscriptions.add(createSubscription(forceLoad, offset, limit));
        }
        else
        {
            artistsView.showError(new NetworkErrorException());
        }
    }

    @Override
    public void bind(ArtistsView view)
    {
        artistsView = view;
    }

    @Override
    public void unbind()
    {
        subscriptions.clear();
        artistsView = null;
    }

    private Subscription createSubscription(boolean forceLoad, final int offset, final int limit)
    {
        if (forceLoad) repository.delete();
        return repository.getList(offset, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError, onCompleted);
    }

    private Action0 onCompleted = () -> {
        artistsView.showProgressView(false);
    };

    private Action1<List<Artist>> onNext = artists -> {
        artistsView.showArtists(artists);
    };

    private Action1<Throwable> onError = e -> {
        artistsView.showProgressView(false);
        artistsView.showError(e);
    };
}
