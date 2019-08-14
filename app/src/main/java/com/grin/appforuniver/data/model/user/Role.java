package com.grin.appforuniver.data.model.user;


import com.google.gson.annotations.SerializedName;

class Role {

    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
}
