package com.postnov.android.summerschoolapp.data.source.local;

import com.postnov.android.summerschoolapp.data.entity.Artist;
import com.postnov.android.summerschoolapp.data.source.IDataSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import rx.Observable;

/**
 * Created by platon on 15.07.2016.
 */
public class LocalDataSource implements IDataSource
{
    private final File cacheDir;
    private final String fileName = "artists.list";

    public LocalDataSource(File cacheDir)
    {
        this.cacheDir = cacheDir;
    }

    @Override
    public Observable<ArrayList<Artist>> getList(int loaded)
    {
        File file = new File(cacheDir, fileName);
        ArrayList<Artist> artists = null;

        try (FileInputStream is = new FileInputStream(file); ObjectInputStream in = new ObjectInputStream(is))
        {
            artists = (ArrayList<Artist>) in.readObject();
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        return Observable.just(artists);
    }

    @Override
    public void save(ArrayList<Artist> artists)
    {
        File file = new File(cacheDir, fileName);

        try (FileOutputStream os = new FileOutputStream(file); ObjectOutputStream out = new ObjectOutputStream(os))
        {
            out.writeObject(artists);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
