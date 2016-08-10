package com.postnov.android.summerschoolapp.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Cover implements Serializable
{
    @SerializedName("small")
    private String coverSmall;

    @SerializedName("big")
    private String coverBig;

    public Cover(String small, String big)
    {
        coverSmall = small;
        coverBig = big;
    }

    public String getSmall() {
        return coverSmall;
    }

    public void setCoverSmall(String coverSmall) {
        this.coverSmall = coverSmall;
    }

    public String getCoverBig() {
        return coverBig;
    }

    public void setCoverBig(String coverBig) {
        this.coverBig = coverBig;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cover cover = (Cover) o;

        if (!coverSmall.equals(cover.coverSmall)) return false;

        return coverBig.equals(cover.coverBig);
    }

    @Override
    public int hashCode()
    {
        int result = coverSmall.hashCode();
        result = 31 * result + coverBig.hashCode();

        return result;
    }
}
