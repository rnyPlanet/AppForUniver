/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grin.appforuniver.data.model.schedule;


import com.google.gson.annotations.SerializedName;

import lombok.Data;
/**
 *
 * @author Йцукен
 */
@Data
public class Groups {

    @SerializedName("id")
    private Integer mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("excludedDay")
    private String mExcludedDay;

    @SerializedName("size")
    private Short mSize;
    
    @Override
    public String toString() {
        return " name = " + mName + ", excludedDay: " + mExcludedDay + ", size: " + mSize;
    }

    public Integer getmId() {
        return mId;
    }

    public String getmName() {
        return mName;
    }

    public String getmExcludedDay() {
        return mExcludedDay;
    }

    public Short getmSize() {
        return mSize;
    }
}
