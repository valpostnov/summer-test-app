package com.postnov.android.summerschoolapp.data.source.local;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.postnov.android.summerschoolapp.data.entity.Artist;

import java.util.List;

/**
 * Created by platon on 10.08.2016.
 */
public class JsonSerializer
{
    private final Gson gson = new Gson();

    public String serialize(List<Artist> artist)
    {
        return gson.toJson(artist);
    }

    public List<Artist> deserialize(String jsonString)
    {
        return gson.fromJson(jsonString, new TypeToken<List<Artist>>() {}.getType());
    }
}
