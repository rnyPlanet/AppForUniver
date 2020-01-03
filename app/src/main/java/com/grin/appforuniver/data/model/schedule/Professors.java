/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grin.appforuniver.data.model.schedule;


import com.google.gson.annotations.SerializedName;
import com.grin.appforuniver.data.model.user.Department;
import com.grin.appforuniver.data.model.user.Posada;
import com.grin.appforuniver.data.model.user.User;

import lombok.Data;

@Data
public class Professors {

    @SerializedName("id")
    private Integer mId;

    @SerializedName("posada")
    private Posada mPosada;

    @SerializedName("department")
    private Department mDepartment;

    @SerializedName("user")
    private User mUser;

    public Integer getId() {
        return mId;
    }

    public void setId(Integer mId) {
        this.mId = mId;
    }

    public Posada getPosada() {
        return mPosada;
    }

    public void setPosada(Posada mPosada) {
        this.mPosada = mPosada;
    }

    public Department getDepartment() {
        return mDepartment;
    }

    public void setDepartment(Department mDepartment) {
        this.mDepartment = mDepartment;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User mUser) {
        this.mUser = mUser;
    }

    @Override
    public String toString() {
        return "Professors{" +
                "mId=" + mId +
                ", mPosada=" + mPosada +
                ", mDepartment=" + mDepartment +
                ", mUser=" + mUser +
                '}';
    }
}
