package com.postnov.android.summerschoolapp.data.source.local;

import com.postnov.android.summerschoolapp.data.entity.Artist;
import com.postnov.android.summerschoolapp.data.source.IDataSource;

import java.util.List;

import rx.Observable;

/**
 * Created by platon on 15.07.2016.
 */
public class LocalDataSource implements IDataSource
{
    @Override
    public Observable<List<Artist>> getList(int loaded) {
        return null;
    }

}
