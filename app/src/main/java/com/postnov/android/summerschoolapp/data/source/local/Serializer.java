package com.postnov.android.summerschoolapp.data.source.local;

import com.postnov.android.summerschoolapp.data.entity.Artist;

import java.util.List;

/**
 * Created by platon on 14.08.2016.
 */
public interface Serializer<T>
{
    String serialize(T artist);

    T deserialize(String string);
}
