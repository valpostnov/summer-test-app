package com.postnov.android.summerschoolapp.data.source;

import com.postnov.android.summerschoolapp.data.entity.Artist;
import com.postnov.android.summerschoolapp.data.source.local.ICache;

import java.util.List;

import rx.Observable;

import static com.postnov.android.summerschoolapp.utils.Utils.subList;

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
        return remote.getList(from, to)
                .doOnNext(artists -> cache.put(artists))
                .map(artists -> artists.subList(from, to));
    }

    private Observable<List<Artist>> fromLocal(final int from, final int to)
    {
        return Observable.just(cache.get())
                .filter(artists -> (artists.size() != from))
                .map(artists -> subList(from, to, artists));
    }
}
