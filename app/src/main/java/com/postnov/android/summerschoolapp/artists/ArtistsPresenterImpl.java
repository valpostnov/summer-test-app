package com.postnov.android.summerschoolapp.artists;

import com.postnov.android.summerschoolapp.artists.interfaces.ArtistsPresenter;
import com.postnov.android.summerschoolapp.artists.interfaces.ArtistsView;
import com.postnov.android.summerschoolapp.data.entity.Artist;
import com.postnov.android.summerschoolapp.data.source.IDataSource;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by platon on 15.07.2016.
 */
public class ArtistsPresenterImpl implements ArtistsPresenter<ArtistsView>
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
    public void fetchArtists(final int loaded)
    {
        artistsView.showProgressView(true);
        subscriptions.add(repository.getList(loaded)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Artist>>()
                {
                    @Override
                    public void onCompleted()
                    {
                        artistsView.showProgressView(false);
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        artistsView.showProgressView(false);
                        artistsView.showError(e);
                    }

                    @Override
                    public void onNext(List<Artist> artists)
                    {
                        if (loaded == 0) artistsView.showArtists(artists, true);
                        else artistsView.showArtists(artists, false);
                    }
                }));
    }

    @Override
    public void bind(ArtistsView view)
    {
        this.artistsView = view;
    }

    @Override
    public void unbind()
    {
        artistsView.showProgressView(false);
        artistsView = null;
    }

    @Override
    public void unsubscribe()
    {
        subscriptions.clear();
    }
}
