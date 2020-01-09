/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grin.appforuniver.data.model.schedule;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Subject {

    @SerializedName("id")
    private Integer mId;

    @SerializedName("fullName")
    private String mFullName;

    @SerializedName("shortName")
    private String mShortName;

    public Integer getId() {
        return mId;
    }

    public void setId(Integer mId) {
        this.mId = mId;
    }

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String mFullName) {
        this.mFullName = mFullName;
    }

    public String getShortname() {
        return mShortName;
    }

    public void setShortname(String mShortName) {
        this.mShortName = mShortName;
    }

    @Override
    public String toString() {
        return "Classes{" +
                "mId=" + mId +
                ", mSubject='" + mFullName + '\'' +
                ", mType='" + mShortName +
                '}';
    }
}
