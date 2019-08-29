package com.grin.appforuniver.data.model.user;


import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Posada {

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

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getPostVykl() {
        return mPostVykl;
    }

    public void setPostVykl(String postVykl) {
        this.mPostVykl = postVykl;
    }

    @Override
    public String toString() {
        return "Posada{" +
                "id=" + mId +
                ", postVykl='" + mPostVykl + '\'' +
                '}';
    }
}
