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
    
}
