package com.postnov.android.summerschoolapp.artists;

import com.postnov.android.summerschoolapp.artists.interfaces.ArtistsPresenter;
import com.postnov.android.summerschoolapp.artists.interfaces.ArtistsView;
import com.postnov.android.summerschoolapp.data.entity.Artist;
import com.postnov.android.summerschoolapp.data.source.IDataSource;

import java.util.List;

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

    public ArtistsPresenterImpl(IDataSource repository)
    {
        this.repository = repository;
        subscriptions = new CompositeSubscription();
    }

    @Override
    public void fetchArtists(final boolean forceLoad, final int from, final int to)
    {
        artistsView.showProgressView(true);
        if (forceLoad) repository.delete();

        subscriptions.add(repository.getList(from, to)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError, onCompleted));
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
