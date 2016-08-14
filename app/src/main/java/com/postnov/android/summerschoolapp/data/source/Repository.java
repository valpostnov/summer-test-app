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
    public Observable<List<Artist>> getList(int offset, int limit)
    {
        if (cache.isEmpty()) return fromRemote(offset, limit);

        return fromLocal(offset, limit);
    }

    @Override
    public void delete()
    {
        cache.clear();
    }

    private Observable<List<Artist>> fromRemote(final int offset, final int limit)
    {
        return remote.getList(offset, limit)
                .doOnNext(artists -> cache.put(artists))
                .map(artists -> artists.subList(offset, limit));
    }

    private Observable<List<Artist>> fromLocal(final int offset, final int limit)
    {
        return Observable.just(cache.get())
                .filter(artists -> (artists.size() != offset))
                .map(artists -> subList(offset, limit, artists));
    }
}
