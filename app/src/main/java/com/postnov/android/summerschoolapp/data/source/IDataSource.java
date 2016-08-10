package com.postnov.android.summerschoolapp.data.source;

import com.postnov.android.summerschoolapp.data.entity.Artist;

import java.util.List;

import rx.Observable;

/**
 * Created by platon on 15.07.2016.
 */
public interface IDataSource
{
    Observable<List<Artist>> getList(int from, int to);
    void delete();
}
