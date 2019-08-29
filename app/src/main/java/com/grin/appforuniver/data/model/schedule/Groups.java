/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grin.appforuniver.data.model.schedule;


import com.google.gson.annotations.SerializedName;
import com.grin.appforuniver.data.model.user.User;

import java.util.Collection;

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
    
}
