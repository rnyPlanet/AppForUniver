/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grin.appforuniver.data.model.schedule;


import com.google.gson.annotations.SerializedName;

import java.util.Collection;

import lombok.Data;

/**
 *
 * @author Йцукен
 */

@Data
public class Professors {

    @SerializedName("id")
    private Integer mId;

    @SerializedName("name")
    private String mName;


    @Override
    public String toString() {
        return "newModel.Professors[ id= ]";
    }
    
}
