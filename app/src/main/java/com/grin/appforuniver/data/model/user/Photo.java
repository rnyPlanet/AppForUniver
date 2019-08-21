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

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        this.mId = id;
    }

    public byte[] getPhoto() {
        return mPhoto;
    }

    public void setPhoto(byte[] photo) {
        this.mPhoto = photo;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + mId +
                ", photo=" + Arrays.toString(mPhoto) +
                '}';
    }
}
