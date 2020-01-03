/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grin.appforuniver.data.model.schedule;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.grin.appforuniver.data.utils.Constants;

import lombok.Data;

/**
 * @author Йцукен
 */
@Data
public class Classes {

    @SerializedName("id")
    private Integer mId;

    @SerializedName("subject")
    private String mSubject;

    @SerializedName("type")
    private String mType;

    @SerializedName("place")
    private Constants.Place mPlace;

    @SerializedName("indexInDay")
    private int mIndexInDay;

    @SerializedName("additionalRequirements")
    private String mAdditionalRequirements;

    @SerializedName("week")
    private Constants.Week mWeek;

    @SerializedName("group")
    private Groups mAssignedGroup;

    @SerializedName("professor")
    private Professors mProfessor;

    @SerializedName("room")
    private Rooms mRoom;

    @SerializedName("subgroup")
    private Constants.Subgroup mSubgroup;

    public Integer getId() {
        return mId;
    }

    public String getSubject() {
        return mSubject;
    }

    public String getType() {
        return mType;
    }

    public Constants.Place getPlace() {
        return mPlace;
    }

    public int getIndexInDay() {
        return mIndexInDay;
    }

    public String getAdditionalRequirements() {
        return mAdditionalRequirements;
    }

    public Constants.Week getWeek() {
        return mWeek;
    }

    public Groups getAssignedGroup() {
        return mAssignedGroup;
    }

    public Professors getProfessor() {
        return mProfessor;
    }

    public Rooms getRoom() {
        return mRoom;
    }

    public Constants.Subgroup getSubgroup() {
        return mSubgroup;
    }

    public void setSubgroup(Constants.Subgroup mSubgroup) {
        this.mSubgroup = mSubgroup;
    }

    @NonNull
    @Override
    public String toString() {
        return "Classes{" +
                "mId=" + mId + '\t' +
                ", mSubject='" + mSubject + '\'' + '\t' +
                ", mType='" + mType + '\'' + '\t' +
                ", mPlace=" + mPlace + '\t' +
                ", mIndexInDay=" + mIndexInDay + '\t' +
                ", mAdditionalRequirements='" + mAdditionalRequirements + '\'' + '\t' +
                ", mWeek=" + mWeek + '\t' +
                ", mAssignedGroup=" + mAssignedGroup + '\t' +
                ", mProfessor=" + mProfessor + '\t' +
                ", mRoom=" + mRoom + '\t' +
                ", mSubgroup=" + mSubgroup + '\t' +
                "}\n";
    }
}
