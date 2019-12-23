/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grin.appforuniver.data.model.schedule;

import com.google.gson.annotations.SerializedName;

import lombok.Data;


@Data
public class Rooms {

    @SerializedName("id")
    private Integer mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("countPlaces")
    private Short mCountPlaces;

    @SerializedName("countPCs")
    private Short mCountPCs;

    @SerializedName("isBlocked")
    private Short mIsBlocked;

    @SerializedName("type")
    private String mType;

    @Override
    public String toString() {
        return "newModel.Rooms[ id= ]";
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public Short getCountPlaces() {
        return mCountPlaces;
    }

    public void setCountPlaces(Short mCountPlaces) {
        this.mCountPlaces = mCountPlaces;
    }

    public Short getCountPCs() {
        return mCountPCs;
    }

    public void setCountPCs(Short mCountPCs) {
        this.mCountPCs = mCountPCs;
    }

    public Short getIsBlocked() {
        return mIsBlocked;
    }

    public void setIsBlocked(Short mIsBlocked) {
        this.mIsBlocked = mIsBlocked;
    }

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }
}
