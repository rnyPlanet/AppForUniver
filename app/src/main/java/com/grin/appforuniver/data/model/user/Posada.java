package com.grin.appforuniver.data.model.user;


import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
class Posada {

    /*
    sprPosada": {
        "id": 1,
        "postVykl": "професор, доктор наук"
    }
    */

    @SerializedName("id")
    private int mId;
    @SerializedName("postVykl")
    private String mPostVykl;

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmPostVykl() {
        return mPostVykl;
    }

    public void setmPostVykl(String mPostVykl) {
        this.mPostVykl = mPostVykl;
    }

    @Override
    public String toString() {
        return "Posada{" +
                "id=" + mId +
                ", postVykl='" + mPostVykl + '\'' +
                '}';
    }
}
