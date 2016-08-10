package com.postnov.android.summerschoolapp.data.source;

import com.postnov.android.summerschoolapp.data.entity.Artist;
import com.postnov.android.summerschoolapp.data.source.local.ICache;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by platon on 15.07.2016.
 */
public class Repository implements IDataSource
{
    private ICache<Artist> cache;
    private IDataSource remote;

    public Repository(ICache<Artist> cache, IDataSource remote)
    {
        this.cache = cache;
        this.remote = remote;
    }

    @Override
    public Observable<List<Artist>> getList(int from, int to)
    {
        if (cache.isEmpty()) return fromRemote(from, to);
        return fromLocal(from, to);
    }

    @Override
    public void delete()
    {
        cache.clear();
    }

    private Observable<List<Artist>> fromRemote(final int from, final int to)
    {
        return remote.getList(from, to).doOnNext(new Action1<List<Artist>>()
        {
            @Override
            public void call(List<Artist> artists)
            {
                cache.put(artists);
            }

        }).map(new Func1<List<Artist>, List<Artist>>()
        {
            @Override
            public List<Artist> call(List<Artist> artists)
            {
                return artists.subList(from, to);
            }
        });
    }

    private Observable<List<Artist>> fromLocal(final int from, final int to)
    {
        return Observable.just(cache.get()).map(new Func1<List<Artist>, List<Artist>>()
        {
            @Override
            public List<Artist> call(List<Artist> artists)
            {
                return artists.subList(from, to);
            }
        });
    }
}
