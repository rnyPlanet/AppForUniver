package com.grin.appforuniver.data.model.schedule;

import com.google.gson.annotations.SerializedName;

public class TypeRoom {

    @SerializedName("id")
    private Integer mId;

    @SerializedName("type")
    private String mType;

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    @Override
    public String toString() {
        return "TypeRoom{" +
                "mId=" + mId +
                ", mType='" + mType + '\'' +
                '}';
    }
}
