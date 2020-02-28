package com.grin.appforuniver.data.model.user;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Photo {

    /*
    photo": {
        "id": 1,
        "photo": null
    }
    */

    @SerializedName("id")
    private Integer mId;
    @SerializedName("filename")
    private String mPhotoUrl;

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        this.mId = id;
    }

    public String getUrl() {
        return mPhotoUrl;
    }

    public void setPhoto(String photo) {
        this.mPhotoUrl = photo;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + mId +
                ", photo=" + mPhotoUrl +
                '}';
    }
}
