package com.postnov.android.summerschoolapp.artists;

import com.postnov.android.summerschoolapp.artists.interfaces.ArtistsPresenter;
import com.postnov.android.summerschoolapp.artists.interfaces.ArtistsView;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by platon on 15.07.2016.
 */
public class ArtistsPresenterImpl implements ArtistsPresenter<ArtistsView>
{
    private ArtistsView view;
    private CompositeSubscription subscriptions;

    public ArtistsPresenterImpl()
    {

    }

    @Override
    public void fetchArtists(boolean forceLoad, int loaded)
    {

    }

    @Override
    public void bind(ArtistsView view)
    {
        this.view = view;
        this.view.showProgressView(true);
    }

    @Override
    public void unbind()
    {
        this.view.showProgressView(false);
        view = null;
    }

    @Override
    public void unsubscribe()
    {
        subscriptions.clear();
    }
}
