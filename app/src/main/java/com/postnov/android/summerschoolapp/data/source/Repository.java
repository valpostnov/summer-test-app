package com.postnov.android.summerschoolapp.data.source;

import com.postnov.android.summerschoolapp.data.entity.Artist;

import java.util.List;

import rx.Observable;

/**
 * Created by platon on 15.07.2016.
 */
public class Repository implements IDataSource
{
    private IDataSource local;
    private IDataSource remote;

    public Repository(IDataSource local, IDataSource remote)
    {
        this.local = local;
        this.remote = remote;
    }

    @Override
    public Observable<List<Artist>> getList(int loaded)
    {
        return remote.getList(loaded);
    }
}
