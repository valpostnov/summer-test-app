package com.postnov.android.summerschoolapp.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by postnov on 12.04.2016.
 */
public class Cover implements Serializable
{
    @SerializedName("small")
    private String mCoverSmall;

    @SerializedName("big")
    private String mCoverBig;

    public String getCoverSmall() {
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
}
