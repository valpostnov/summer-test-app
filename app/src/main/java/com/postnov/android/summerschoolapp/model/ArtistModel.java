package com.postnov.android.summerschoolapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by postnov on 12.04.2016.
 */
public class ArtistModel {

    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("genres")
    private String[] mGenres;
    @SerializedName("tracks")
    private String mTracks;
    @SerializedName("albums")
    private String mAlbums;
    @SerializedName("description")
    private String mDesc;
    @SerializedName("cover")
    private CoverModel mCover;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getGenres() {

        StringBuilder genres = new StringBuilder();

        if (mGenres.length > 1)
        {
            genres.append(mGenres[0]);
            for (int i = 1; i < mGenres.length; i++)
            {
                genres.append(", ");
                genres.append(mGenres[i]);
            }
        }
        else if (mGenres.length == 0)
        {
            genres.append("none");
        }
        else
        {
            genres.append(mGenres[0]);
        }

        return genres.toString();
    }

    public void setGenres(String[] genres) {
        this.mGenres = genres;
    }

    public String getTracks() {
        return mTracks;
    }

    public void setTracks(String tracks) {
        this.mTracks = tracks;
    }

    public String getAlbums() {
        return mAlbums;
    }

    public void setAlbums(String albums) {
        this.mAlbums = albums;
    }

    public String getDesc() {
        return mDesc;
    }

    public void setDesc(String desc) {
        this.mDesc = desc;
    }

    public CoverModel getCover() {
        return mCover;
    }

    public void setCover(CoverModel cover) {
        this.mCover = cover;
    }

}
