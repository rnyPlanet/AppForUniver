package com.grin.appforuniver.data.model.schedule;

import com.google.gson.annotations.SerializedName;

public class TypeClasses {

    @SerializedName("id")
    private Integer mId;

    @SerializedName("type")
    private String mType;

    public Integer getId() {
        return mId;
    }

    public void setId(Integer mId) {
        this.mId = mId;
    }

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    @Override
    public String toString() {
        return "TypeClasses{" +
                "mId=" + mId +
                ", mType='" + mType + '\'' +
                '}';
    }
}
