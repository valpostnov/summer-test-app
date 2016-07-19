package com.postnov.android.summerschoolapp.data.source.local;

import com.postnov.android.summerschoolapp.data.entity.Artist;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by platon on 19.07.2016.
 */
public class CacheImpl implements ICache<Artist>
{
    private final File fullName;

    public CacheImpl(File cacheDir, String name)
    {
        fullName = new File(cacheDir, name);
    }

    public CacheImpl(File cacheDir)
    {
        this(cacheDir, "artists.ser");
    }

    @Override
    public List<Artist> get(int loaded) {

        try (FileInputStream is = new FileInputStream(fullName); ObjectInputStream in = new ObjectInputStream(is))
        {
            return loadNext((List<Artist>) in.readObject(), loaded);
        }
        catch (ClassNotFoundException | IOException e)
        {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void put(List<Artist> list)
    {
        try (FileOutputStream os = new FileOutputStream(fullName); ObjectOutputStream out = new ObjectOutputStream(os))
        {
            out.writeObject(list);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public boolean clear()
    {
        return fullName.delete();
    }

    @Override
    public boolean isEmpty()
    {
        return fullName.exists();
    }

    private List<Artist> loadNext(List<Artist> list, int loadedEntries)
    {

        int allEntries = list.size();

        // если записи еще не загружены, загружаем первые 20
        if (loadedEntries == 0)
        {
            return list.subList(0, 20);
        }

        // если загруженных записей меньше чем всех записей
        if (loadedEntries < allEntries)
        {
            // если записей, которых осталось загрузить  <= 20
            if (allEntries - loadedEntries <= 20)
            {
                // то грузим оставшиеся
                return list.subList(loadedEntries, loadedEntries + (allEntries - loadedEntries));
            }
            else
            {
                // иначе грузим следующие 20 записей
                return list.subList(loadedEntries, loadedEntries + 20);
            }
        }

        return list.subList(0, 0);
    }
}
