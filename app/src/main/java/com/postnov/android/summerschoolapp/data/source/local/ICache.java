package com.postnov.android.summerschoolapp.data.source.local;

import java.util.List;

/**
 * Created by platon on 19.07.2016.
 */
public interface ICache<T>
{
    List<T> get(int loaded);
    void put(List<T> list);
    boolean clear();
    boolean isEmpty();
}
