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
    private ArtistsView mArtistsView;
    private CompositeSubscription mSubscriptions;
    private IDataSource mRepository;

    public ArtistsPresenterImpl(IDataSource repository)
    {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void fetchArtists(final boolean forceLoad, final int[] range)
    {
        mArtistsView.showProgressView(true);

        if (forceLoad) mRepository.delete();

        mSubscriptions.add(mRepository.getList(range)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Artist>>()
                {
                    @Override
                    public void onCompleted()
                    {
                        mArtistsView.showProgressView(false);
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        mArtistsView.showProgressView(false);
                        mArtistsView.showError(e);
                    }

                    @Override
                    public void onNext(List<Artist> artists)
                    {
                        mArtistsView.showArtists(artists);
                    }
                }));
    }

    @Override
    public void bind(ArtistsView view)
    {
        this.mArtistsView = view;
    }

    @Override
    public void unbind()
    {
        mArtistsView.showProgressView(false);
        mArtistsView = null;
    }

    @Override
    public void unsubscribe()
    {
        mSubscriptions.clear();
    }
}
