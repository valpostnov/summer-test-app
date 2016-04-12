package com.postnov.android.summerschoolapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by postnov on 12.04.2016.
 */
public class CoverModel {
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
