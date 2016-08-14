package com.postnov.android.summerschoolapp.data.source.local;

import com.postnov.android.summerschoolapp.data.entity.Artist;

import java.io.File;
import java.util.List;

/**
 * Created by platon on 19.07.2016.
 */
public class CacheImpl implements ICache<Artist>
{
    private final File cachedFile;
    private final Serializer<List<Artist>> serializer;
    private final FileManager fileManager;

    public CacheImpl(File cacheDir, String name, Serializer<List<Artist>> jsonSerializer)
    {
        cachedFile = new File(cacheDir, name);
        fileManager = new FileManager();
        serializer = jsonSerializer;
    }

    public CacheImpl(File cacheDir, Serializer<List<Artist>> jsonSerializer)
    {
        this(cacheDir, "artists.list", jsonSerializer);
    }

    @Override
    public List<Artist> get()
    {
        String json = fileManager.readFileContent(cachedFile);
        return serializer.deserialize(json);
    }

    @Override
    public void put(List<Artist> list)
    {
        fileManager.writeToFile(cachedFile, serializer.serialize(list));
    }

    @Override
    public boolean clear()
    {
        return cachedFile.delete();
    }

    @Override
    public boolean isEmpty()
    {
        return !cachedFile.exists();
    }
}
