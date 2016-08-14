package com.postnov.android.summerschoolapp.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;

public class Artist implements Serializable
{
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("genres")
    private String[] genres;

    @SerializedName("tracks")
    private int tracks;

    @SerializedName("albums")
    private int albums;

    @SerializedName("description")
    private String desc;

    @SerializedName("cover")
    private Cover cover;

    public Artist(int id, String name, String[] genres,
                  int tracks, int albums, String desc, Cover cover)
    {
        this.id = id;
        this.name = name;
        this.genres = genres;
        this.tracks = tracks;
        this.albums = albums;
        this.desc = desc;
        this.cover = cover;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGenres() {

        StringBuilder genres = new StringBuilder();

        if (this.genres.length > 1)
        {
            genres.append(this.genres[0]);
            for (int i = 1; i < this.genres.length; i++)
            {
                genres.append(", ");
                genres.append(this.genres[i]);
            }
        }
        else if (this.genres.length == 0)
        {
            genres.append("unknown");
        }
        else
        {
            genres.append(this.genres[0]);
        }

        return genres.toString();
    }

    public int getTracks() {
        return tracks;
    }

    public int getAlbums() {
        return albums;
    }

    public String getDesc() {
        return desc;
    }

    public Cover getCover() {
        return cover;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Artist artist = (Artist) o;

        if (id != artist.id) return false;
        if (tracks != artist.tracks) return false;
        if (albums != artist.albums) return false;
        if (!name.equals(artist.name)) return false;
        if (!desc.equals(artist.desc)) return false;

        return cover.equals(artist.cover);
    }

    @Override
    public int hashCode()
    {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + Arrays.hashCode(genres);
        result = 31 * result + tracks;
        result = 31 * result + albums;
        result = 31 * result + desc.hashCode();
        result = 31 * result + cover.hashCode();

        return result;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        builder.append("Artist { ");
        builder.append("id: ");
        builder.append(id);
        builder.append(" name: ");
        builder.append(name);
        builder.append(" genres: ");
        builder.append(Arrays.toString(genres));
        builder.append(" albums: ");
        builder.append(albums);
        builder.append(" tracks: ");
        builder.append(tracks);
        builder.append(" }");

        return builder.toString();
    }

    public static Builder Builder()
    {
        return new Builder();
    }

    public static class Builder
    {
        private int id;
        private String name;
        private String[] genres;
        private int tracks;
        private int albums;
        private String desc;
        private Cover cover;

        public Builder setId(int id)
        {
            this.id = id;
            return this;
        }

        public Builder setName(String name)
        {
            this.name = name;
            return this;
        }

        public Builder setGenres(String[] genres)
        {
            this.genres = genres;
            return this;
        }

        public Builder setTracks(int tracks)
        {
            this.tracks = tracks;
            return this;
        }

        public Builder setAlbums(int albums)
        {
            this.albums = albums;
            return this;
        }

        public Builder setDesc(String desc)
        {
            this.desc = desc;
            return this;
        }

        public Builder setCover(Cover cover)
        {
            this.cover = cover;
            return this;
        }

        public Artist build()
        {
            return new Artist(id, name, genres, tracks, albums, desc, cover);
        }
    }
}
