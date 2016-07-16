package com.postnov.android.summerschoolapp.data.source;

import com.postnov.android.summerschoolapp.data.entity.Artist;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by platon on 15.07.2016.
 */
public class Repository implements IDataSource
{
    private IDataSource local;
    private IDataSource remote;
    private boolean isConnected = false;

    public Repository(IDataSource local, IDataSource remote)
    {
        this.local = local;
        this.remote = remote;
    }

    @Override
    public Observable<ArrayList<Artist>> getList(final int loaded)
    {
        if (isConnected)
        {
            return remote.getList(loaded).doOnNext(new Action1<ArrayList<Artist>>()
            {
                @Override
                public void call(ArrayList<Artist> artists)
                {
                    local.save(artists);
                }
            });
        }
        else
        {
            return local.getList(loaded);
        }
    }

    @Override
    public void save(ArrayList<Artist> artists) {}
}
