package com.postnov.android.summerschoolapp.data.source.local;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.postnov.android.summerschoolapp.data.entity.Artist;

import java.util.List;

/**
 * Created by platon on 10.08.2016.
 */
public class JsonSerializer implements Serializer<List<Artist>>
{
    private final Gson gson = new Gson();

    @Override
    public String serialize(List<Artist> artist)
    {
        return gson.toJson(artist);
    }

    @Override
    public List<Artist> deserialize(String string)
    {
        return gson.fromJson(string, new TypeToken<List<Artist>>() {}.getType());
    }
}
