package com.grin.appforuniver.data.model.user;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

import lombok.Data;

@Data
class Photo {

    /*
    photo": {
        "id": 1,
        "photo": null
    }
    */

    @SerializedName("id")
    private Integer mId;
    @SerializedName("photo")
    private byte[] mPhoto;

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public byte[] getmPhoto() {
        return mPhoto;
    }

    public void setmPhoto(byte[] mPhoto) {
        this.mPhoto = mPhoto;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + mId +
                ", photo=" + Arrays.toString(mPhoto) +
                '}';
    }
}
