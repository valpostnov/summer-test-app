package com.postnov.android.summerschoolapp.data.source;

import com.postnov.android.summerschoolapp.data.entity.Artist;
import com.postnov.android.summerschoolapp.data.source.local.ICache;

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

    private static Repository sRepository;

    public static Repository getInstance(ICache<Artist> cache, IDataSource remote)
    {
        if (sRepository == null)
        {
            sRepository = new Repository(cache, remote);
        }
        return sRepository;
    }

    private Repository(ICache<Artist> cache, IDataSource remote)
    {
        this.cache = cache;
        this.remote = remote;
    }

    @Override
    public Observable<List<Artist>> getList(int loaded)
    {
        if (!cache.isEmpty()) return fromRemote(loaded);
        return fromLocal(loaded);
    }

    @Override
    public void delete()
    {
        cache.clear();
    }

    private Observable<List<Artist>> fromRemote(int loaded)
    {
        return remote.getList(loaded).doOnNext(new Action1<List<Artist>>()
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
                return artists.subList(0, 20);
            }
        });
    }

    private Observable<List<Artist>> fromLocal(int loaded)
    {
        return Observable.just(cache.get(loaded));
    }
}
