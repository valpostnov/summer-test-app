package com.postnov.android.summerschoolapp.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Cover implements Serializable
{
    @SerializedName("small")
    private String mCoverSmall;

    @SerializedName("big")
    private String mCoverBig;

    public Cover(String small, String big)
    {
        mCoverSmall = small;
        mCoverBig = big;
    }

    public String getSmall() {
        return mCoverSmall;
    }

    public void setCoverSmall(String coverSmall) {
        this.mCoverSmall = coverSmall;
    }

    public String getCoverBig() {
        return mCoverBig;
    }

    public void setCoverBig(String coverBig) {
        this.mCoverBig = coverBig;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cover cover = (Cover) o;

        if (!mCoverSmall.equals(cover.mCoverSmall)) return false;

        return mCoverBig.equals(cover.mCoverBig);
    }

    @Override
    public int hashCode()
    {
        int result = mCoverSmall.hashCode();
        result = 31 * result + mCoverBig.hashCode();

        return result;
    }
}
